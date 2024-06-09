package com.example.wordle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun SettingsDisplay(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit,
    areNotificationsEnabled: Boolean,
    onNotificationsToggle: (Boolean) -> Unit,
    onResetGameData: () -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeaderImage(id = R.drawable.wordlesettings)
            ThemeSettings(isDarkTheme = isDarkTheme, onThemeChange = onThemeChange)
            LanguageSettings(selectedLanguage = selectedLanguage, onLanguageChange = onLanguageChange)
            NotificationSettings(
                areNotificationsEnabled = areNotificationsEnabled,
                onNotificationsToggle = onNotificationsToggle
            )
            ResetGameData(onReset = onResetGameData)
        }
    }
}


@Composable
fun ThemeSettings(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.8f)
    ) {
        Text(
            text = stringResource(id = R.string.dark_theme),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = isDarkTheme,
            onCheckedChange = onThemeChange,
            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                checkedTrackColor = MaterialTheme.colorScheme.onPrimary)
        )
    }
}

@Composable
fun LanguageSettings(
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    val languages = listOf("English", "Spanish", "French", "German")
    Column(modifier = Modifier
        .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text = stringResource(id = R.string.language),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        languages.forEach { language ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.8f)
            ) {
                RadioButton(
                    selected = selectedLanguage == language,
                    onClick = { onLanguageChange(language) },
                    colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.tertiary)
                )
                Text(
                    text = language,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun NotificationSettings(
    areNotificationsEnabled: Boolean,
    onNotificationsToggle: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.8f)
    ) {
        Text(
            text = stringResource(id = R.string.enable_notifications),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = areNotificationsEnabled,
            onCheckedChange = onNotificationsToggle,
            colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                checkedTrackColor = MaterialTheme.colorScheme.onPrimary)
        )
    }
}

@Composable
fun ResetGameData(onReset: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.reset_highscore),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.clear_progress),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = onReset,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFfa0907),
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(text = stringResource(id = R.string.reset))
        }
    }
}
