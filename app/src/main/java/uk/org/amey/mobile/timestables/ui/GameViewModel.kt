package uk.org.amey.mobile.timestables.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameViewModel : ViewModel() {

    data class UiState(
        val sum: String = "",
        val round: Int = 0,
        val score: Int = 0,
        val isLastGuessWrong: Boolean = false,
        val isGameComplete: Boolean = false
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var target = Triple(0, 0, 0)
    private var currentGuess = 0

    init {
        resetGame()
    }

    fun handleKey(x: Int) {
        when (x) {
            -1 -> {
                currentGuess/=10
                updateSum()
            }

            -2 -> checkAnswer()

            else -> {
                val newAnswer = currentGuess * 10 + x
                if (newAnswer < 1000) {
                    currentGuess = newAnswer
                }
                updateSum()
            }
        }
    }

    private fun updateSum() {
        _uiState.update { current ->
            val ans = if (currentGuess == 0) {
                ""
            } else {
                currentGuess.toString()
            }
            with(target) {
                current.copy(sum = "$first x $second = $ans", isLastGuessWrong = false)
            }
        }
    }

    private fun checkAnswer() {
        if (currentGuess == target.third) {
            nextSum()
        } else {
            _uiState.update { current ->
                current.copy(isLastGuessWrong = true)
            }
        }
    }

    private fun resetGame() {
        nextSum()
    }

    private fun nextSum() {
        val (x, y) = pickRandomInts()
        target = Triple(x, y, x*y)
        currentGuess = 0
        updateSum()
    }

    private fun pickRandomInts(): List<Int> {
        return List(2) { Random.nextInt(1, 10) }
    }
}