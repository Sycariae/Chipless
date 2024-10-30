package com.sycarias.chipless.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.R

@Composable
fun AddPlayerButton(
    onClick: () -> Unit = {}
) {
    val aPBImage = painterResource(id = R.drawable.image_add_player) // Add APButton Image in Drawable
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(50.dp),
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(15.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = remember { aPBImage }, // Cache Add Player Button Image
            contentDescription = "Add Player Button",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}