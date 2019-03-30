package com.myasset.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.myasset.model.Asset

class DBHelper(val context : Context?) : SQLiteOpenHelper(context,DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION){


    private val TABLE_NAME = "Assets"
    private val COL_ID = "id"
    private val COL_CURRENCYFLAG = "currencyFlag"
    private val COL_CURRENCYTYPE = "currencyType"
    private val COL_CURRENCYAMOUNT = "currencyAmount"
    private val COL_CURRENCYRATE="currencyRate"
    private val COL_CURRENCYCURRENTRATE="currencyCurrentRate"
    private val COL_PROFIT="profit"

    companion object {
        private val DATABASE_NAME = "MyAsset"
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_CURRENCYFLAG VARCHAR(256),$COL_CURRENCYTYPE  VARCHAR(256),$COL_CURRENCYAMOUNT  DECIMAL (18,2), $COL_CURRENCYRATE DECIMAL (18,2), $COL_CURRENCYCURRENTRATE DECIMAL(18,2), $COL_PROFIT DECIMAL(18,2))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(asset : Asset){
        val sqliteDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_CURRENCYFLAG, asset.currencyFlag)
        contentValues.put(COL_CURRENCYTYPE,asset.currencyType)
        contentValues.put(COL_CURRENCYAMOUNT,asset.currencyAmount)
        contentValues.put(COL_CURRENCYRATE,asset.currencyRate)
        contentValues.put(COL_CURRENCYCURRENTRATE,asset.currencyCurrentRate)
        contentValues.put(COL_PROFIT,asset.profit)

        val result = sqliteDB.insert(TABLE_NAME,null,contentValues)

        Toast.makeText(context,if(result != -1L) "Kayıt Başarılı" else "kayıt yapılamadı", Toast.LENGTH_SHORT).show()
    }
    fun readData():MutableList<Asset>{
        val assetList = mutableListOf<Asset>()
        val sqliteDB = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = sqliteDB.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                val asset = Asset(
                result.getString(result.getColumnIndex(COL_ID)).toInt(),
                    result.getString(result.getColumnIndex(COL_CURRENCYFLAG)),
                    result.getString(result.getColumnIndex(COL_CURRENCYTYPE)),
                    result.getString(result.getColumnIndex(COL_CURRENCYAMOUNT)).toDouble(),
                    result.getString(result.getColumnIndex(COL_CURRENCYRATE)).toDouble(),
                    result.getString(result.getColumnIndex(COL_CURRENCYCURRENTRATE)).toDouble(),
                    result.getString(result.getColumnIndex(COL_PROFIT)).toDouble()
                )
                assetList.add(asset)
            }while(result.moveToNext())
        }
        result.close()
        sqliteDB.close()
        return assetList
    }
}