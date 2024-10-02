package com.sycarias.chipless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sycarias.chipless.ui.theme.ChiplessTheme
import com.sycarias.chipless.ui.theme.ChiplessButtonColors as CButtonColors
import com.sycarias.chipless.ui.theme.ChiplessColors as CColor
import com.sycarias.chipless.ui.theme.ChiplessTypography as CType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChiplessTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "CHIPLESS",
            style = CType.title,
            color = CColor.textPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "Bet Manager For Texas Hold 'Em Poker",
            style = CType.sh2,
            color = CColor.textTertiary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Image
        Image(
            painter = painterResource(id = R.drawable.cards_image), // Add your image in drawable
            contentDescription = "Poker Cards Icon",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(170.dp)
                .padding(top = 40.dp, bottom = 40.dp)
        )

        // Create Table Button
        Button(
            onClick = { /* TODO: Create Table Button click */ },
            modifier = Modifier
                .width(320.dp)
                .height(65.dp),
            shape = RoundedCornerShape(100.dp),
            colors = CButtonColors(CColor.primary),
            elevation = ButtonDefaults.buttonElevation(10.dp)
        ) {
            Text("Create Table", color = CColor.textPrimary, style = CType.h2)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Saved Tables Button
        Button(
            onClick = { /* TODO: Saved Tables Button click */ },
            modifier = Modifier
                .width(290.dp)
                .height(65.dp),
            shape = RoundedCornerShape(100.dp),
            colors = CButtonColors(),
            elevation = ButtonDefaults.buttonElevation(6.dp)
        ) {
            Text("Saved Tables", color = CColor.textPrimary, style = CType.sh1)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Settings Button
        Button(
            onClick = { /* TODO: Settings Button click */ },
            modifier = Modifier
                .width(290.dp)
                .height(65.dp),
            shape = RoundedCornerShape(100.dp),
            colors = CButtonColors(),
            elevation = ButtonDefaults.buttonElevation(6.dp)
        ) {
            Text("Settings", color = CColor.textPrimary, style = CType.sh1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ChiplessTheme {
        MainScreen()
    }
}