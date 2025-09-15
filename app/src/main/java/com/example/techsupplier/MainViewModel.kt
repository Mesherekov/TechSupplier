package com.example.techsupplier

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class MainViewModel: ViewModel() {
    val firestore = Firebase.firestore
    val firebaseAuth = FirebaseAuth.getInstance()

    fun addDetail(detail: Detail){
        firestore.collection("Company")
            .document(firebaseAuth.uid.hashCode().toString())
            .set(detail)
    }

    fun getAllDetails(){
    }
}