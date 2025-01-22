package com.example.taller2app.application.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.example.taller2app.R
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.ui.theme.AcceptButtonColor
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.CardBackground
import com.example.taller2app.ui.theme.ContrastColor
import com.example.taller2app.ui.theme.DeleteButtonColor

@Composable
fun NewPaymentDialog(show: Boolean, viewModel: TallerViewModel, onDismiss: () -> Unit) {

    val amountValue by viewModel.amountPaymentValue.collectAsState()
    val paymentValue by viewModel.paymentMethod.collectAsState()

    if (show) {
        Dialog(
            onDismissRequest = {
                onDismiss()
                viewModel.updateAmountPaymentValue("")
            }
        ) {
            Card(
                Modifier
                    .width(250.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(
                    1.dp,
                    color = ButtonColor
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.new_payment),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(8.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    CashAmountTextField(
                        amountValue,
                        viewModel
                    ) { viewModel.updateAmountPaymentValue(it) }
                    Spacer(Modifier.size(16.dp))
                    PaymentMethodGroup(paymentValue) { viewModel.updatePaymentMethod(it) }
                    Spacer(Modifier.size(16.dp))
                    AcceptDeclineButtons(
                        acceptText = stringResource(R.string.accept),
                        declineText = stringResource(R.string.decline),
                        onDecline = {
                            onDismiss()
                            viewModel.updateAmountPaymentValue("")
                        },
                        onAccept = {
                            if (viewModel.hasAllCorrectFields(
                                    amount = amountValue,
                                    listValue = paymentValue
                                )
                            ) {
                                onDismiss()
                                viewModel.updateAmountPaymentValue("")
                                viewModel.updatePaymentMethod("")
                            }
                        })
                }
            }
        }
    }

}

@Composable
fun PaymentMethodGroup(paymentValue: String, onValueChange: (String) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        RadioButtonItem(paymentValue, stringResource(R.string.cash)) { onValueChange(it) }
        RadioButtonItem(paymentValue, stringResource(R.string.checks)) { onValueChange(it) }
    }
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

@Composable
fun CashAmountTextField(
    amountValue: String,
    viewModel: TallerViewModel,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = amountValue,
        onValueChange = { newValue ->
            if (viewModel.isValidPrice(newValue)) {
                onValueChange(newValue)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        placeholder = { Text(text = stringResource(R.string.enter_amount)) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CardBackground,
            unfocusedContainerColor = CardBackground,
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_cash),
                contentDescription = "money icon",
                tint = Color.White
            )
        },
    )
}

@Composable
fun EditWorkDoneDialog(
    show: Boolean,
    viewModel: TallerViewModel,
    acceptText: String,
    declineText: String,
    workSelected: String,
    titleText: String,
    workQuantity: String,
    onDismiss: () -> Unit,
    onQuantityChange: (String) -> Unit,
    onAcceptButtonClicked: () -> Unit
) {

    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Card(
                Modifier
                    .width(250.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(
                    1.dp,
                    color = ButtonColor
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        titleText,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(8.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    SelectWorkTextField(viewModel, workSelected)
                    Spacer(Modifier.size(16.dp))
                    WorkQuantityTextFieldItem(
                        workQuantity,
                        onValueChange = { onQuantityChange(it) }
                    )
                    Spacer(Modifier.size(32.dp))
                    AcceptDeclineButtons(
                        acceptText = acceptText,
                        declineText = declineText,
                        onAccept = { onAcceptButtonClicked() },
                        onDecline = { onDismiss() }
                    )
                }
            }
        }
    }

}

@Composable
fun AddNewWorkDoneDialog(
    show: Boolean,
    viewModel: TallerViewModel,
    workSelected: String,
    workQuantity: String,
    onDismiss: () -> Unit,
    onQuantityChange: (String) -> Unit,
    onAcceptButtonClicked: () -> Unit
) {
    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Card(
                Modifier
                    .width(250.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(
                    1.dp,
                    color = ButtonColor
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.add_new_work),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(8.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    SelectWorkTextField(viewModel, workSelected)
                    Spacer(Modifier.size(16.dp))
                    WorkQuantityTextFieldItem(
                        workQuantity,
                        onValueChange = { onQuantityChange(it) }
                    )
                    Spacer(Modifier.size(32.dp))
                    AcceptDeclineButtons(
                        acceptText = stringResource(R.string.accept),
                        declineText = stringResource(R.string.decline),
                        onAccept = { onAcceptButtonClicked() },
                        onDecline = { onDismiss() }
                    )
                }
            }
        }
    }
}

