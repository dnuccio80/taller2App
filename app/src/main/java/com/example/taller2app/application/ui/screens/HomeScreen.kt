package com.example.taller2app.application.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
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
import com.example.taller2app.application.ui.TallerViewModel
import com.example.taller2app.application.ui.dataClasses.PaymentDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import com.example.taller2app.application.ui.dataClasses.formatNumber
import com.example.taller2app.application.ui.dataClasses.getTotalPrice
import com.example.taller2app.application.ui.items.AddNewWorkDoneDialog
import com.example.taller2app.application.ui.items.BodyTextItem
import com.example.taller2app.application.ui.items.ConfirmDialog
import com.example.taller2app.application.ui.items.EditPaymentDialog
import com.example.taller2app.application.ui.items.EditWorkDoneDialog
import com.example.taller2app.application.ui.items.HorizontalDividerCard
import com.example.taller2app.application.ui.items.NewPaymentDialog
import com.example.taller2app.application.ui.items.TitleItem
import com.example.taller2app.application.ui.sealedClasses.AvailablePaymentMethods
import com.example.taller2app.ui.theme.AppBackground
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.CardBackground
import com.example.taller2app.ui.theme.ContrastColor
import com.example.taller2app.ui.theme.NegativeBalanceColor
import com.example.taller2app.ui.theme.PositiveBalanceColor
import com.example.taller2app.ui.theme.TextColor
import kotlin.math.exp

@Composable
fun HomeScreen(innerPadding: PaddingValues, viewModel: TallerViewModel) {

    val showPaymentDialog = viewModel.showPaymentDialog.collectAsState()
    val showAddWorkDialog = viewModel.showAddNewWorkDoneDialog.collectAsState()
    val quantityEditedWork = viewModel.quantityEditedWork.collectAsState()
    val workSelectedValue = viewModel.workSelectedValue.collectAsState()

    val workDoneList = viewModel.workDoneList.collectAsState()
    val paymentReceivedList = viewModel.paymentReceivedList.collectAsState()

    val totalPaymentReceivedByCategory by viewModel.totalPaymentReceivedByCategory.collectAsState()
    val totalAmountInWorkDone by viewModel.totalAmountInWorkDone.collectAsState()

    // Saver for SelectedWorkDataClass
    val workDataClassSaver = Saver<WorkDataClass, List<Any>>(
        save = { workData ->
            listOf(
                workData.id,
                workData.description,
                workData.unitPrice,
                workData.dateModified
            )
        },
        restore = { list ->
            WorkDataClass(
                id = list[0] as Int,
                description = list[1] as String,
                unitPrice = list[2] as Int,
                dateModified = list[3] as Long
            )
        }
    )

    var selectedWorkDataClass by rememberSaveable(stateSaver = workDataClassSaver) {
        mutableStateOf(
            WorkDataClass(description = "", unitPrice = 0)
        )
    }

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
                .verticalScroll(rememberScrollState()),

            ) {
            TitleItem("home")
            Spacer(Modifier.size(16.dp))
            BalanceCardItem(
                totalPaymentReceivedByCategory,
                totalAmountInWorkDone,
                viewModel
            )
            Spacer(Modifier.size(16.dp))
            WorkDoneCardItem(workDoneList,
                onWorkDoneDataModified = {
                    viewModel.updateWorkDone(it)
                },
                onDeletedButtonClicked = {
                    viewModel.deleteWorkDone(it)
                },
                onClearDataButtonClicked = {
                    viewModel.clearAllWorkDoneData()
                })

            Spacer(Modifier.size(16.dp))
            PaymentReceivedCardItem(
                paymentReceivedList,
                viewModel
            )
        }
        HomeFabItem(
            Modifier.align(Alignment.BottomEnd),
            innerPadding,
            onNewWorkClicked = { viewModel.updateShowAddWorkDialog(true) },
            onNewPaymentClicked = { viewModel.updateShowPaymentDialog(true) })
        NewPaymentDialog(showPaymentDialog.value, viewModel) {
            viewModel.updateShowPaymentDialog(
                false
            )
            viewModel.clearPaymentDialogData()
        }
        AddNewWorkDoneDialog(
            show = showAddWorkDialog.value,
            viewModel = viewModel,
            workSelected = workSelectedValue.value,
            workQuantity = quantityEditedWork.value,
            onDismiss = {
                viewModel.updateShowAddWorkDialog(false)
                viewModel.clearAddNewWorkDataDialog()
            },
            onQuantityChange = { viewModel.updateQuantityEditedWork(it) },
            onAcceptButtonClicked = {
                if (viewModel.hasAllCorrectFields(
                        quantityEditedWork.value,
                        workSelectedValue.value
                    )
                ) {
                    viewModel.addNewWorkDone(
                        WorkDoneDataClass(
                            workDataClass = selectedWorkDataClass,
                            quantity = quantityEditedWork.value.toInt()
                        )
                    )
                    viewModel.updateShowAddWorkDialog(false)
                    viewModel.clearAddNewWorkDataDialog()
                }
            },
            onWorkChange = { selectedWorkDataClass = it }
        )
    }
}

