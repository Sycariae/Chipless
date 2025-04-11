package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sycarias.chipless.ui.composables.presets.ActionButtonText
import com.sycarias.chipless.ui.composables.presets.PrimaryActionButton
import com.sycarias.chipless.ui.composables.presets.Subtitle
import com.sycarias.chipless.ui.extensions.dropShadow
import com.sycarias.chipless.ui.extensions.innerShadow
import com.sycarias.chipless.ui.theme.ChiplessColors

enum class DialogInputType {
    INT,
    STRING
}

@Composable
fun InputDialog(
    type: DialogInputType,
    prompt: String = "",
    inputFieldLabel: String,
    maxLen: Int = 16,
    isValid: Boolean = true,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val inputFieldValue by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .width(250.dp)
                .border(
                    width = (0.5).dp,
                    color = ChiplessColors.primary,
                    shape = RoundedCornerShape(50.dp)
                )
                .innerShadow(
                    shape = RoundedCornerShape(50.dp),
                    color = ChiplessColors.primary.copy(alpha = 0.6f),
                    blur = 25.dp
                )
                .dropShadow(
                    color = ChiplessColors.primary.copy(alpha = 0.5f),
                    blurRadius = 30.dp,
                    cornerRadius = 50.dp
                ),
            shape = RoundedCornerShape(50.dp),
            colors = CardDefaults.cardColors(
                containerColor = ChiplessColors.bgSecondary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .padding(start = 40.dp, end = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Subtitle(text = prompt)
                when (type) {
                    DialogInputType.INT ->
                        IntInputField(
                            label = inputFieldLabel,
                            initialValue = inputFieldValue,
                            modifier = Modifier
                                .fillMaxWidth(),
                            maxLen = maxLen,
                            onValueChange = { onValueChange(it) }
                        )
                    DialogInputType.STRING ->
                        InputField(
                            label = inputFieldLabel,
                            initialValue = inputFieldValue,
                            modifier = Modifier
                                .fillMaxWidth(),
                            maxLen = maxLen,
                            onValueChange = { onValueChange(it) }
                        )
                }
                PrimaryActionButton(
                    onClick = { onConfirm() },
                    enabled = isValid,
                    modifier = Modifier.padding(top = 10.dp)
                ) { ActionButtonText(text = "Confirm") }
            }
        }
    }
}