@Composable
fun WorkQuantityTextFieldItem(workQuantity: String, onValueChange: (String) -> Unit) {

    TextField(value = workQuantity,
        onValueChange = {
            if (it.isDigitsOnly() && it.length <= 4) {
                onValueChange(it)
            }
        },
        label = { Text(stringResource(R.string.quantity)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CardBackground,
            unfocusedContainerColor = CardBackground,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        leadingIcon = {
            Icon(
                painterResource(R.drawable.ic_quantity),
                contentDescription = "quantity icon",
                tint = Color.White
            )
        }
    )
}


@Composable
fun SelectWorkTextField(viewModel: TallerViewModel, workSelectedValue: String) {

    val expanded = viewModel.showWorkDropdownMenu.collectAsState()
    val workList = viewModel.workList.collectAsState()

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        TextField(
            value = workSelectedValue,
            onValueChange = {},
            placeholder = { Text(text = stringResource(R.string.search_work)) },
            enabled = false,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = CardBackground,
                disabledTextColor = Color.White,
                disabledIndicatorColor = ButtonColor
            ),
            modifier = Modifier.clickable {
                viewModel.updateShowWorkDoneDropdownMenu(true)
                Log.i("Damian", "${workList.value}")
            },
            trailingIcon = {
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = "down arrow button",
                    tint = Color.White
                )
            }
        )
        SelectWorkDropdownMenu(
            expanded.value,
            workList,
            onDismiss = { viewModel.updateShowWorkDoneDropdownMenu(false) },
            onItemSelectedChange = {
                viewModel.updateWorkSelectedValue(it.description)
                viewModel.updateShowWorkDoneDropdownMenu(false)
            }
        )
    }
}

@Composable
fun SelectWorkDropdownMenu(
    expanded: Boolean,
    workList: State<List<WorkDataClass>>,
    onDismiss: () -> Unit,
    onItemSelectedChange: (WorkDataClass) -> Unit
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.background(ContrastColor)
    ) {
        if (workList.value.isEmpty()) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.no_works_found)) },
                onClick = { onDismiss() }
            )
        } else {
            workList.value.forEach {
                DropdownMenuItem(
                    text = { Text(it.description) },
                    onClick = { onItemSelectedChange(it) }
                )
            }
        }
    }
}

@Composable
fun AddNewWorkDialog(
    show: Boolean,
    addNewWorkDescription: String,
    unitPriceNewWorkText: String,
    onDismiss: () -> Unit,
    onWorkDescriptionChange: (String) -> Unit,
    onUnitPriceChange: (String) -> Unit,
    onAcceptButtonClicked: () -> Unit,
    onDeclineButtonClicked: () -> Unit
) {


    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Card(
                Modifier
                    .width(250.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(
                    1.dp,
                    color = ButtonColor
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.add_new_work),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(4.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    TextField(
                        value = addNewWorkDescription,
                        onValueChange = { onWorkDescriptionChange(it) },
                        placeholder = { Text(stringResource(R.string.work_description)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CardBackground,
                            unfocusedContainerColor = CardBackground,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray
                        ),
                    )
                    TextField(value = unitPriceNewWorkText,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                onUnitPriceChange(it)
                            }
                        },
                        label = { Text(stringResource(R.string.unit_price)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CardBackground,
                            unfocusedContainerColor = CardBackground,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Icon(
                                painterResource(R.drawable.ic_cash),
                                contentDescription = "cash icon",
                                tint = Color.White
                            )
                        }
                    )
                    Spacer(Modifier.size(32.dp))
                    AcceptDeclineButtons(
                        acceptText = stringResource(R.string.accept),
                        declineText = stringResource(R.string.decline),
                        onAccept = { onAcceptButtonClicked() },
                        onDecline = { onDeclineButtonClicked() }
                    )
                }
            }
        }
    }

}

@Composable
fun ModifyWorkInListDialog(
    show: Boolean,
    workDataClass: WorkDataClass,
    onDismiss: () -> Unit,
    onAcceptButtonClick: (WorkDataClass) -> Unit,
    onDeleteButtonClick: (WorkDataClass) -> Unit
) {

    var descriptionText by rememberSaveable { mutableStateOf(workDataClass.description) }
    var unitPriceText by rememberSaveable { mutableStateOf(workDataClass.unitPrice.toString()) }

    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Card(
                Modifier
                    .width(250.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(
                    1.dp,
                    color = ButtonColor
                )
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.edit_work),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(4.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    TextField(
                        value = descriptionText,
                        onValueChange = { descriptionText = it },
                        placeholder = { Text(stringResource(R.string.work_description)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CardBackground,
                            unfocusedContainerColor = CardBackground,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray
                        ),
                    )
                    TextField(value = unitPriceText,
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                unitPriceText = it
                            }
                        },
                        label = { Text(stringResource(R.string.unit_price)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CardBackground,
                            unfocusedContainerColor = CardBackground,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = {
                            Icon(
                                painterResource(R.drawable.ic_cash),
                                contentDescription = "cash icon",
                                tint = Color.White
                            )
                        }
                    )
                    Spacer(Modifier.size(32.dp))
                    AcceptDeclineButtons(
                        acceptText = stringResource(R.string.accept),
                        declineText = stringResource(R.string.delete),
                        declineContainerColor = DeleteButtonColor,
                        acceptContainerColor = AcceptButtonColor,
                        onAccept = {
                            if (descriptionText.isNotEmpty() && unitPriceText.isNotEmpty()) {
                                onAcceptButtonClick(
                                    workDataClass.copy(
                                        description = descriptionText,
                                        unitPrice = unitPriceText.toInt()
                                    )
                                )
                            }
                        },
                        onDecline = { onDeleteButtonClick(workDataClass) }
                    )
                }
            }
        }
    }
}
