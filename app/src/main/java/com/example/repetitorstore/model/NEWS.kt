package com.example.repetitorstore.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// ДЛЯ КОНВЕРТИРОВАНИЯ ПОЛУЧЕННЫХ ДАННЫХ В ГОТОВЫЙ ОБЪЕКТ С МЕТОДАМИ
@Parcelize
class NEWS (

    var name:String = "",
    var text:String = ""

):Parcelable
//