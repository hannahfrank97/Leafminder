package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cc221009.ccl3_leafminder.ui.theme.CCL3_LeafminderTheme

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CCL3_LeafminderTheme {
        Greeting("Heyo")
    }
}