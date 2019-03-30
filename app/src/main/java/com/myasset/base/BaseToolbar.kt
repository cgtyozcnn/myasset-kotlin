package com.myasset.base

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.myasset.R
import com.myasset.util.bindView


class BaseToolbar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    Toolbar(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_base_toolbar, this)
    }

    private val toolbarLeftButton by bindView<TextView>(R.id.baseToolbar_textView_leftButton)
    private val toolbarTitle by bindView<TextView>(R.id.baseToolbar_textView_title)


    fun setTitle(title:String){
        toolbarTitle.text = title
    }

}