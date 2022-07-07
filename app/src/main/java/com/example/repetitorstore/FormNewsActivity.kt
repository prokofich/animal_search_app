package com.example.repetitorstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.repetitorstore.firestore.FIRESTORE
import com.example.repetitorstore.fragments.AccountFragment
import com.example.repetitorstore.model.AllNews
import com.example.repetitorstore.model.NEWS
import com.example.repetitorstore.model.NEWSAccount
import com.example.repetitorstore.model.USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_form_news.*

class FormNewsActivity : AppCompatActivity() {

    private  var countnews = 0
    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_news)

        //ПРИЛОЖЕНИЕ ВО ВЕСЬ ЭКРАН/////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ///////////////////////////////////////////////

        val number = intent.getIntExtra("key2",-1)


        //ОБРАБОТКА СОХРАНЕНИЯ ОБЪЯВЛЕНИЯ///////////////////////////////////////////////////////////
        id_formnews_save.setOnClickListener {

            var news = hashMapOf(
                "name" to id_formnews_name.text.toString(),
                "text" to id_formnews_text.text.toString(),
                "message" to "животное пока не найдено"
            )

            mFirestore.collection("USERS")
                .document(FIRESTORE().getCurrentUserId())
                .get()
                .addOnSuccessListener { document ->
                    var user = document.toObject(USER::class.java)
                    countnews = user!!.count_news

                    mFirestore.collection("NEWS_USERS").document("CountAll")
                        .get()
                        .addOnSuccessListener { task->
                            var doc = task.toObject(AllNews::class.java)

                            mFirestore.collection("NEWS_USERS").document("CountAll").update("CountAllNews",doc!!.CountAllNews+1)
                            Toast.makeText(this,"всего новостей:${doc!!.CountAllNews+1}",Toast.LENGTH_SHORT).show()

                            mFirestore.collection("All_News").document("${doc!!.CountAllNews+1}")
                                .set(news)
                                .addOnSuccessListener {
                                    Toast.makeText(this,"данные отправлены в ленту",Toast.LENGTH_SHORT).show()
                                }


                            var number_news = hashMapOf(
                                "number" to doc!!.CountAllNews+1
                            )
                            mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId()).collection("number_news").document("${countnews+1}")
                                .set(number_news)
                                .addOnSuccessListener {
                                    Toast.makeText(this,"номер объявления сохранен",Toast.LENGTH_SHORT).show()
                                }

                        }



                    //ОТПРАВКА ОБЪЯВЛЕНИЯ В СВОЮ БД/////////////////////////////////////////////////
                    mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId()).collection("${countnews+1}")
                        .document("${countnews+1}")
                        .set(news)
                        .addOnSuccessListener {
                            Toast.makeText(this,"данные получены",Toast.LENGTH_SHORT).show()
                            mFirestore.collection("USERS").document(FIRESTORE().getCurrentUserId()).update("count_news",countnews+1)

                        }
                        .addOnFailureListener {
                            Toast.makeText(this,"ошибка",Toast.LENGTH_SHORT).show()
                        }
                    ////////////////////////////////////////////////////////////////////////////////
                }

        finish()
        }
    }



} 