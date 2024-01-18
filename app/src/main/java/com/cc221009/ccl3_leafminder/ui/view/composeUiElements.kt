package com.cc221009.ccl3_leafminder.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc221009.ccl3_leafminder.R


@Composable
fun Header(
    viewName: String?,
    rightIconPath: Int?,
) {
    Box(
        modifier = Modifier
        .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack, contentDescription = "",
                tint = colorScheme.primary
            )

            viewName?.let {
            H2Text(
                text = viewName,
            )
            }

            Box(
                modifier = Modifier
                .width(20.dp)
            ) {
                // Conditionally display textDetail if it's not null
                rightIconPath?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "Back"
                    )
                }
            }




        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun DefaultTextField(headline: String, placeholderText: String) {
    var textSaver by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    val textState = remember { mutableStateOf("") }
    val borderBottomWidth = 2.dp
    val borderBottomColor = colorScheme.outline // Change as needed

    Column {


        // Your CopyText Composable
        CopyText(text = headline)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = borderBottomWidth.value * density
                    val y = size.height - strokeWidth
                    drawLine(
                        color = borderBottomColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, // Hide the indicator
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTextField(
    headline: String,
    placeholderText: String,
    imgPath: Int,
    selectedDate: String
) {
    var textSaver by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }

    CopyText(text = headline)

    Button(
        onClick = { },
    ) {
        CopyText(
            text = if (selectedDate.isNotEmpty()) selectedDate else "stringResource(R.string.select_a_date)",
            //modifier = Modifier.weight(1f),
            // style = TextStyle(),
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

@Composable
fun PrimaryButton(
    text: String,
    onClickLogic: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(0.7f) // 70% of the screen width
            .height(80.dp)
            .padding(bottom = 20.dp),
        shape = RoundedCornerShape(15.dp),

        onClick = { onClickLogic() },
        colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.background
                ),
        ) {
        CopyBoldText(text = text, colorScheme.background)
    }
}

@Composable
fun PlantItem(
    plantName: String,
    species: String?,
    imgPath: Int,
    needsWater: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(end = 20.dp)
            .clickable {

        }
    ) {
        val borderColor = if (needsWater) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary

        Box(
            modifier = Modifier
                .size(80.dp) // Set the size including the border
                .background(color = borderColor, shape = CircleShape),
        ) {
            Image(
                painter = painterResource(id = imgPath),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(75.dp) // Image size, smaller than the Box to create a border effect
                    .align(Alignment.Center) // Center the image inside the Box
                    .clip(CircleShape), // Clip the image to a circle shape
                contentScale = ContentScale.Crop,
            )

            if (needsWater) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(35.dp)
                        .align(Alignment.TopEnd) // Center the image inside the Box
                        .background(MaterialTheme.colorScheme.secondary),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_waterdrop_small),
                        contentDescription = "Waterdrop",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        CopyText(text = plantName)

        // Conditionally display textDetail if it's not null
        species?.let {
            CopyItalicText(text = it, MaterialTheme.colorScheme.outline)

        }
    }
}


// ––––––––––––––––––––– TYPO –––––––––––––––––––––––

@Composable
fun H1Text(
    text: String,
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.grandhotel_regular)),
            fontSize = 50.sp)
    )
}

@Composable
fun H2Text(
    text: String,
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 20.sp)
    )
}

@Composable
fun H3Text(
    text: String,
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 16.sp)
    )
    Spacer(modifier = Modifier.height(10.dp))

}

@Composable
fun H4Text(
    text: String,
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 14.sp,
            textAlign = TextAlign.Center)
    )
}

@Composable
fun CopyText(
    text: String,
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_semibold)),
            fontSize = 15.sp)
    )
}

@Composable
fun CopyBoldText(
    text: String,
    color: Color
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_bold)),
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun CopyItalicText(
    text: String,
    color: Color
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.opensans_semibold_italic)),
            fontSize = 15.sp,
            color = color)
    )
}









