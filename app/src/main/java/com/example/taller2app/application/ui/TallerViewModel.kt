package com.example.taller2app.application.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taller2app.application.domain.annotations.AddAnnotationUseCase
import com.example.taller2app.application.domain.annotations.DeleteAnnotationUseCase
import com.example.taller2app.application.domain.annotations.GetAllAnnotationsUseCase
import com.example.taller2app.application.domain.annotations.UpdateAnnotationUseCase
import com.example.taller2app.application.domain.payments.AddNewPaymentUseCase
import com.example.taller2app.application.domain.payments.DeletePaymentUseCase
import com.example.taller2app.application.domain.payments.GetAllPaymentsUseCase
import com.example.taller2app.application.domain.payments.UpdatePaymentUseCase
import com.example.taller2app.application.domain.works.AddNewWorkUseCase
import com.example.taller2app.application.domain.works.DeleteWorkUseCase
import com.example.taller2app.application.domain.works.GetAllWorkListUseCase
import com.example.taller2app.application.domain.works.UpdateWorkUseCase
import com.example.taller2app.application.domain.worksDone.AddNewWorkDoneUseCase
import com.example.taller2app.application.domain.worksDone.DeleteWorkDoneUseCase
import com.example.taller2app.application.domain.worksDone.GetAllWorkDoneListUseCase
import com.example.taller2app.application.domain.worksDone.UpdateWorkDoneUseCase
import com.example.taller2app.application.ui.dataClasses.AnnotationsDataClass
import com.example.taller2app.application.ui.dataClasses.PaymentDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    // Payments
    getAllPaymentsUseCase: GetAllPaymentsUseCase,
    private val addNewPaymentUseCase: AddNewPaymentUseCase,
    private val updatePaymentUseCase: UpdatePaymentUseCase,
    private val deletePaymentUseCase: DeletePaymentUseCase,

    // Annotations
    getAllAnnotationsUseCase: GetAllAnnotationsUseCase,
    private val addAnnotationUseCase: AddAnnotationUseCase,
    private val updateAnnotationUseCase: UpdateAnnotationUseCase,
    private val deleteAnnotationUseCase: DeleteAnnotationUseCase,

    ) : ViewModel() {


    // <--------------------- Vals --------------------->

    // Home Screen

    private val _showPaymentDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showPaymentDialog: StateFlow<Boolean> = _showPaymentDialog

    private val _showAddNewWorkDoneDialog = MutableStateFlow(false)
    val showAddNewWorkDoneDialog: StateFlow<Boolean> = _showAddNewWorkDoneDialog

    private val _quantityEditedWork = MutableStateFlow("")
    val quantityEditedWork: StateFlow<String> = _quantityEditedWork

    private val _workDoneList = getAllWorkDoneListUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val workDoneList: StateFlow<List<WorkDoneDataClass>> = _workDoneList

    private val _paymentReceivedList = getAllPaymentsUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val paymentReceivedList: StateFlow<List<PaymentDataClass>> = _paymentReceivedList

    val totalPaymentReceivedByCategory = _paymentReceivedList.map { list ->
        list.groupBy { it.method }
            .mapValues { (_, payments) ->
                payments.sumOf { it.amount }
            }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap()
    )

    val totalAmountInWorkDone = _workDoneList.map { list ->
        list.sumOf { it.totalPrice }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), 0
    )


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

    // Annotations Screen

    private val _annotationsList = getAllAnnotationsUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val annotationsList = _annotationsList


    // BottomBar

    private val _indexBottomBarSelected = MutableStateFlow(0)
    val indexBottomBarSelected: StateFlow<Int> = _indexBottomBarSelected

    // Dialogs

    // << ShowPaymentDialog >>

    private val _amountPaymentValue = MutableStateFlow("")
    val amountPaymentValue: StateFlow<String> = _amountPaymentValue

    private val _paymentMethodValue = MutableStateFlow("")
    val paymentMethodValue: StateFlow<String> = _paymentMethodValue

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

    fun updateShowAddWorkDialog(value: Boolean) {
        _showAddNewWorkDoneDialog.value = value
    }

    fun updateQuantityEditedWork(value: String) {
        _quantityEditedWork.value = value
    }

    fun addNewWorkDone(work: WorkDoneDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("Damian", "new work done added: $work")
            addNewWorkDoneUseCase(work)
        }
    }

    fun updateWorkDone(work: WorkDoneDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            updateWorkDoneUseCase(work)
        }
    }

    fun deleteWorkDone(work: WorkDoneDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteWorkDoneUseCase(work)
        }
    }

    fun addNewPayment(payment: PaymentDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            addNewPaymentUseCase(payment)
        }
    }

    fun updatePayment(payment: PaymentDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            updatePaymentUseCase(payment)
        }
    }

    fun deletePayment(payment: PaymentDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePaymentUseCase(payment)
        }
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

    fun updateUnitPriceNewWorkText(value: String) {
        _unitPriceNewWorkText.value = value
    }

    fun addNewWork(description: String, unitPrice: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            addNewWorkUseCase(description, unitPrice)
        }
    }

    fun updateWorkInWorkList(workDataClass: WorkDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            updateWorkUseCase(workDataClass)
        }
    }

    fun deleteWorkInWorkList(workDataClass: WorkDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteWorkUseCase(workDataClass)
        }
    }

    fun clearAddWorkListDialogData() {
        _addNewWorkDescription.value = ""
        _unitPriceNewWorkText.value = ""
    }

    // Annotations Screen

    fun addAnnotation(annotation:AnnotationsDataClass){
        viewModelScope.launch(Dispatchers.IO) {
            addAnnotationUseCase(annotation)
        }
    }

    fun updateAnnotation(annotation: AnnotationsDataClass){
        viewModelScope.launch(Dispatchers.IO) {
            updateAnnotationUseCase(annotation)
        }
    }

    fun deleteAnnotation(annotation: AnnotationsDataClass){
        viewModelScope.launch(Dispatchers.IO) {
            deleteAnnotationUseCase(annotation)
        }
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

    fun updatePaymentMethodValue(value: String) {
        _paymentMethodValue.value = value
    }

    fun clearPaymentDialogData() {
        updateAmountPaymentValue("")
        updatePaymentMethodValue("")
    }


    // << Edit Work Done >>

    fun updateShowWorkDoneDropdownMenu(value: Boolean) {
        _showWorkDropdownMenu.value = value
    }

    fun updateWorkSelectedValue(value: String) {
        _workSelectedValue.value = value
    }

    fun clearAddNewWorkDataDialog() {
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

    fun formatPrice(value: Int): String {
        val formatter = NumberFormat.getInstance(Locale("es", "AR"))
        return formatter.format(value)
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