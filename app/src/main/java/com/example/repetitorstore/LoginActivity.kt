package com.example.repetitorstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
/*
Активити для входа с помощью пароля и почты
Можно перейти в активити с регистрацией
 */

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //ПРИЛОЖЕНИЕ НА ВЕСЬ ЭКРАН/////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ///////////////////////////////////////////////

        mAuth = FirebaseAuth.getInstance()

        //ПЕРЕХОД НА РЕГИСТРАЦИЮ////////////////////////////////////////////////
        id_login_button_registr.setOnClickListener {
            startActivity(Intent(this,RegistrActivity::class.java))
        }
        ////////////////////////////////////////////////////////////////////////


        //ОБРАБОТКА ВХОДА С ПОМОЩЬЮ ФУНКЦИИ///////////////////
        id_login_button_input.setOnClickListener {
            InputUser()
        }
        //////////////////////////////////////////////////////

    }
    //ФУНКЦИЯ ВХОДА ЗАРЕГИСТРИРОВАННОГО ПОЛЬЗОВАТЕЛЯ////////////////////////////////////////////////
    private fun InputUser(){
        val email = id_login_et_email.text.toString()
        val password = id_login_et_password.text.toString()

            mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    }else{
                        Toast.makeText(this, "ошибка:"+task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }
    ////////////////////////////////////////////////////////////////////////////////////////////////

}