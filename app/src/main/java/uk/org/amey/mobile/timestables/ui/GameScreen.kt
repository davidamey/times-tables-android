package uk.org.amey.mobile.timestables.ui

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.org.amey.mobile.timestables.ui.theme.TimesTablesTheme
import uk.org.amey.mobile.timestables.ui.widgets.Keypad

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current

    if (configuration.orientation == ORIENTATION_LANDSCAPE) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.align(Alignment.End).padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Streak: ${String.format("%03d", uiState.streak)}",
                    modifier = Modifier.padding(8.dp)
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Keypad(
                    onClick = { viewModel.handleKey(it) },
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = uiState.sum,
                    fontSize = 48.sp,
                    color = if (uiState.isLastGuessWrong) Color.Red else Color.Unspecified,
                    modifier = Modifier.weight(1f).padding(start = 16.dp)
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.align(Alignment.End).padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Streak: ${String.format("%03d", uiState.streak)}",
                    modifier = Modifier.padding(8.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = uiState.sum,
                    fontSize = 48.sp,
                    color = if (uiState.isLastGuessWrong) Color.Red else Color.Unspecified
                )
            }
            Keypad(
                onClick = { viewModel.handleKey(it) },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }

}

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun GameScreenPreview() {
    TimesTablesTheme {
        GameScreen()
    }
}
