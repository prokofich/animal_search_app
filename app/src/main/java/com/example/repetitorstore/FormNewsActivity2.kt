package com.example.repetitorstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.repetitorstore.firestore.FIRESTORE
import com.example.repetitorstore.model.NEWSAccount
import com.example.repetitorstore.model.NUMBER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_form_news2.*
/*
Активити для редактирования объявления
 */

class FormNewsActivity2 : AppCompatActivity() {

    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_news2)

        //ПРИЛОЖЕНИЕ ВО ВЕСЬ ЭКРАН//////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ////////////////////////////////////////////////

        val number = intent.getIntExtra("key2",-1)
        mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId())
            .collection("$number")
            .document("$number")
            .get()
            .addOnSuccessListener { task ->

                var news = task.toObject(NEWSAccount::class.java)
                id_formnews2_name.setText(news!!.name)// ПОКАЗ СТАРОГО ИМЕНИ ОБЪЯВЛЕНИЯ
                id_formnews2_text.setText(news!!.text)// ПОКАЗ СТАРОГО ТЕКСТА К ОБЪЯВЛЕНИЮ

            }

        //ОБРАБОТКА СОХРАНЕНИЯ ИЗМЕНЕНИЯ///////////////////////////
        id_formnews2_save.setOnClickListener {

            var new_name = id_formnews2_name.text.toString()
            var new_text = id_formnews2_text.text.toString()

            mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId())
                .collection("$number")
                .document("$number")
                .update("name",new_name,"text",new_text)// СОХРАНЕНИЕ НОВОЙ ИНФОРМАЦИИ

            mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId())
                .collection("number_news")
                .document("$number")
                .get()
                .addOnSuccessListener { task ->

                    val number_in_tape = task.toObject(NUMBER::class.java)

                    mFirestore.collection("All_News").document("${number_in_tape!!.number}")
                        .update("name",new_name,"text",new_text)// СОХРАНЕНИЕ НОВОЙ ИНФОРМАЦИИ
                    Toast.makeText(this,"данные изменены",Toast.LENGTH_SHORT).show()
                }
            finish()
        }
        ///////////////////////////////////////////////////////////

    }

}