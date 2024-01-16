package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221009.ccl3_leafminder.R
import com.cc221009.ccl3_leafminder.ui.theme.CCL3_LeafminderTheme


@Composable
fun Header(
    viewName: String,
    rightIconPath: Int,
) {
    Box() {
        Row() {

            Icon(
                painter = painterResource(id = R.drawable.icon_plus),
                contentDescription = "Back"
            )

            Text(
                text = viewName,
            )

            Icon(
                painter = painterResource(id = rightIconPath),
                contentDescription = "Back"
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextField(headline: String, placeholderText: String) {
    var textSaver by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    Text(text = headline)

    TextField(
        value = textSaver,
        onValueChange = { newTextValue -> textSaver = newTextValue },
        label = { Text(text = placeholderText) },
        placeholder = { Text(text = placeholderText) },
        modifier = Modifier
            .padding(bottom = 20.dp)
            .border(2.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
            .fillMaxSize(0.8f)
            .height(50.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTextField(headline: String, placeholderText: String, imgPath: Int, selectedDate: String) {
    var textSaver by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    Text(text = headline)

    Button(
        onClick = { },
    ) {
        Text(
            text = if (selectedDate.isNotEmpty()) selectedDate else "stringResource(R.string.select_a_date)",
            modifier = Modifier.weight(1f),
            style = TextStyle()
        )

        Icon(
            painter = painterResource(id = R.drawable.icon_calendar),
            contentDescription = "Select Date",
            tint = colorScheme.secondary
        )
    }

    TextField(
        value = textSaver,
        onValueChange = { newTextValue -> textSaver = newTextValue },
        label = { Text(text = placeholderText) },
        placeholder = { Text(text = placeholderText) },
        modifier = Modifier
            .padding(bottom = 20.dp)
            .border(2.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
            .fillMaxSize(0.8f)
            .height(50.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryButton(
    text: String,
    onClickLogic: () -> Unit
) {
    Button (
        modifier = Modifier
            .fillMaxWidth(0.8f) // 70% of the screen width
            .height(80.dp)
            .padding(bottom = 20.dp)
        ,
        shape = RoundedCornerShape(15.dp),
        onClick = { onClickLogic() }

        ) {
        Text(text = text)
    }
}
