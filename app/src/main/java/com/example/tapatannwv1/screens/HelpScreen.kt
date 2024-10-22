package com.example.tapatannwv1.screens

///I got the all the 'help' information from this website
//(https://www.whatdowedoallday.com/tapatan/)


import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tapatannwv1.R

@Composable
fun HelpScreen(
    onBackClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.Top
    ) {

        //Row for help screen navigation bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.secondaryColor)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            IconButton(onClick = onBackClicked) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(50.dp),
                    tint = colorResource(id = R.color.tertiaryColor)
                )
            }

            Spacer(modifier = Modifier.weight(0.70f))

            Image(
                painter = painterResource(id = R.drawable.tapatanlogo),
                contentDescription = "Tapatan Logo",
                modifier = Modifier
                    .size(50.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Text(
            text = "Objective:",
            style = MaterialTheme.typography.headlineLarge,
            color = colorResource(id = R.color.tertiaryColor),
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )

        Text(
            text = stringResource(R.string.helpObjectives),
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )

        Text(
            text = "Rules:",
            style = MaterialTheme.typography.headlineLarge,
            color = colorResource(id = R.color.tertiaryColor),
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )

        Text(
            text = stringResource(R.string.rules),
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )

        Text(
            text = "The Drop Phase:",
            style = MaterialTheme.typography.headlineLarge,
            color = colorResource(id = R.color.tertiaryColor),
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )

        Text(
            text = stringResource(R.string.dropPhase),
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )

        Text(
            text = "The Move Phase:",
            style = MaterialTheme.typography.headlineLarge,
            color = colorResource(id = R.color.tertiaryColor),
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )

        Text(
            text = stringResource(R.string.movePhase),
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )

        Text(
            text = "TIP:",
            style = MaterialTheme.typography.headlineLarge,
            color = colorResource(id = R.color.tertiaryColor),
            modifier = Modifier
                .padding(16.dp, 8.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )

        Text(
            text = stringResource(R.string.tip),
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .align(Alignment.Start),
        )
    }
}
@Preview
@Composable
fun PreviewHelpScreen() {
    HelpScreen(onBackClicked = {})
}