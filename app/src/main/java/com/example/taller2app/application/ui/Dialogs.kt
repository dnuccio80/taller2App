package com.example.taller2app.application.ui

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
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.CardBackground
import com.example.taller2app.ui.theme.ContrastColor

@Composable
fun NewPaymentDialog(show: Boolean, onDismiss: () -> Unit) {

    var amountValue by rememberSaveable { mutableStateOf("") }
    var paymentValue by rememberSaveable { mutableStateOf("") }


    if (show) {
        Dialog(
            onDismissRequest = {
                onDismiss()
                amountValue = ""
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
                    CashAmountTextField(amountValue) { amountValue = it }
                    Spacer(Modifier.size(16.dp))
                    PaymentMethodGroup(paymentValue) { paymentValue = it }
                    Spacer(Modifier.size(16.dp))
                    AcceptDeclineButtons(
                        onDecline = {
                            onDismiss()
                            amountValue = ""
                        },
                        onAccept = {
                            if (hasAllCorrectFields(
                                    amount = amountValue,
                                    paymentMethod = paymentValue
                                )
                            ) {
                                onDismiss()
                                amountValue = ""
                                paymentValue = ""
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
fun CashAmountTextField(amountValue: String, onValueChange: (String) -> Unit) {
    TextField(
        value = amountValue,
        onValueChange = { newValue ->
            if (isValidAmount(newValue)) {
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
    titleText:String,
    workQuantity: String,
    onDismiss: () -> Unit,
    onQuantityChange: (String) -> Unit
) {
    if(show){
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
                    SelectWorkTextField()
                    Spacer(Modifier.size(16.dp))
                    WorkQuantityTextFieldItem(
                        workQuantity,
                        onValueChange = { onQuantityChange(it) }
                    )
                    Spacer(Modifier.size(32.dp))
                    AcceptDeclineButtons(
                        onAccept = { },
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
fun SelectWorkTextField() {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var workSelectedValue by rememberSaveable { mutableStateOf("") }

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
            modifier = Modifier.clickable { expanded = true },
            trailingIcon = {
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = "down arrow button",
                    tint = Color.White
                )
            }
        )
        SelectWorkDropdownMenu(
            expanded,
            onDismiss = { expanded = false },
            onItemSelectedChange = {
                workSelectedValue = it
                expanded = false
            })
    }
}

@Composable
fun SelectWorkDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onItemSelectedChange: (String) -> Unit
) {

    val list = listOf("Tubo torneado", "Pintura", "Manzana")

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.background(ContrastColor)
    ) {
        list.forEach {
            DropdownMenuItem(
                text = { Text(it) },
                onClick = { onItemSelectedChange(it) }
            )
        }

    }
}


fun isValidAmount(amount: String): Boolean {
    return amount.matches(Regex("\\d+,\\d*")) ||
            amount.isDigitsOnly()
}

fun hasAllCorrectFields(amount: String, paymentMethod: String): Boolean {
    return amount.isNotEmpty() && paymentMethod.isNotEmpty()
}
