package com.myasset.view

import com.myasset.model.SpinnerItem

class HomePresenter constructor(val viewable:HomeViewable) {
    fun onViewCreated() {
        viewable.showAssetFragment()
    }

}
interface HomeViewable {
    fun showErrorMessage()
    fun showAssetFragment()
}
