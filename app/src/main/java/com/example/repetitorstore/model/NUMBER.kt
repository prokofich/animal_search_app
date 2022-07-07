package com.example.repetitorstore.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// ДЛЯ КОНВЕРТИРОВАНИЯ ПОЛУЧЕННЫХ ДАННЫХ В ГОТОВЫЙ ОБЪЕКТ С ПОЛЯМИ
@Parcelize
class NUMBER (

    var number:Int = 0

):Parcelable
//