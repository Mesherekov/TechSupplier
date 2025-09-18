package com.example.techsupplier

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    val firestore = Firebase.firestore
    val firebaseAuth = Firebase.auth
    private val _stateFlowProfile = MutableStateFlow(Company())
    val profileState: StateFlow<Company> = _stateFlowProfile.asStateFlow()

    private val _stateFlowDetails = MutableStateFlow< List<Detail>>(emptyList())
    val detailsState: StateFlow<List<Detail>> = _stateFlowDetails.asStateFlow()

    fun addDetail(detail: Detail){
        firestore.collection("Company")
            .document(firebaseAuth.uid.toString())
            .set(detail)
    }

    fun getAllDetails(){
        firestore.collection(Paths.DETAILS).addSnapshotListener { snapshot, ex ->
            _stateFlowDetails.value = snapshot?.toObjects(Detail::class.java) ?: emptyList()
        }
    }
    fun getProfile(uid: String){
        firestore.collection(Paths.COMPANY).document(uid).get().addOnCompleteListener { snapshot ->
            _stateFlowProfile.value = snapshot.result.toObject(Company::class.java) ?: Company(name = "Error")
        }
    }

}