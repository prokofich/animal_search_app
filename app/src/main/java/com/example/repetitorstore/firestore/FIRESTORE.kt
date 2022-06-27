package com.example.repetitorstore.firestore

import com.example.repetitorstore.model.USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FIRESTORE {

    private val mFirestore = FirebaseFirestore.getInstance()


    //ФУНКЦИЯ ОТПРАВКИ ПЕРВЫХ ДАННЫХ В БАЗУ///////
    fun registerUser(user: USER) {

        mFirestore.collection("USERS")
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {

            }.addOnFailureListener { exception ->}

    }
    //////////////////////////////////////////////

    //ФУНКЦИЯ ОПРЕДЕЛЕНИЯ ID ПОЛЬЗОВАТЕЛЯ///////////////////////
    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }
    ////////////////////////////////////////////////////////////


}