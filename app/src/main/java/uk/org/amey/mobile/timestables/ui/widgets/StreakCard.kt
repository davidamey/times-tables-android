package uk.org.amey.mobile.timestables.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.org.amey.mobile.timestables.ui.theme.TimesTablesTheme

@Composable
fun StreakRow(
    current: Int,
    best: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        StreakCard(
            label = "Streak",
            streak = current
        )
        StreakCard(
            label = "Best",
            streak = best
        )
    }
}

@Composable
private fun StreakCard(
    label: String,
    streak: Int
) {
    Card {
        Text(
            text = "$label: ${String.format("%03d", streak)}",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StreakRowPreview() {
    TimesTablesTheme {
        StreakRow(current = 10, best = 20)
    }
}