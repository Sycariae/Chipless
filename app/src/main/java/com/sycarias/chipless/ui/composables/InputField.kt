package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.utils.EmptyTextToolbar
import com.sycarias.chipless.ui.theme.ChiplessColors as CColor
import com.sycarias.chipless.ui.theme.ChiplessTypography as CStyle

@Composable
fun InputField( // TODO: Make un-focus on keyboard hide
    label: String,
    initialText: String = "",
    placeholder: String = "",
    isValid: (String) -> Boolean = { true }, // default to always valid
    maxLen: Int = 9, // TODO: Finish Implementing maxLength
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit = {}
) {
    CompositionLocalProvider(
        LocalTextToolbar provides EmptyTextToolbar
    ) {
        var textFieldValue by remember { mutableStateOf(TextFieldValue(initialText)) }
        val isInvalid = textFieldValue.text.isNotEmpty() && !isValid(textFieldValue.text)
        remember { keyboardType }

        OutlinedTextField(
            modifier = modifier,
            value = textFieldValue,
            label = { Text(text = label, style = CStyle.l2) },
            placeholder = { Text(text = placeholder, style = CStyle.body) },
            onValueChange = { newValue ->
                val updatedText = if (keyboardType == KeyboardType.Number && newValue.text.all { it.isDigit() }) {
                    when {
                        newValue.text.isEmpty() -> placeholder
                        textFieldValue.text == "0" && newValue.text.drop(1).isNotEmpty() -> newValue.text.drop(1)
                        newValue.text.length > 9 -> newValue.text.take(9)
                        newValue.text.length > maxLen -> newValue.text.take(maxLen)
                        else -> newValue.text
                    }
                } else {
                    newValue.text
                }

                // Update text and lock the cursor at the end
                textFieldValue = TextFieldValue(
                    text = updatedText,
                    selection = TextRange(updatedText.length) // Lock cursor to the end
                )

                // Trigger external onValueChange arg with the valid updated text
                onValueChange(updatedText)
            },
            textStyle = CStyle.body,
            singleLine = true,
            isError = isInvalid,
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = CColor.accentPrimary,
                errorLabelColor = CColor.accentPrimary,
                focusedBorderColor = CColor.textTertiary,
                unfocusedBorderColor = CColor.textTertiary,
                errorCursorColor = Transparent,
                cursorColor = Transparent,
                selectionColors = TextSelectionColors(
                    handleColor = Transparent,
                    backgroundColor = Transparent
                ),
            ),
            shape = RoundedCornerShape(15.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            )
        )
    }
}

@Composable
fun IntInputField ( // TODO: Copy the function rather than call it with certain args
    label:String,
    initialText: String = "",
    minNum: Int = 0,
    maxNum: Int = Int.MAX_VALUE,
    maxLen: Int = 9,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    InputField(
        label = label,
        initialText = initialText,
        isValid = { it.toInt() in minNum..maxNum },
        maxLen = maxLen,
        placeholder = "0",
        keyboardType = KeyboardType.Number,
        onValueChange = onValueChange,
        modifier = modifier
    )
}