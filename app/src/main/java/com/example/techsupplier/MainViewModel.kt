package com.example.techsupplier

import android.widget.Toast
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import com.google.api.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow

class MainViewModel: ViewModel() {
    val firestore = Firebase.firestore
    val firebaseAuth = Firebase.auth
    private val _stateFlowProfile = MutableStateFlow<Company>(Company())
    val profileState: StateFlow<Company> = _stateFlowProfile.asStateFlow()

    fun addDetail(detail: Detail){
        firestore.collection("Company")
            .document(firebaseAuth.uid.toString())
            .set(detail)
    }

    fun getAllDetails(){
        firestore.collection(Paths.COMPANY).document().addSnapshotListener { i, item ->

        }
    }
    fun getProfile(uid: String){
        firestore.collection(Paths.COMPANY).document(uid).get().addOnCompleteListener { snapshot ->
            _stateFlowProfile.value = snapshot.result.toObject(Company::class.java) ?: Company(name = "Error")
        }
    }

}