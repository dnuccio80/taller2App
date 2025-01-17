package com.example.taller2app.application.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.example.taller2app.application.ui.dataClasses.PaymentReceivedDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TallerViewModel @Inject constructor() : ViewModel() {

    // Testing values

    private val listaDeWorkDone = mutableListOf(
        WorkDoneDataClass("Tubo torneado", 15, 4400),
        WorkDoneDataClass("Tubo agujereado", 15, 600),
        WorkDoneDataClass("Cuñas torneadas", 300, 800),
        WorkDoneDataClass("Cuñas agujereadas", 300, 300),
    )

    private val paymentList = mutableListOf(
        PaymentReceivedDataClass("Cash", 890000),
        PaymentReceivedDataClass("Checks", 73000)
    )

    // <--------------------- Vals --------------------->

    // Home Screen

    private val _showPaymentDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showPaymentDialog: StateFlow<Boolean> = _showPaymentDialog

    private val _showEditWorkDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showEditWorkDialog: StateFlow<Boolean> = _showEditWorkDialog

    private val _showAddWorkDialog = MutableStateFlow(false)
    val showAddWorkDialog: StateFlow<Boolean> = _showAddWorkDialog

    private val _quantityEditedWork = MutableStateFlow("")
    val quantityEditedWork: StateFlow<String> = _quantityEditedWork

    private val _workDoneList = MutableStateFlow<List<WorkDoneDataClass>>(listaDeWorkDone)
    val workDoneList: StateFlow<List<WorkDoneDataClass>> = _workDoneList

    private val _paymentReceivedList = MutableStateFlow<List<PaymentReceivedDataClass>>(paymentList)
    val paymentReceivedList: StateFlow<List<PaymentReceivedDataClass>> = _paymentReceivedList

    // Work List Screen

    private val _searchWorkText = MutableStateFlow("")
    val searchWorkText: StateFlow<String> = _searchWorkText

    // BottomBar

    private val _indexBottomBarSelected = MutableStateFlow(0)
    val indexBottomBarSelected: StateFlow<Int> = _indexBottomBarSelected

    // Dialogs

    // << ShowPaymentDialog >>

    private val _amountPaymentValue = MutableStateFlow("")
    val amountPaymentValue: StateFlow<String> = _amountPaymentValue

    private val _paymentMethod = MutableStateFlow("")
    val paymentMethod: StateFlow<String> = _paymentMethod

    // << Edit Work Done >>

    private val _showWorkDropdownMenu = MutableStateFlow(false)
    val showWorkDropdownMenu: StateFlow<Boolean> = _showWorkDropdownMenu

    private val _workSelectedValue = MutableStateFlow("")
    val workSelectedValue: StateFlow<String> = _workSelectedValue


    // <--------------------- Funs --------------------->

    // Home Screen

    fun updateShowPaymentDialog(value: Boolean) {
        _showPaymentDialog.value = value
    }

    fun updateShowEditWorkDialog(value: Boolean) {
        _showEditWorkDialog.value = value
    }

    fun updateShowAddWorkDialog(value: Boolean) {
        _showAddWorkDialog.value = value
    }

    fun updateQuantityEditedWork(value: String) {
        _quantityEditedWork.value = value
    }

    fun addWorkDone(work: WorkDoneDataClass) {
        listaDeWorkDone.add(work)
        _workDoneList.value = listaDeWorkDone
    }

    fun addPaymentReceived(payment: PaymentReceivedDataClass) {
        paymentList.add(payment)
        _paymentReceivedList.value = paymentList
    }

    fun deleteWorkDone(work: WorkDoneDataClass) {
        listaDeWorkDone.remove(work)
        _workDoneList.value = listaDeWorkDone
    }

    fun deletePaymentDone(payment: PaymentReceivedDataClass){
        paymentList.remove(payment)
    }

    // Work List Screen

    fun updateSearchWorkText(value: String) {
        _searchWorkText.value = value
    }

    // Bottom Bar

    fun updateIndexBottomBarSelected(value: Int) {
        _indexBottomBarSelected.value = value
    }

    // Dialogs

    // << ShowPaymentDialog >>

    fun updateAmountPaymentValue(value: String) {
        _amountPaymentValue.value = value
    }

    fun updatePaymentMethod(value: String) {
        _paymentMethod.value = value
    }


    // << Edit Work Done >>

    fun updateShowWorkDoneDropdownMenu(value: Boolean) {
        _showWorkDropdownMenu.value = value
    }

    fun updateWorkSelectedValue(value: String) {
        _workSelectedValue.value = value
    }

    // Corroborate fields

    fun isValidPrice(amount: String): Boolean {
        return amount.matches(Regex("\\d+,\\d*")) ||
                amount.isDigitsOnly()
    }

    fun hasAllCorrectFields(amount: String, listValue: String): Boolean {
        return amount.isNotEmpty() && listValue.isNotEmpty()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalDate(dateModified: Long): String {
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val localDateTime = Instant.ofEpochMilli(dateModified)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        return localDateTime.format(format)
    }




}