package com.proway.mvvm_solo.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot

data class Bill(

    val id: String?,
    val name: String?,
    val price: Double?
) {

    companion object {

        fun fromData(snapshot: QueryDocumentSnapshot): Bill {
            return Bill(
                id = snapshot.id,
                name = snapshot.data["name"] as? String,
                price = snapshot.data["price"] as? Double
            )
        }

        fun fromDocument(doc: DocumentReference): Bill {

            return Bill(
                id = doc.id,
                name = null,
                price = null
            )
        }
    }
}