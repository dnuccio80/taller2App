package com.example.taller2app.application.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taller2app.R
import com.example.taller2app.ui.theme.AppBackground
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.CardBackground
import com.example.taller2app.ui.theme.TextColor

@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        HomeFabItem(Modifier.align(Alignment.BottomEnd), innerPadding)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            TitleItem("home")
            Spacer(Modifier.size(16.dp))
            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(
                    2.dp, brush = Brush.verticalGradient(
                        colors = listOf(CardBackground, ButtonColor),
                    )
                ),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    TitleItem("Balance")
                    Spacer(Modifier.size(8.dp))
                    TitleItem("$50.000")
                    Spacer(Modifier.size(16.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    BodyTextItem("Trabajos realizados: $150.000")
                    Spacer(Modifier.size(8.dp))
                    BodyTextItem("Pagos recibidos: $150.000")
                }
            }
            Spacer(Modifier.size(16.dp))
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(
                    2.dp, brush = Brush.verticalGradient(
                        colors = listOf(CardBackground, ButtonColor),
                    )
                ),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    TitleItem(text = stringResource(R.string.work_done))
                    Spacer(Modifier.size(4.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    LazyColumn(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(10) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BodyTextItem("Tubo torneado", Modifier.weight(1f))
                                BodyTextItem("x12")
                                Spacer(Modifier.width(16.dp))
                                BodyTextItem("$7200")
                                Spacer(Modifier.width(16.dp))
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = "edit work",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                }
            }
            Spacer(Modifier.size(16.dp))
            Card(
                Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(
                    2.dp, brush = Brush.verticalGradient(
                        colors = listOf(CardBackground, ButtonColor),
                    )
                ),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    TitleItem(text = stringResource(R.string.payments_received))
                    Spacer(Modifier.size(4.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    BodyTextItem("Cheques: $150.000")
                    Spacer(Modifier.size(8.dp))
                    BodyTextItem("Efectivo: $150.000")
                }
            }
        }
    }
}

@Composable
fun TitleItem(text: String) {
    Text(text, style = MaterialTheme.typography.titleLarge)
}

@Composable
fun BodyTextItem(text: String, modifier: Modifier = Modifier) {
    Text(text, style = MaterialTheme.typography.bodyLarge, modifier = modifier)
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
fun HomeFabItem(modifier: Modifier, innerPadding: PaddingValues){
    FloatingActionButton(
        onClick = { },
        modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding() + 16.dp),
        containerColor = ButtonColor,
        contentColor = TextColor

    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add button")
    }
}

