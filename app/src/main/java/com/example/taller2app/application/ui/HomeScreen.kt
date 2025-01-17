package com.example.taller2app.application.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.taller2app.R
import com.example.taller2app.ui.theme.AppBackground
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.CardBackground
import com.example.taller2app.ui.theme.TextColor

@Composable
fun HomeScreen(innerPadding: PaddingValues) {

    var showPaymentDialog by rememberSaveable { mutableStateOf(false) }
    var showEditWorkDialog by rememberSaveable { mutableStateOf(false) }
    var showAddWorkDialog by rememberSaveable { mutableStateOf(false) }
    var quantityEditedWork by rememberSaveable { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
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
            BalanceCardItem()
            Spacer(Modifier.size(16.dp))
            WorkDoneCardItem() { showEditWorkDialog = true }
            Spacer(Modifier.size(16.dp))
            PaymentReceivedCardItem()
        }
        HomeFabItem(
            Modifier.align(Alignment.BottomEnd),
            innerPadding,
            onNewWorkClicked = { showAddWorkDialog = true },
            onNewPaymentClicked = { showPaymentDialog = true })
        NewPaymentDialog(showPaymentDialog) { showPaymentDialog = false }
        EditWorkDoneDialog(
            showEditWorkDialog,
            titleText = stringResource(R.string.edit_work_done),
            onDismiss = { showEditWorkDialog = false },
            workQuantity = quantityEditedWork,
            onQuantityChange = { quantityEditedWork = it }
        )
        EditWorkDoneDialog(
            showAddWorkDialog,
            titleText = stringResource(R.string.add_new_work),
            onDismiss = {
                showAddWorkDialog = false
                quantityEditedWork = ""
            },
            workQuantity = quantityEditedWork,
            onQuantityChange = { quantityEditedWork = it }
        )
    }
}

@Composable
private fun BalanceCardItem() {
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
            TitleItem(text = stringResource(R.string.balance))
            Spacer(Modifier.size(8.dp))
            TitleItem("$50.000")
            Spacer(Modifier.size(16.dp))
            HorizontalDividerCard()
            Spacer(Modifier.size(16.dp))
            BodyTextItem("${stringResource(R.string.work_done)}: $150.000")
            Spacer(Modifier.size(8.dp))
            BodyTextItem("${stringResource(R.string.cash_received)}: $150.000")
            Spacer(Modifier.size(8.dp))
            BodyTextItem("${stringResource(R.string.checks_received)}: $150.000")
        }
    }
}

@Composable
private fun PaymentReceivedCardItem() {
    Card(
        Modifier
            .fillMaxWidth()
            .height(180.dp),
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
            LazyColumn(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(10) {
                    PaymentCardItem()
                }
            }
        }
    }
}

@Composable
fun PaymentCardItem() {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyTextItem("Checks")
        Spacer(Modifier.width(8.dp))
        BodyTextItem("...................................")
        Spacer(Modifier.width(8.dp))
        BodyTextItem("$")
        Spacer(Modifier.width(8.dp))
        BodyTextItem("7200")
        Spacer(Modifier.width(16.dp))
        Icon(
            Icons.Filled.Edit,
            contentDescription = "edit work",
            tint = Color.White
        )
    }
}

@Composable
private fun WorkDoneCardItem(onEditWorkButtonClicked: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(280.dp),
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
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                onEditWorkButtonClicked()
                            }
                        )
                    }
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
fun HomeFabItem(
    modifier: Modifier,
    innerPadding: PaddingValues,
    onNewPaymentClicked: () -> Unit,
    onNewWorkClicked: () -> Unit
) {

    var expandedValue by rememberSaveable { mutableStateOf(false) }

    FloatingActionButton(
        onClick = { expandedValue = true },
        modifier = modifier.padding(
            bottom = innerPadding.calculateBottomPadding() + 16.dp,
            end = 8.dp
        ),
        containerColor = ButtonColor,
        contentColor = TextColor

    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add button")
        DropdownMenuHomeItem(
            expandedValue,
            onDismiss = { expandedValue = false },
            onNewWorkClicked = {
                onNewWorkClicked()
                expandedValue = false
            },
            onNewPaymentClicked = {
                onNewPaymentClicked()
                expandedValue = false
            }
        )
    }
}

@Composable
fun DropdownMenuHomeItem(
    expandedValue: Boolean,
    onDismiss: () -> Unit,
    onNewPaymentClicked: () -> Unit,
    onNewWorkClicked: () -> Unit
) {
    DropdownMenu(
        expanded = expandedValue,
        onDismissRequest = { onDismiss() },
        offset = DpOffset((-34).dp, 0.dp),
        modifier = Modifier.background(ButtonColor)
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.add_new_work)) },
            onClick = { onNewWorkClicked() }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.add_new_payment)) },
            onClick = { onNewPaymentClicked() }
        )
    }
}

