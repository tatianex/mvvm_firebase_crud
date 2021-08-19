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
                    val bill = Bill.fromData(it)
                    listOf.add(bill)
                }
                callback(listOf, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun fetchBill(uid: String, callback: (Bill?, String?) -> Unit) {
        dataBase.collection(BILLS_COLLECTION)
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                val bill = Bill.fromDocument(document)
                callback(bill, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun addBill(bill: Bill, callback: (Bill?, String?) -> Unit) {
        dataBase.collection(BILLS_COLLECTION)
            .add(bill)
            .addOnSuccessListener {
                callback(bill.apply {
                    uid = it.id }, null)
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun deleteBill(uid: String, callback: (Boolean) -> Unit) {
        dataBase.collection(BILLS_COLLECTION)
            .document(uid)
            .delete()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}