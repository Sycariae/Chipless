package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.ui.composables.presets.Label
import com.sycarias.chipless.ui.theme.ChiplessColors as CColor
import com.sycarias.chipless.ui.theme.ChiplessTypography as CStyle

@Composable
fun IntInputField(
    label: String,
    modifier: Modifier = Modifier,
    initialValue: String = "",
    maxLen: Int = 7,
    isValid: Boolean = true,
    onValueChange: (String) -> Unit = {}
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(initialValue, selection = TextRange(initialValue.length))) }
    val maxLength = minOf(9,maxLen)

    fun validateInput(newValue: String): String {
        var newIntValue = newValue
        if ( newValue.any { it.isDigit().not() } ) {
            newIntValue = newIntValue.filter { it.isDigit() }
        }
        return when {
            newIntValue.isEmpty() -> "0" // Prevent empty value
            newIntValue == "00" -> "0" // Prevent multiple zeros
            newIntValue.startsWith('0') && newIntValue.toInt() > 0 -> newIntValue.trimStart('0')
            newIntValue.length > maxLength -> newIntValue.take(maxLength) // Limit to max length
            else -> newIntValue
        }
    }

    OutlinedTextField(
        label = { Label(text = label) },
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
        textStyle = CStyle.body.copy(color = CColor.textSecondary),
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