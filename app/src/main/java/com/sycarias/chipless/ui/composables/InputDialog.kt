package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sycarias.chipless.ui.composables.presets.ActionButtonText
import com.sycarias.chipless.ui.composables.presets.PrimaryActionButton
import com.sycarias.chipless.ui.composables.presets.Subtitle
import com.sycarias.chipless.ui.extensions.dropShadow
import com.sycarias.chipless.ui.extensions.innerShadow
import com.sycarias.chipless.ui.theme.ChiplessColors

@Composable
fun InputDialog(
    title: String = "",
    buttonText: String = "Confirm",
    isConfirmEnabled: Boolean = true,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    contents: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
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
                    color = ChiplessColors.primary.copy(alpha = 0.6f),
                    blurRadius = 20.dp,
                    cornerRadius = 50.dp
                ),
            shape = RoundedCornerShape(50.dp),
            colors = CardDefaults.cardColors(
                containerColor = ChiplessColors.bgSecondary
            )
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (title.isNotEmpty()) {
                    Subtitle(
                        text = title
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }

                Column(
                    modifier = Modifier.padding(bottom = 20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    contents()
                }

                PrimaryActionButton(
                    onClick = { onConfirm() },
                    enabled = isConfirmEnabled,
                    modifier = Modifier.padding(top = 10.dp)
                ) { ActionButtonText(text = buttonText) }
            }
        }
    }
}