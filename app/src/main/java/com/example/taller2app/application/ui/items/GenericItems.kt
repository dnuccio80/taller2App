package com.example.taller2app.application.ui.items

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.taller2app.ui.theme.AcceptButtonColor
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.ContrastColor
import com.example.taller2app.ui.theme.DeleteButtonColor
import com.example.taller2app.ui.theme.TextColor

@Composable
fun SimpleFabItem(modifier: Modifier, innerPadding: PaddingValues, onClick: () -> Unit) {

    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier.padding(
            bottom = innerPadding.calculateBottomPadding() + 16.dp,
            end = 8.dp
        ),
        containerColor = ContrastColor,
        contentColor = TextColor

    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add button")
    }
}

@Composable
fun AcceptDeclineButtons(
    acceptText: String,
    declineText: String,
    acceptContainerColor: Color = AcceptButtonColor,
    acceptContentColor: Color = TextColor,
    declineContainerColor: Color = DeleteButtonColor,
    declineContentColor: Color = TextColor,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        ButtonItem(
            declineText,
            containerColor = declineContainerColor,
            contentColor = declineContentColor,
            onClick = { onDecline() })
        ButtonItem(
            acceptText,
            containerColor = acceptContainerColor,
            contentColor = acceptContentColor,
            onClick = { onAccept() })
    }
}

@Composable
fun ButtonItem(
    text: String,
    containerColor: Color = ButtonColor,
    contentColor: Color = TextColor,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(text)
    }
}

@Composable
fun TitleItem(text: String, color: Color = TextColor, modifier: Modifier = Modifier) {
    Text(text, style = MaterialTheme.typography.titleLarge, color = color, modifier = modifier)
}

@Composable
fun BodyTextItem(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun HorizontalDividerCard() {
    HorizontalDivider(
        Modifier.fillMaxWidth(),
        thickness = .5.dp,
        color = ButtonColor
    )
}

@Composable
fun RadioButtonItem(selectedValue: String, description: String, onClick: (String) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                onClick(description)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = description == selectedValue,
            onClick = { onClick(description) },
            colors = RadioButtonDefaults.colors(
                selectedColor = ContrastColor,
                unselectedColor = ContrastColor
            )
        )
        Text(description)
    }
}

