package com.example.repetitorstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.EditText
import com.example.repetitorstore.firestore.FIRESTORE
import com.example.repetitorstore.model.USER
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_registr.*
/*
Активити регистрации с помощью пароля и почты
 */

class RegistrActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registr)

        //ПРИЛОЖЕНИЕ ВО ВЕСЬ ЭКРАН/////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ///////////////////////////////////////////////

        val firstname = findViewById<EditText>(R.id.id_reg_firstname) //ИМЯ
        val fullname = findViewById<EditText>(R.id.id_reg_fullname) //ФАМИЛИЯ
        val email = findViewById<EditText>(R.id.id_reg_email) //ПОЧТА
        val mobile = findViewById<EditText>(R.id.id_reg_mobile) //ТЕЛЕФОН
        val password = findViewById<EditText>(R.id.id_reg_password) //ПАРОЛЬ


        //ОБРАБОТКА НАЖАТИЯ РЕГИСТРАЦИИ///////////////////////////////////////////////////
        id_reg_button_registr.setOnClickListener {
            if (id_reg_email.text.toString()!="" && id_reg_password.text.toString()!=""){
                signUp(id_reg_email.text.toString(),id_reg_password.text.toString()) //ФУНКЦИЯ РЕГИСТРАЦИИ
                startActivity(Intent(this,LoginActivity::class.java)) //ПЕРЕХОД В ПРОФИЛЬ ПОСЛЕ РЕГИСТРАЦИИ
            }
        }
        /////////////////////////////////////////////////////////////////////////////////////

    }

    //ФУНКЦИЯ РЕГИСТРАЦИИ ПОЛЬЗОВАТЕЛЯ//////////////////////////////////////////////////////////////////////
    private fun signUp(email:String, password:String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val userId = firebaseUser.uid

                        val new_user = USER(userId,
                            findViewById<EditText>(R.id.id_reg_firstname).text.toString().trim{it <=' '},
                            findViewById<EditText>(R.id.id_reg_fullname).text.toString().trim{it <=' '},
                            findViewById<EditText>(R.id.id_reg_email).text.toString().trim{it <=' '},
                            findViewById<EditText>(R.id.id_reg_mobile).text.toString().trim{it <=' '}
                        )
                        FIRESTORE().registerUser(new_user)

                    }
                }
            )
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

}