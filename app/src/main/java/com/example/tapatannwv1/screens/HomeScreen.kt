package com.example.tapatannwv1.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tapatannwv1.R

@Composable
fun HomeScreen(
    onStartNewMatch: () -> Unit,
    onSettingsClicked: () -> Unit,
    onHelpClicked: () -> Unit,
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.secondaryColor))
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.End
            ){
            IconButton(
                onClick = onHelpClicked,
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    Icons.Default.Help,
                    contentDescription = "Help",
                    tint = colorResource(id = R.color.tertiaryColor)
                )
            }
        }

        //Spacer(modifier = Modifier.height(25.dp))

        Image(
            painter = painterResource(id = R.drawable.tapatanlogo),
            contentDescription = "Tapatan Logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            alignment = Alignment.TopCenter
        )

        Text(
            text = "TAPATAN",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 60.sp,
        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = onStartNewMatch,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .size(75.dp)
                .align(Alignment.CenterHorizontally),
            shape = RectangleShape
        ) {
            Text(
                text = "NEW MATCH",
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Button(
            onClick = onSettingsClicked,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .size(75.dp)
                .align(Alignment.CenterHorizontally),
            shape = RectangleShape
        ) {
            Text(
                text = "SETTINGS",
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelLarge
            )
        }

    }
}



@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen({}, {}, {})
}
