package com.app.babydogecloudminingapp.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.app.babydogecloudminingapp.R
import com.app.babydogecloudminingapp.model.User
import com.app.babydogecloudminingapp.utill.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {

    val success = MutableLiveData<Boolean>()

    fun doLoginAuth(email: String, password: String,context: Context) {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            success.value = task.isSuccessful

            if (!task.isSuccessful){
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()

            }

        }

    }

    fun doCreateAccountAuth(email: String, password: String, fullName: String, context: Context) {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
                val collectionReference: CollectionReference =
                    firestore.collection(Util.USER_COLLECTION)
                val documentReference: DocumentReference =
                    collectionReference.document(auth.currentUser?.uid.toString())

                val user = User(auth.uid.toString(), email, fullName)

                documentReference.set(user).addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            context.getText(R.string.account_success),
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()
                    }

                    success.value = task.isSuccessful

                }

            } else {

                Toast.makeText(context, task.exception?.message, Toast.LENGTH_LONG).show()
                success.value = task.isSuccessful

            }

        }


    }

}