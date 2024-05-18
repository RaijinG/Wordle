package com.example.wordle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wordle.ui.theme.WordleTheme

//0xFFfa0907 - červená
@Composable
fun MainPage(
    onPlayClicked: () -> Unit,
    onHowToPlayClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF545454)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.wordlelogo),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier.size(320.dp)
            )
            Spacer(modifier = Modifier.padding(30.dp))
            Button(
                onClick = onPlayClicked,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .height(50.dp)
                    .fillMaxWidth(0.7f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF424141),
                    contentColor = Color(0xFF32f0ef))
            ) {
                Text(text = stringResource(id = R.string.play), fontSize = 20.sp)
            }
            Button(
                onClick = onHowToPlayClicked,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .height(50.dp)
                    .fillMaxWidth(0.7f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF424141),
                    contentColor = Color(0xFF32f0ef))
            ) {
                Text(text = stringResource(id = R.string.how_to_play), fontSize = 20.sp)
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .height(50.dp)
                    .fillMaxWidth(0.7f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF424141),
                    contentColor = Color(0xFF32f0ef))
            ) {
                Text(text = stringResource(id = R.string.settings), fontSize = 20.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    WordleTheme {
        MainPage(onPlayClicked = {}, onHowToPlayClicked = {})
    }
}