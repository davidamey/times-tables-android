package uk.org.amey.mobile.timestables.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.org.amey.mobile.timestables.data.PreferencesRepository
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor(
    private val preferences: PreferencesRepository
) : ViewModel() {

    data class UiState(
        val sum: String = "",
        val round: Int = 0,
        val score: Int = 0,
        val streak: Int = 0,
        val bestStreak: Int = 0,
        val isLastGuessWrong: Boolean = false,
        val isGameComplete: Boolean = false
    )

    private var target = Triple(0, 0, 0)

    private val maxStreak = preferences.maxStreak.stateIn(viewModelScope, SharingStarted.Lazily, 0)
//    private val maxStreak = MutableStateFlow(0)
    private val currentGuess = MutableStateFlow(0)
    private val currentStreak = MutableStateFlow(0)
    private val lastGuessWrong = MutableStateFlow(false)

    val uiState = combine(
        currentGuess,
        currentStreak,
        lastGuessWrong,
        maxStreak
    ) { current, streak, lastGuessWrong, maxStreak ->
        val ans = if (current == 0) {
            ""
        } else {
            currentGuess.value.toString()
        }

        UiState(
            sum = "${target.first} x ${target.second} = $ans",
            round = 0,
            score = 0,
            streak = streak,
            bestStreak = maxStreak,
            isLastGuessWrong = lastGuessWrong,
            isGameComplete = false
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), UiState())

    init {
        resetGame()
    }

    fun handleKey(x: Int) {
        when (x) {
            -1 -> {
                currentGuess.value/=10
            }

            -2 -> checkAnswer()

            else -> {
                val newAnswer = currentGuess.value * 10 + x
                if (newAnswer < 1000) {
                    currentGuess.value = newAnswer
                }
            }
        }
    }

    private fun checkAnswer() {
        if (currentGuess.value == target.third) {
            currentStreak.value++
            if (currentStreak.value > maxStreak.value) {
                viewModelScope.launch {
//                    maxStreak.value = currentStreak.value
                    preferences.setMaxStreak(currentStreak.value)
                }
            }
            nextSum()
        } else {
            currentStreak.value = 0
            lastGuessWrong.value = true
        }
    }

    private fun resetGame() {
        nextSum()
    }

    private fun nextSum() {
        val (x, y) = pickRandomInts()
        target = Triple(x, y, x*y)
        currentGuess.value = 0
        lastGuessWrong.value = false
    }

    private fun pickRandomInts(): List<Int> {
        return List(2) { Random.nextInt(1, 10) }
    }
}