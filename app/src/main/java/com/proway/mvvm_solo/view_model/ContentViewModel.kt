package com.proway.mvvm_solo.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.proway.mvvm_solo.model.Bill
import com.proway.mvvm_solo.repository.AuthenticationRepository
import com.proway.mvvm_solo.repository.BillRepository

class ContentViewModel : ViewModel() {

    private val _bills = MutableLiveData<List<Bill>>()
    val bills: LiveData<List<Bill>> = _bills

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isSignedIn = MutableLiveData<Boolean>(true)
    val isSignedIn: LiveData<Boolean> = _isSignedIn

    private val _user = MutableLiveData<FirebaseUser>()
    val user: LiveData<FirebaseUser> = _user

    private val billsRepository = BillRepository()
    private val authenticationRepository = AuthenticationRepository()


    fun fetchBills() {
        billsRepository.fetchBills { bills, error ->
            if (error != null) {
                _error.value = error
            }
            else {
                _bills.value = bills
            }
        }
    }

    fun addBill(name: String, price: Double?) {
        Bill(null, name, price).apply {
            billsRepository.addBill(this) { _, error ->
                if (error != null) {
                    _error.value = error
                }
                else {
                    fetchBills()
                }
            }
        }
    }

    fun fetchCurrentUser() {
        authenticationRepository.currentUser()?.apply {
            _user.value = this
        }
    }

    fun signOut() {
        authenticationRepository.signOut()
        _isSignedIn.value = false
    }
}