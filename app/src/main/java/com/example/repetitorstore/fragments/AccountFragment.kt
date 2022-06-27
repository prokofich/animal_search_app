package com.example.repetitorstore.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.repetitorstore.*
import com.example.repetitorstore.firestore.FIRESTORE
import com.example.repetitorstore.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_tape.*
import kotlinx.android.synthetic.main.news_for_account.*


class AccountFragment : Fragment() {

    private lateinit var user: USER
    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ПЕРЕХОД В НАСТРОЙКИ///////////////////////////////////////////////////////////////////////
        id_account_settings.setOnClickListener {
            startActivity(Intent(context,SettingsActivity::class.java))
        }
        ////////////////////////////////////////////////////////////////////////////////////////////



        //УСТАНОВКА АВАТАРКИ/////////////////////////////////////////////////////////////////



        /////////////////////////////////////////////////////////////////////////////////////


        //ПОКАЗ ДАННЫХ ПОЛЬЗОВАТЕЛЯ//////////////////////////////////////////////////////////////////////
        mFirestore.collection("USERS")
            .document(FIRESTORE().getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(USER::class.java)

                if (user!!.firstname.toString() != "" && user.fullname.toString() != "") {

                    id_acc_username.text = "${user!!.firstname.toString()} ${user.fullname.toString()}"
                    id_acc_mobile.text = user.mobile.toString()


                    //ПОКАЗ СВОИХ ОБЪЯВЛЕНИЙ//////////////////////////////////////
                    if (user!!.count_news > 0) {

                        for (i in 1..user.count_news) {
                            mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId())
                                .collection("$i")
                                .document("$i")
                                .get()
                                .addOnCompleteListener { task ->

                                    if(task.isSuccessful){

                                        val doc = task.result
                                        if (doc!=null){

                                            if(doc.exists()){

                                                mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId())
                                                    .collection("$i")
                                                    .document("$i")
                                                    .get()
                                                    .addOnSuccessListener { task ->

                                                        var news = task.toObject(NEWS::class.java)
                                                        addNews(news!!.name,news!!.text,i)

                                                    }
                                            }
                                        }

                                    }

                                }
                        }
                    }
                    //////////////////////////////////////////////////////////////////////////


                }
            }


    }


    //ФУНКЦИЯ ДОБАВЛЕНИЯ ОБЪЯВЛЕНИЯ НА ЭКРАН////////////////////////////////////////////////////////
    private fun addNews(name: String, text: String, number: Int) {
        var blockView = View.inflate(context, R.layout.news_for_account, null)
        var blockname = blockView.findViewById<TextView>(R.id.id_account_news_name)
        var blocktext = blockView.findViewById<TextView>(R.id.id_account_news_text)
        var blockButtonDel = blockView.findViewById<Button>(R.id.id_account_news_del)
        var blockButtonRedact = blockView.findViewById<Button>(R.id.id_account_news_redact)

        var blocknumber = number

        blockname.text = name
        blocktext.text = "пока не нашли"

        //ОБРАБОТКА УДАЛЕНИЯ////////////////////////////////////////////////////////////////////////
        blockButtonDel.setOnClickListener {

            id_account_tape.removeView(blockView)

            mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId())
                .collection("number_news")
                .document("$blocknumber")
                .get()
                .addOnSuccessListener { task->

                    val dokk = task.toObject(NUMBER::class.java)

                    mFirestore.collection("All_News")
                        .document("${dokk!!.number}")
                        .delete()
                        .addOnSuccessListener {}

                    mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId())
                        .collection("number_news")
                        .document("$blocknumber")
                        .delete()
                        .addOnSuccessListener{}

                    mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId())
                        .collection("$blocknumber")
                        .document("$blocknumber")
                        .delete()
                        .addOnSuccessListener {}

                }




            }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //ОБРАБОТА РЕДАКТИРОВАНИЯ/////////////////////
        blockButtonRedact.setOnClickListener {

            var intent = Intent(getContext(),FormNewsActivity2::class.java)
            intent.putExtra("key2",blocknumber)
            startActivity(intent)


        }
        /////////////////////////////////////////////


        mFirestore.collection("NEWS_USERS").document(FIRESTORE().getCurrentUserId())
            .collection("number_news")
            .document("$blocknumber")
            .get()
            .addOnSuccessListener { task ->
                var real_number = task.toObject(NUMBER::class.java)

                mFirestore.collection("All_News").document("${real_number!!.number}")
                    .addSnapshotListener { value, error ->

                        if (value!=null && value.exists()) {
                            var sms = value!!.toObject(NEWSAccount::class.java)
                            blocktext.text = sms!!.message
                        }

                    }
            }

        id_account_tape.addView(blockView)

    }
}
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


