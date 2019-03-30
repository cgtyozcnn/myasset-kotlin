package com.myasset.view.assetfragment

import android.content.Context
import com.myasset.database.DBHelper
import com.myasset.model.Asset
import com.myasset.model.SpinnerItem

class AssetPresenter constructor(val viewable : AssetViewable, context: Context?){
    var spinnerItems = mutableListOf<SpinnerItem>()
    var assetList = mutableListOf<Asset>()

    val db by lazy {
        DBHelper(context)
    }
    fun onViewCreated(){

        //getAllCountries()
        getAllAsset()
    }

    fun getAllCountries() : List<SpinnerItem>{
        spinnerItems.add(0, SpinnerItem("1", "EUR", "EU"))
        spinnerItems.add(1, SpinnerItem("2", "USD", "US"))
        spinnerItems.add(2, SpinnerItem("3", "GBP", "GB"))
        spinnerItems.add(3, SpinnerItem("4", "CHF", "CH"))
        spinnerItems.add(2, SpinnerItem("3", "CAD", "CA"))

        return spinnerItems
    }

    fun oldgetAllAsset(){
        assetList.add(0,Asset(0,"EU","EUR",100.0,6.442,6.543,5.4))
        assetList.add(1,Asset(1,"GB","GBP",100.0,6.442,6.543,5.4))
        assetList.add(2,Asset(2,"CA","CAD",100.0,6.442,6.543,5.4))
        viewable.showAssetList(assetList)
    }
    fun oldinsertDB(currencyFlag : String, currencyType: String, amount:Double,currencyRate:Double){
        if(validateSection(amount)){
        }
    }

    fun insertDB(asset:Asset){
        if(validateSection(asset.currencyAmount)){
            db.insertData(asset)
        }
        getAllAsset()
    }
    fun getAllAsset(){
        assetList = db.readData()
        viewable.showAssetList(assetList)
    }

    fun validateSection(amount : Double): Boolean{
        var validation = false
        if(amount == 0.0){
            validation = false
            println("boş bırakılamaz")
        }
        else{
            validation = true

        }
        return validation
    }
}

interface AssetViewable{
    fun showErrorMessage()
    fun showAssetList(list: MutableList<Asset>)

}