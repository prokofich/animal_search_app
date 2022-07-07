package com.example.repetitorstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.repetitorstore.fragments.AccountFragment
import com.example.repetitorstore.fragments.TapeFragment
import kotlinx.android.synthetic.main.activity_main.*
/*
Активити с меню и окном для фрагментов(лента объявлений и профиль юзера)
 */

class MainActivity : AppCompatActivity() {

    private val accountfragment = AccountFragment()
    private val tapefragment = TapeFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ПРИЛОЖЕНИЕ ВО ВЕСЬ ЭКРАН/////////////////////
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ///////////////////////////////////////////////


        //ОБРАБОТКА НАЖАТИЯ КНОПОК В МЕНЮ+ПОКАЗ ФРАГМЕНТОВ/////////////
        replaceFragment(accountfragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.id_menu_acc ->replaceFragment(accountfragment)
                R.id.id_menu_lenta ->replaceFragment(tapefragment)
            }
            true
        }
        ///////////////////////////////////////////////////////////////

    }


    //ФУНКЦИЯ ПОКАЗА ФРАГМЕНТА///////////////////////////////////////////
    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }
    /////////////////////////////////////////////////////////////////////

}