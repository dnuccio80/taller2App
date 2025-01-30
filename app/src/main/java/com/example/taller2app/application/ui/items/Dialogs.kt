package com.example.taller2app.application.ui.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.example.taller2app.R
import com.example.taller2app.application.ui.dataClasses.PaymentDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import com.example.taller2app.application.ui.TallerViewModel
import com.example.taller2app.application.ui.dataClasses.AnnotationsDataClass
import com.example.taller2app.application.ui.sealedClasses.AvailablePaymentMethods
import com.example.taller2app.ui.theme.AcceptButtonColor
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.CardBackground
import com.example.taller2app.ui.theme.ContrastColor
import com.example.taller2app.ui.theme.DeleteButtonColor
import com.example.taller2app.ui.theme.TextColor
import kotlin.math.max

@Composable
fun NewPaymentDialog(show: Boolean, viewModel: TallerViewModel, onDismiss: () -> Unit) {

    val amountValue by viewModel.amountPaymentValue.collectAsState()
    val paymentValue by viewModel.paymentMethodValue.collectAsState()

    if (show) {
        Dialog(
            onDismissRequest = {
                onDismiss()
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
                    PaymentMethodGroup(paymentValue) { viewModel.updatePaymentMethodValue(it) }
                    Spacer(Modifier.size(16.dp))
                    AcceptDeclineButtons(
                        acceptText = stringResource(R.string.accept),
                        declineText = stringResource(R.string.decline),
                        onDecline = {
                            onDismiss()
                        },
                        onAccept = {
                            if (viewModel.hasAllCorrectFields(
                                    amount = amountValue,
                                    listValue = paymentValue
                                )
                            ) {
                                viewModel.addNewPayment(
                                    PaymentDataClass(
                                        method = paymentValue,
                                        amount = amountValue.toInt()
                                    )
                                )
                                onDismiss()
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun EditPaymentDialog(
    show: Boolean,
    viewModel: TallerViewModel,
    paymentData: PaymentDataClass,
    onDismiss: () -> Unit
) {

    var amountValue by rememberSaveable { mutableStateOf(paymentData.amount.toString()) }
    var paymentValue by rememberSaveable { mutableStateOf(paymentData.method) }


    if (show) {
        Dialog(
            onDismissRequest = {
                onDismiss()
                amountValue = paymentData.amount.toString()
                paymentValue = paymentData.method
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
                        stringResource(R.string.edit_payment),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(8.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    CashAmountTextField(
                        amountValue,
                        viewModel
                    ) { amountValue = it }
                    Spacer(Modifier.size(16.dp))
                    PaymentMethodGroup(paymentValue) { paymentValue = it }
                    Spacer(Modifier.size(16.dp))
                    AcceptDeclineButtons(
                        acceptText = stringResource(R.string.accept),
                        declineText = stringResource(R.string.delete),
                        acceptContainerColor = AcceptButtonColor,
                        declineContainerColor = DeleteButtonColor,
                        onDecline = {
                            viewModel.deletePayment(paymentData)
                            onDismiss()
                            amountValue = paymentData.amount.toString()
                            paymentValue = paymentData.method
                        },
                        onAccept = {
                            if (viewModel.hasAllCorrectFields(
                                    amount = amountValue,
                                    listValue = paymentValue
                                )
                            ) {
                                viewModel.updatePayment(
                                    paymentData.copy(
                                        amount = amountValue.toInt(),
                                        method = paymentValue
                                    )
                                )
                                onDismiss()
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
        RadioButtonItem(
            selectedValue = paymentValue,
            description = AvailablePaymentMethods.Cash.paymentMethod
        ) { onValueChange(it) }
        RadioButtonItem(
            selectedValue = paymentValue,
            description = AvailablePaymentMethods.Check.paymentMethod
        ) { onValueChange(it) }
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
    quantityText: String,
    workDoneToEdit: WorkDoneDataClass,
    onDismiss: () -> Unit,
    onQuantityChange: (String) -> Unit,
    onAcceptButtonClicked: () -> Unit,
    onDeleteButtonClick: () -> Unit
) {


    if (show) {
        Dialog(
            onDismissRequest = {
                onDismiss()
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
                        stringResource(R.string.edit_work_done),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(8.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    EditedWorkName(workDoneToEdit.workDataClass.description)
                    Spacer(Modifier.size(16.dp))
                    WorkQuantityTextFieldItem(
                        quantityText,
                        onValueChange = { onQuantityChange(it) }
                    )
                    Spacer(Modifier.size(32.dp))
                    AcceptDeclineButtons(
                        acceptText = stringResource(R.string.modify),
                        declineText = stringResource(R.string.delete),
                        acceptContainerColor = AcceptButtonColor,
                        declineContainerColor = DeleteButtonColor,
                        onAccept = { onAcceptButtonClicked() },
                        onDecline = { onDeleteButtonClick() }
                    )
                }
            }
        }
    }

}

@Composable
fun EditedWorkName(workDescriptionName: String) {
    TextField(
        value = workDescriptionName,
        onValueChange = {},
        placeholder = { Text(text = stringResource(R.string.search_work)) },
        enabled = false,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = CardBackground,
            disabledTextColor = Color.Magenta,
            disabledIndicatorColor = ButtonColor
        )
    )
}

@Composable
fun AddNewWorkDoneDialog(
    show: Boolean,
    viewModel: TallerViewModel,
    workSelected: String,
    workQuantity: String,
    onDismiss: () -> Unit,
    onQuantityChange: (String) -> Unit,
    onAcceptButtonClicked: () -> Unit,
    onWorkChange: (WorkDataClass) -> Unit
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
                    SelectWorkTextField(
                        viewModel,
                        workSelected,
                    ) { onWorkChange(it) }
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
fun SelectWorkTextField(
    viewModel: TallerViewModel,
    workSelectedValue: String,
    onWorkChange: (WorkDataClass) -> Unit
) {

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
                disabledTextColor = TextColor,
                disabledIndicatorColor = ButtonColor
            ),
            modifier = Modifier.clickable {
                viewModel.updateShowWorkDoneDropdownMenu(true)
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
                onWorkChange(it)
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
        modifier = Modifier
            .background(ContrastColor)
            .heightIn(max = 200.dp)
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
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
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
    descriptionText:String,
    unitPriceText:String,
    lastModifText:String,
    onDescriptionChange:(String) -> Unit,
    onUnitPriceChange:(String) -> Unit,
    onDismiss: () -> Unit,
    onAcceptButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit
) {
    if (show) {
        Dialog(
            onDismissRequest = {
                onDismiss()
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
                        stringResource(R.string.edit_work),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(4.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    TextField(
                        value = descriptionText,
                        onValueChange = { onDescriptionChange(it) },
                        placeholder = { Text(stringResource(R.string.work_description)) },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
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
                        declineText = stringResource(R.string.delete),
                        declineContainerColor = DeleteButtonColor,
                        acceptContainerColor = AcceptButtonColor,
                        onAccept = { onAcceptButtonClick() },
                        onDecline = { onDeleteButtonClick() }
                    )
                    Spacer(Modifier.size(16.dp))
                    Text("Last modif: $lastModifText", color = ButtonColor)
                }
            }
        }
    }
}

@Composable
fun NewAnnotationsDialog(
    show: Boolean,
    titleText: String,
    descriptionText: String,
    onDismiss: () -> Unit,
    onTitleChange: (String) -> Unit,
    onAcceptButtonClicked: () -> Unit,
    onDescriptionChange: (String) -> Unit
) {


    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() },
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
                        stringResource(R.string.new_annotation),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(4.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    TextField(
                        value = titleText,
                        onValueChange = { onTitleChange(it) },
                        label = { Text(stringResource(R.string.annotation_title)) },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CardBackground,
                            unfocusedContainerColor = CardBackground,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray,
                            focusedLabelColor = ContrastColor,
                            unfocusedLabelColor = TextColor
                        ),
                        singleLine = true
                    )
                    TextField(
                        value = descriptionText,
                        onValueChange = {
                            onDescriptionChange(it)
                        },
                        label = { Text(stringResource(R.string.description)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CardBackground,
                            unfocusedContainerColor = CardBackground,
                            focusedLabelColor = ContrastColor,
                            unfocusedLabelColor = TextColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        maxLines = 5
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
fun EditAnnotationDialog(
    show: Boolean,
    titleText: String,
    descriptionText: String,
    onDismiss: () -> Unit,
    onAcceptButtonClicked: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit
) {

    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() },
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
                    TextField(
                        value = titleText,
                        onValueChange = { onTitleChange(it) },
                        placeholder = { Text(stringResource(R.string.annotation_title)) },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CardBackground,
                            unfocusedContainerColor = CardBackground,
                            focusedPlaceholderColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.Gray,
                            focusedLabelColor = ContrastColor,
                            unfocusedLabelColor = TextColor
                        ),
                        singleLine = true
                    )
                    HorizontalDividerCard()
                    TextField(
                        value = descriptionText,
                        onValueChange = {
                            onDescriptionChange(it)
                        },
                        placeholder = { Text(stringResource(R.string.description)) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CardBackground,
                            unfocusedContainerColor = CardBackground,
                            focusedLabelColor = ContrastColor,
                            unfocusedLabelColor = TextColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        maxLines = 5
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
fun ConfirmDialog(show: Boolean, text: String, onDismiss: () -> Unit, onAccept: () -> Unit) {
    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() },
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
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text, color = TextColor, textAlign = TextAlign.Center)
                    Spacer(Modifier.size(16.dp))
                    AcceptDeclineButtons(
                        acceptText = stringResource(R.string.accept),
                        declineText = stringResource(R.string.decline),
                        acceptContainerColor = AcceptButtonColor,
                        declineContainerColor = DeleteButtonColor,
                        onAccept = { onAccept() },
                        onDecline = { onDismiss() }
                    )
                }
            }
        }
    }
}

@Composable
fun AddDebitCreditBalanceDialog(
    show: Boolean,
    amountValue: String,
    selectedValue: String,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    onDeleteData: () -> Unit,
    onAmountChange: (String) -> Unit,
    onSelectedValueChange: (String) -> Unit
) {

    var showConfirmDialog by rememberSaveable { mutableStateOf(false) }

    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() },
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
                        .padding(16.dp),
                ) {
                    Text(
                        stringResource(R.string.add_balance),
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.size(8.dp))
                    HorizontalDividerCard()
                    Spacer(Modifier.size(16.dp))
                    TextField(
                        value = amountValue,
                        onValueChange = { onAmountChange(it) },
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
                    Spacer(Modifier.size(16.dp))
                    DebitCreditRadioButtonGroup(selectedValue) { onSelectedValueChange(it) }
                    Spacer(Modifier.size(16.dp))
                    AcceptDeclineButtons(
                        acceptText = stringResource(R.string.accept),
                        declineText = stringResource(R.string.reset),
                        onAccept = { onAccept() },
                        onDecline = { showConfirmDialog = true }
                    )
                }
            }
        }
    }
    ConfirmDialog(
        show = showConfirmDialog,
        text = stringResource(R.string.confirm_delete_credit_debit_balance),
        onDismiss = { showConfirmDialog = false },
        onAccept = {
            showConfirmDialog = false
            onDeleteData()
        }
    )
}

@Composable
fun DebitCreditRadioButtonGroup(selectedValue: String, onSelectedValueChange: (String) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        RadioButtonItem(
            selectedValue = selectedValue,
            description = AvailablePaymentMethods.DebitBalance.paymentMethod
        ) { onSelectedValueChange(AvailablePaymentMethods.DebitBalance.paymentMethod) }
        RadioButtonItem(
            selectedValue = selectedValue,
            description = AvailablePaymentMethods.CreditBalance.paymentMethod
        ) { onSelectedValueChange(AvailablePaymentMethods.CreditBalance.paymentMethod) }
    }
}