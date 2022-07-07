package com.example.repetitorstore.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// ДЛЯ КОНВЕРТИРОВАНИЯ ПОЛУЧЕННЫХ ДАННЫХ В ГОТОВЫЙ ОБЪЕКТ С ПОЛЯМИ
@Parcelize
data class USER (

    var id:String = "",
    var firstname:String = "",
    var fullname:String = "",
    var email:String = "",
    var mobile:String = "",
    var image:String = "",
    var profileCompleted: Int = 0,
    var count_news :Int = 0

): Parcelable
//