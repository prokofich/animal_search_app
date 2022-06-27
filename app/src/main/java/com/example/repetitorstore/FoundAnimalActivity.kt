package com.example.repetitorstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_found_animal.*

class FoundAnimalActivity : AppCompatActivity() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_found_animal)

        //ПРИЛОЖЕНИЕ ВО ВЕСЬ ЭКРАН/////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ///////////////////////////////////////////////

        val number = intent.getIntExtra("key1",100)

        //ОТПРАВКА СООБЩЕНИЯ В БАЗУ/////////////////////////////////////////////////////////////////
        id_fa_button.setOnClickListener {

            mFirestore.collection("All_News").document("$number")
                .update("message", "Меня зовут ${id_fa_et_name}."+"Мой номер телефона:${id_fa_et_mobile}. "+id_fa_et.text.toString())
            finish()

        }
        ////////////////////////////////////////////////////////////////////////////////////////////

    }
}