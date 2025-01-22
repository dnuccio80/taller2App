package com.example.taller2app.application.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taller2app.application.domain.works.AddNewWorkUseCase
import com.example.taller2app.application.domain.works.DeleteWorkUseCase
import com.example.taller2app.application.domain.works.GetAllWorkListUseCase
import com.example.taller2app.application.domain.works.UpdateWorkUseCase
import com.example.taller2app.application.domain.worksDone.AddNewWorkDoneUseCase
import com.example.taller2app.application.domain.worksDone.DeleteWorkDoneUseCase
import com.example.taller2app.application.domain.worksDone.GetAllWorkDoneListUseCase
import com.example.taller2app.application.domain.worksDone.UpdateWorkDoneUseCase
import com.example.taller2app.application.ui.dataClasses.PaymentReceivedDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Thread.State
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TallerViewModel @Inject constructor(
    // Work
    getAllWorkListUseCase: GetAllWorkListUseCase,
    private val addNewWorkUseCase: AddNewWorkUseCase,
    private val updateWorkUseCase: UpdateWorkUseCase,
    private val deleteWorkUseCase: DeleteWorkUseCase,

    // Work Done
    getAllWorkDoneListUseCase: GetAllWorkDoneListUseCase,
    private val addNewWorkDoneUseCase: AddNewWorkDoneUseCase,
    private val updateWorkDoneUseCase: UpdateWorkDoneUseCase,
    private val deleteWorkDoneUseCase: DeleteWorkDoneUseCase,

    ) : ViewModel() {

    // Testing values

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

    private val _showAddNewWorkDoneDialog = MutableStateFlow(false)
    val showAddNewWorkDoneDialog: StateFlow<Boolean> = _showAddNewWorkDoneDialog

    private val _quantityEditedWork = MutableStateFlow("")
    val quantityEditedWork: StateFlow<String> = _quantityEditedWork

    private val _workDoneList = getAllWorkDoneListUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val workDoneList: StateFlow<List<WorkDoneDataClass>> = _workDoneList

    private val _paymentReceivedList = MutableStateFlow<List<PaymentReceivedDataClass>>(paymentList)
    val paymentReceivedList: StateFlow<List<PaymentReceivedDataClass>> = _paymentReceivedList

    // Work List Screen

    private val _workList = getAllWorkListUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val workList = _workList

    private val _searchWorkText = MutableStateFlow("")
    val searchWorkText: StateFlow<String> = _searchWorkText

    private val _addNewWorkDescription = MutableStateFlow("")
    val addNewWorkDescription: StateFlow<String> = _addNewWorkDescription

    private val _unitPriceNewWorkText = MutableStateFlow("")
    val unitPriceNewWorkText: StateFlow<String> = _unitPriceNewWorkText

    private val _showAddNewWorkDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showAddNewWorkDialog: StateFlow<Boolean> = _showAddNewWorkDialog


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
        _showAddNewWorkDoneDialog.value = value
    }

    fun updateQuantityEditedWork(value: String) {
        _quantityEditedWork.value = value
    }

    fun addNewWorkDone(work: WorkDoneDataClass){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("Damian", "new work done added: $work")
            addNewWorkDoneUseCase(work)
        }
    }

    fun addPaymentReceived(payment: PaymentReceivedDataClass) {
        paymentList.add(payment)
        _paymentReceivedList.value = paymentList
    }

    fun deletePaymentDone(payment: PaymentReceivedDataClass){
        paymentList.remove(payment)
    }

    // Work List Screen

    fun updateShowAddNewWorkDialog(value: Boolean) {
        _showAddNewWorkDialog.value = value
    }

    fun updateSearchWorkText(value: String) {
        _searchWorkText.value = value
    }

    fun updateAddNewWorkDescription(value: String) {
        _addNewWorkDescription.value = value
    }

    fun updateUnitPriceNewWorkText(value:String){
        _unitPriceNewWorkText.value = value
    }

    fun addNewWork(description:String,unitPrice:Int){
        viewModelScope.launch(Dispatchers.IO) {
            addNewWorkUseCase(description,unitPrice)
        }
    }

    fun updateWorkInWorkList(workDataClass: WorkDataClass){
        viewModelScope.launch(Dispatchers.IO) {
            updateWorkUseCase(workDataClass)
        }
    }

    fun deleteWorkInWorkList(workDataClass: WorkDataClass){
        viewModelScope.launch(Dispatchers.IO) {
            deleteWorkUseCase(workDataClass)
        }
    }

    fun clearAddWorkListDialogData(){
        _addNewWorkDescription.value = ""
        _unitPriceNewWorkText.value = ""
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

    fun clearAddNewWorkDataDialog(){
        updateWorkSelectedValue("")
        updateQuantityEditedWork("")
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