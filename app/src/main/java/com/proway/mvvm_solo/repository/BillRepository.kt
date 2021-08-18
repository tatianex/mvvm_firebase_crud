package com.proway.mvvm_solo.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.proway.mvvm_solo.model.Bill

const val BILLS_COLLECTION = "Bills"

class BillRepository {

    private val dataBase = FirebaseFirestore.getInstance()

    fun fetchBills(callback: (List<Bill>?, String?) -> Unit) {
        dataBase.collection(BILLS_COLLECTION)
            .get()
            .addOnSuccessListener { result ->

                val listOf = arrayListOf<Bill>()
                result.forEach {
                    val account = Bill.fromData(it)
                    listOf.add(account)
                }
                callback(listOf, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun addBill(bill: Bill, callback: (Bill?, String?) -> Unit) {
        dataBase.collection(BILLS_COLLECTION)
            .add(bill)
            .addOnSuccessListener {
                Bill.fromDocument(it).apply {
                    callback(this, null)
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun deleteBill(id: String, callback: (Boolean) -> Unit) {
        dataBase.collection(BILLS_COLLECTION)
            .document(id)
            .delete()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}