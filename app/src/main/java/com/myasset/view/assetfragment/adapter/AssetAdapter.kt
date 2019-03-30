package com.myasset.view.assetfragment.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.myasset.R
import com.myasset.model.Asset
import com.myasset.util.bindView
import com.myasset.util.inflateChild
import org.intellij.lang.annotations.JdkConstants
import java.lang.Character.toLowerCase

class AssetAdapter : RecyclerView.Adapter<AssetViewHolder>() {
var assetList = mutableListOf<Asset>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        return AssetViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return assetList.size
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        holder.bindItem(assetList[position],position,itemCount)
    }
    fun updateList(items : MutableList<Asset>?){
        assetList.clear()
        if(items != null){
            assetList.addAll(items)
        }
        notifyDataSetChanged()
    }

}

class AssetViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(parent.inflateChild(R.layout.row_asset)){

    val imageViewCurrencyFlag by bindView<ImageView>(R.id.rowAsset_imageView_currencyFlag)
    val textViewCurrenyType by bindView<TextView>(R.id.rowAsset_textView_currencyType)
    val textViewCurrencyAmount by bindView<TextView>(R.id.rowAsset_textView_currencyAmount)
    val textViewCurrencyRate by bindView<TextView>(R.id.rowAsset_textView_currencyRate)
    val textViewCurrencyCurrentRate by bindView<TextView>(R.id.rowAsset_textView_currencyCurrentRate)
    val textViewProfit by bindView<TextView>(R.id.rowAsset_textView_profit)
    val context =parent.context

    fun bindItem(asset : Asset, position: Int, totalSize:Int ){
        val imageRef = asset.currencyFlag.toLowerCase()
        val resID = context.resources.getIdentifier(imageRef, "drawable", context.packageName)
        imageViewCurrencyFlag.setImageResource(resID)

        textViewCurrenyType.text=asset.currencyType
        textViewCurrencyAmount.text=asset.currencyAmount.toString()
        textViewCurrencyRate.text=asset.currencyRate.toString()
        textViewCurrencyCurrentRate.text = asset.currencyCurrentRate.toString()
        textViewProfit.text = asset.profit.toString()
    }
}