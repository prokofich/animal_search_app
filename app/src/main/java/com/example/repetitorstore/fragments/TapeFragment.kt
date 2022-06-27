package com.example.repetitorstore.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.repetitorstore.FormNewsActivity
import com.example.repetitorstore.FoundAnimalActivity
import com.example.repetitorstore.R
import com.example.repetitorstore.model.AllNews
import com.example.repetitorstore.model.NEWS
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_tape.*


class TapeFragment : Fragment() {

    val mFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //ПЕРЕХОД НА СОЗДАНИЕ ОБЪЯВЛЕНИЯ////////////////////////////////////////////////////////////
        id_tape_add_news.setOnClickListener {
            val intent = Intent(getContext(),FormNewsActivity::class.java)
             startActivity(intent)
        }
        ////////////////////////////////////////////////////////////////////////////////////////////


        //ПОКАЗ ОБЪЯВЛЕНИЙ//////////////////////////////////////////////////////////////////////////
        mFirestore.collection("NEWS_USERS").document("CountAll")
            .get()
            .addOnSuccessListener { task ->
                var doc = task.toObject(AllNews::class.java)

                for (i in 1..doc!!.CountAllNews){

                    mFirestore.collection("All_News").document("$i")
                        .get()
                        .addOnCompleteListener { task ->

                            if(task.isSuccessful){

                                val doc = task.result
                                if (doc!=null){

                                    if(doc.exists()){

                                        mFirestore.collection("All_News").document("$i")
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
        ////////////////////////////////////////////////////////////////////////////////////////////

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tape, container, false)
    }




    //ФУНКЦИЯ ДОБАВЛЕНИЯ ОБЪЯВЛЕНИЯ////////////////////////////////////////////////////////////////////////
    private fun addNews(name: String, text:String,number:Int) {
        var blockView = View.inflate(context, R.layout.news_for_tape, null)//Создаём 1 block
        var blockname = blockView.findViewById<TextView>(R.id.id_tape_news_name)//Инициализируем поле name
        var blocktext = blockView.findViewById<TextView>(R.id.id_tape_news_text)//Инициализируем поле text
        var blockButton = blockView.findViewById<Button>(R.id.id_tape_found)
        var blocknumber = number

        blockname.text = name
        blocktext.text = text

        //ПЕРЕХОД НА ЭКРАН ОТПРАВКИ СООБЩЕНИЯ О НАХОДКЕ//////////////////////
        blockButton.setOnClickListener {
            var intent = Intent(getContext(),FoundAnimalActivity::class.java)
            intent.putExtra("key1",blocknumber)
            startActivity(intent)
        }
        /////////////////////////////////////////////////////////////////////

        id_tape.addView(blockView)
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

}







