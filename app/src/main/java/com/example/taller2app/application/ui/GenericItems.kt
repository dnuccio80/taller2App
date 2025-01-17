package com.example.taller2app.application.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.taller2app.R
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.TextColor

@Composable
fun SimpleFabItem(modifier: Modifier, innerPadding: PaddingValues) {

    FloatingActionButton(
        onClick = { },
        modifier = modifier.padding(
            bottom = innerPadding.calculateBottomPadding() + 16.dp,
            end = 8.dp
        ),
        containerColor = ButtonColor,
        contentColor = TextColor

    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add button")
    }
}

@Composable
fun AcceptDeclineButtons(acceptText: String, declineText: String, onAccept: () -> Unit, onDecline: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        ButtonItem(acceptText) { onAccept() }
        ButtonItem(declineText) { onDecline() }
    }
}

@Composable
fun ButtonItem(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor,
            contentColor = TextColor
        )
    ) {
        Text(text)
    }
}

@Composable
fun TitleItem(text: String) {
    Text(text, style = MaterialTheme.typography.titleLarge)
}

@Composable
fun BodyTextItem(text: String, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Start) {
    Text(text, style = MaterialTheme.typography.bodyLarge, modifier = modifier, textAlign = textAlign)
}

@Composable
fun HorizontalDividerCard() {
    HorizontalDivider(
        Modifier.fillMaxWidth(),
        thickness = .5.dp,
        color = ButtonColor
    )
}