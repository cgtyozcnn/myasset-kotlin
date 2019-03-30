package com.myasset.model

data class Asset(var id:Int? = null,
                 var currencyFlag: String,
                 var currencyType:String,
                 var currencyAmount: Double,
                 var currencyRate:Double,
                 var currencyCurrentRate:Double,
                 var profit:Double? = null)