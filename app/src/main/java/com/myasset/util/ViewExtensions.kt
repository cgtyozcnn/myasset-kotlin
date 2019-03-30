package com.myasset.util

import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.inflateChild(layoutResId: Int, addParent: Boolean = false) = LayoutInflater.from(this.context).inflate(layoutResId, this, addParent)!!
