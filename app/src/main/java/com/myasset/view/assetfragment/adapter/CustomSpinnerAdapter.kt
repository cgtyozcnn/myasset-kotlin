package com.myasset.view.assetfragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.myasset.R
import com.myasset.model.SpinnerItem

class CustomSpinnerAdapter : ArrayAdapter<SpinnerItem> {
    private var CustomSpinnerItems: List<SpinnerItem>

    constructor(context: Context, resource: Int, pickerItems: List<SpinnerItem>) : super(context, resource, pickerItems){
        this.CustomSpinnerItems = pickerItems
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return CustomSpinnerView(position, convertView, parent)
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return CustomSpinnerView(position, convertView, parent)
    }
    private fun CustomSpinnerView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = layoutInflater.inflate(R.layout.layout_custom_spinner, parent, false)
        val imageView = customView.findViewById(R.id.customSpinner_imageView_countryFlag) as ImageView
        val textView = customView.findViewById(R.id.customSpinner_textView_countryName) as TextView

        val imageRef = CustomSpinnerItems[position].code!!.toLowerCase()
        val resID = context.resources.getIdentifier(imageRef, "drawable", context.packageName)
        imageView.setImageResource(resID)
        //imageView.setImageDrawable();getResource(getApplicationContext(),getImageId(CustomSpinnerItems.get(position).getName()));
        textView.setText(CustomSpinnerItems[position].name)
        return customView
    }
}