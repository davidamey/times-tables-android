package uk.org.amey.mobile.timestables.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Keypad(
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val arrangement = Arrangement.spacedBy(8.dp)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = arrangement
    ) {
        Row(horizontalArrangement = arrangement) {
            listOf(1, 2, 3).map {
                KeypadButton(onClick = { onClick(it) }, text = it.toString())
            }
        }
        Row(horizontalArrangement = arrangement) {
            listOf(4, 5, 6).map {
                KeypadButton(onClick = { onClick(it) }, text = it.toString())
            }
        }
        Row(horizontalArrangement = arrangement) {
            listOf(7, 8, 9).map {
                KeypadButton(onClick = { onClick(it) }, text = it.toString())
            }
        }
        Row(horizontalArrangement = arrangement) {
            KeypadButton(onClick = { onClick(-1) }, text = "<")
            KeypadButton(onClick = { onClick(0) }, text = "0")
            KeypadButton(onClick = { onClick(-2) }, text = "Go")
        }
    }
}

@Composable
fun KeypadButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val size = 100.dp
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.width(size).height(size)
    ) {
        Text(fontSize = 20.sp, text = text)
    }
}