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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.ui.theme.ChiplessColors as CColor
import com.sycarias.chipless.ui.theme.ChiplessTypography as CStyle

@Composable
fun IntInputField(
    label: String,
    initialValue: String = "",
    defaultValue: String = "0",
    maxLen: Int = 9,
    modifier: Modifier = Modifier,
    isValid: Boolean = true,
    onValueChange: (String) -> Unit = {}
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(initialValue, selection = TextRange(initialValue.length))) }
    val maxLength = minOf(9,maxLen)

    fun validateInput(newValue: String): String {
        return when {
            newValue.isEmpty() -> defaultValue
            textFieldValue.text == "0" -> newValue.trimStart('0')
            newValue.length > maxLength -> newValue.take(maxLength)
            else -> newValue
        }
    }

    OutlinedTextField(
        label = { Text(text = label, style = CStyle.l2) },
        value = textFieldValue,
        modifier = modifier,
        isError = !isValid,
        onValueChange = { newValue ->
            val validatedValue = validateInput(newValue.text)
            if (textFieldValue.text != validatedValue) {
                textFieldValue = TextFieldValue(
                    text = validatedValue,
                    selection = TextRange(validatedValue.length)
                )
                onValueChange(validatedValue)
            }
        },
        textStyle = CStyle.body,
        singleLine = true,
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
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )
}