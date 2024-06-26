package com.w36495.senty.view.ui.component.textFields

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import com.w36495.senty.view.ui.theme.Green40

@Composable
fun SentyTextField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    hintSize: TextUnit = TextUnit.Unspecified,
    isError: Boolean = false,
    errorMsg: String,
    enabled: Boolean = true,
    inputType: KeyboardType = KeyboardType.Text,
    onChangeText: (String) -> Unit,
) {
    TextField(
        keyboardOptions = KeyboardOptions(
            keyboardType = inputType
        ),
        modifier = modifier,
        value = text,
        onValueChange = {
            onChangeText(it)
        },
        placeholder = {
            Text(text = hint, fontSize = hintSize)
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedIndicatorColor = Green40,
            focusedIndicatorColor = Green40,
            disabledContainerColor = Color.White,
            disabledIndicatorColor = Green40,
            disabledPlaceholderColor = Color.Unspecified,
            errorContainerColor = Color.White,
            cursorColor = Green40,
        ),
        singleLine = true,
        maxLines = 1,
        isError = isError,
        readOnly = !enabled,
        supportingText = {
            if (isError) {
                Text(text = errorMsg)
            }
        },
    )
}