@Composable
private fun BalanceCardItem(
    totalPaymentReceived: Map<String, Int>,
    totalAmountInWorkDone: Int,
    viewModel: TallerViewModel,
) {

    val cashAmount = totalPaymentReceived[AvailablePaymentMethods.Cash.paymentMethod] ?: 0
    val checkAmount = totalPaymentReceived[AvailablePaymentMethods.Check.paymentMethod] ?: 0

    val balance = totalAmountInWorkDone - cashAmount - checkAmount
    val positiveBalance = balance >= 0

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

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
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                TitleItem(
                    text = stringResource(R.string.balance),
                    TextColor,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { showConfirmDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ContrastColor,
                        contentColor = TextColor
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)

                ) {
                    Text(stringResource(R.string.clear_all))
                }
            }
            HorizontalDividerCard()
            Spacer(Modifier.size(8.dp))
            TitleItem(
                "$ ${viewModel.formatPrice(balance)}",
                if (positiveBalance) PositiveBalanceColor else NegativeBalanceColor
            )
            Spacer(Modifier.size(8.dp))
            BodyTextItem(
                "${stringResource(R.string.work_done)}: $ ${
                    viewModel.formatPrice(
                        totalAmountInWorkDone
                    )
                } "
            )
            Spacer(Modifier.size(8.dp))
            BodyTextItem(
                "${stringResource(R.string.cash_received)}: $ ${
                    viewModel.formatPrice(
                        cashAmount
                    )
                }"
            )
            Spacer(Modifier.size(8.dp))
            BodyTextItem(
                "${stringResource(R.string.checks_received)}: $ ${
                    viewModel.formatPrice(
                        checkAmount
                    )
                }"
            )
        }
    }
    ConfirmDialog(showConfirmDialog, stringResource(R.string.confirm_delete_all_data),
        onAccept = {
            showConfirmDialog = false
            viewModel.clearAllDataHomeScreen()
        },
        onDismiss = {
            showConfirmDialog = false
        })
}

@Composable
private fun PaymentReceivedCardItem(
    paymentReceivedList: State<List<PaymentDataClass>>,
    viewModel: TallerViewModel,
) {

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    val cardSize by animateDpAsState(if(expanded) 380.dp else 200.dp)

    Card(
        Modifier
            .fillMaxWidth()
            .height(cardSize),
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
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                TitleItem(
                    text = stringResource(R.string.payments),
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { expanded = !expanded },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ContrastColor,
                        contentColor = TextColor
                    ),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Icon(if(expanded)Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown , contentDescription = "expand/retract button", tint = TextColor)
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = { showConfirmDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ContrastColor,
                        contentColor = TextColor
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)

                ) {
                    Text(stringResource(R.string.clear))
                }
            }
            Spacer(Modifier.size(4.dp))
            HorizontalDividerCard()
            Spacer(Modifier.size(16.dp))
            LazyColumn(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (paymentReceivedList.value.isEmpty()) {
                    item() {
                        Text(stringResource(R.string.no_payments_found))
                    }
                } else {
                    items(paymentReceivedList.value) {
                        PaymentCardItem(it, viewModel)
                    }
                }
            }
        }
    }

    ConfirmDialog(
        showConfirmDialog, stringResource(R.string.confirm_delete_payment_data),
        onDismiss = { showConfirmDialog = false },
        onAccept = {
            showConfirmDialog = false
            viewModel.clearAllPaymentData()
        }
    )
}

