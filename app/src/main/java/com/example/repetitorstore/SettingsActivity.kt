package com.example.repetitorstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.repetitorstore.firestore.FIRESTORE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_settings.*
/*
Активити с настройками для возможности выхода из аккаунта и редактирования своих данных
 */

class SettingsActivity : AppCompatActivity() {

    val mFirestoreAuth = FirebaseAuth.getInstance()
    val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //ВЫХОД ИЗ АККАУНТА///////////////////////////////////////////////////
        id_settings_exit.setOnClickListener {
            mFirestoreAuth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
        }
        //////////////////////////////////////////////////////////////////////

        //ПОКАЗ ОКНА ДЛЯ РЕДАКТИРОВАНИЯ/////////
        id_settings_redact.setOnClickListener {
            addWindowRedactData()
        }
        ////////////////////////////////////////
    }


    //ФУНКИЯ ПОКАЗА ОКНА РЕДАКТИРОВАНИЯ/////////////////////////////////////////////////////////////
    private fun addWindowRedactData(){
        var blockView = View.inflate(this, R.layout.window_for_redact_data, null)
        var blockname = blockView.findViewById<TextView>(R.id.id_window_name)
        var blocklastname = blockView.findViewById<TextView>(R.id.id_window_lastname)
        var blockmobile = blockView.findViewById<TextView>(R.id.id_window_mobile)
        var blockbuttonsave = blockView.findViewById<Button>(R.id.id_window_save)

        //ОБРАБОТКА СОХРАНЕНИЯ//////////////////////////////////////////////////////////////////////
        blockbuttonsave.setOnClickListener {

            val name = blockname.text.toString()
            val lastname = blocklastname.text.toString()
            val mobile = blockmobile.text.toString()

            mFirestore.collection("USERS").document(FIRESTORE().getCurrentUserId())
                .update("firstname",name,"fullname",lastname,"mobile",mobile)

            id_settings_tape_forredact.removeView(blockView)

        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        id_settings_tape_forredact.addView(blockView)

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


}