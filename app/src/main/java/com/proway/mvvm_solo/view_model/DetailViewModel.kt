package com.proway.mvvm_solo.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.proway.mvvm_solo.model.Bill
import com.proway.mvvm_solo.repository.BillRepository

class DetailViewModel : ViewModel() {

    private val _bill = MutableLiveData<Bill>()
    val bill : LiveData<Bill> = _bill

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val billRepository = BillRepository()

    fun GetBill(uid: String) {
        billRepository.fetchBill(uid) { bill, error ->
            if (error != null) {
                    _error.value = error
            }
            else {
                _bill.value = bill
            }
        }
    }

}