@Composable
fun PaymentCardItem(payment: PaymentDataClass, viewModel: TallerViewModel) {

    var showDialog by rememberSaveable { mutableStateOf(false) }

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyTextItem(payment.method, Modifier.weight(.5f))
        Spacer(Modifier.width(8.dp))
        BodyTextItem("$ ${payment.formatNumber(payment.amount)}", Modifier.weight(.3f))
        Spacer(Modifier.width(16.dp))
        Icon(
            Icons.Filled.Edit,
            contentDescription = "edit work",
            tint = Color.White,
            modifier = Modifier.clickable {
                showDialog = true
            }
        )
        EditPaymentDialog(
            show = showDialog,
            viewModel,
            paymentData = payment,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
private fun WorkDoneCardItem(
    workDoneList: State<List<WorkDoneDataClass>>,
    onWorkDoneDataModified: (WorkDoneDataClass) -> Unit,
    onDeletedButtonClicked: (WorkDoneDataClass) -> Unit,
    onClearDataButtonClicked: () -> Unit
) {

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val cardSize by animateDpAsState(if(expanded) 400.dp else 240.dp)

    Card(
        Modifier
            .fillMaxWidth()
            .height(cardSize),
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
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                TitleItem(text = stringResource(R.string.work_done), modifier = Modifier.weight(1f))
                Button(
                    onClick = { expanded = !expanded },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ContrastColor,
                        contentColor = TextColor
                    ),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Icon(if(expanded)Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown , contentDescription = "expand/retract button", tint = TextColor)
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = { showConfirmDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ContrastColor,
                        contentColor = TextColor
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)

                ) {
                    Text(stringResource(R.string.clear))
                }
            }
            Spacer(Modifier.size(4.dp))
            HorizontalDividerCard()
            Spacer(Modifier.size(16.dp))
            LazyColumn(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (workDoneList.value.isEmpty()) {
                    item() {
                        Text(stringResource(R.string.no_works_found))
                    }
                } else {
                    items(workDoneList.value) { workDoneDataClass ->
                        WorkDoneItem(workDoneDataClass,
                            onAcceptModifyButtonClicked = { workDoneDataModified ->
                                onWorkDoneDataModified(workDoneDataModified)
                            },
                            onDeletedButtonClicked = {
                                onDeletedButtonClicked(workDoneDataClass)
                            })
                    }
                }
            }

        }
    }
    ConfirmDialog(
        show = showConfirmDialog,
        text = stringResource(R.string.confirm_delete_work_done_data),
        onDismiss = { showConfirmDialog = false },
        onAccept = {
            showConfirmDialog = false
            onClearDataButtonClicked()
        }
    )
}

@Composable
fun WorkDoneItem(
    workDoneDataClass: WorkDoneDataClass,
    onAcceptModifyButtonClicked: (WorkDoneDataClass) -> Unit,
    onDeletedButtonClicked: () -> Unit
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var quantityText by rememberSaveable { mutableStateOf(workDoneDataClass.quantity.toString()) }


    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyTextItem(workDoneDataClass.workDataClass.description, Modifier.weight(1f))
        BodyTextItem("x ${workDoneDataClass.quantity}", Modifier.weight(.3f))
        Spacer(Modifier.width(16.dp))
        BodyTextItem(
            "$ ${workDoneDataClass.formatNumber(workDoneDataClass.getTotalPrice())}",
            Modifier.weight(.5f)
        )
        Spacer(Modifier.width(16.dp))
        Icon(
            Icons.Filled.Edit,
            contentDescription = "edit work",
            tint = Color.White,
            modifier = Modifier.clickable {
                showDialog = true
            }
        )
        EditWorkDoneDialog(
            showDialog,
            quantityText,
            workDoneToEdit = workDoneDataClass,
            onDismiss = {
                showDialog = false
                quantityText = workDoneDataClass.quantity.toString()
            },
            onQuantityChange = { quantityText = it },
            onAcceptButtonClicked = {
                if (quantityText.isNotEmpty()) {
                    onAcceptModifyButtonClicked(
                        workDoneDataClass.copy(
                            quantity = quantityText.toInt(),
                        )
                    )
                    showDialog = false
                }
            },
            onDeleteButtonClick = {
                onDeletedButtonClicked()
                showDialog = false
            }
        )
    }
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
        containerColor = ContrastColor,
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
        modifier = Modifier.background(ContrastColor)
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

