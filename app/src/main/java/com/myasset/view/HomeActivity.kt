package com.myasset.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import com.myasset.R
import com.myasset.base.BaseActivity
import com.myasset.view.assetfragment.AssetFragment

class HomeActivity : BaseActivity(), HomeViewable {
    lateinit var countrySpinner: Spinner
    lateinit var countryIdValue: String
    lateinit var countryCodeValue: String
    lateinit var countryNameValue: String
    private val presenter by lazy{
        HomePresenter(this)
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < 16) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(R.layout.activity_home)

        presenter.onViewCreated()
    }







    override fun showErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAssetFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.activityHome_frameLayout_container,AssetFragment(),"SearchFragment").commit()
    }
}
