package com.myasset.view.assetfragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.myasset.R
import com.myasset.model.Asset
import com.myasset.util.bindView
import com.myasset.view.assetfragment.adapter.AssetAdapter
import com.myasset.view.assetfragment.adapter.CustomSpinnerAdapter
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat

class AssetFragment : Fragment(), AssetViewable {


    //   val db by lazy { DBHelper(getContext())  }
    override fun showAssetList(list: MutableList<Asset>) {
        assetAdapter.updateList(list)
    }


    private val countrySpinner by bindView<Spinner>(R.id.fragmentAsset_spinner_country)
    private val editTextAmount by bindView<EditText>(R.id.fragmentAsset_editText_amount)
    private val editTextAmountTRY by bindView<EditText>(R.id.fragmentAsset_editText_amountTry)
    private val recyclerViewAsset by bindView<RecyclerView>(R.id.fragmentAsset_recyclerView_myAsset)
    private val buttonAddAsset by bindView<Button>(R.id.fragmentAsset_button_add)
    lateinit var countryIdValue: String
    lateinit var countryCodeValue: String
    lateinit var countryNameValue: String
    var rate: Double = 0.0
    var amount = 0.0
    val formatRate = DecimalFormat("##.###")

    private val presenter by lazy {
        AssetPresenter(this, context)
    }
    private val assetAdapter by lazy {
        AssetAdapter()
    }

    fun connectionWebService() {
        val downloadData = getCurrency()

        try {
            val url =
                "https://free.currencyconverterapi.com/api/v6/convert?q=" + countryNameValue + "_TRY&compact=ultra&apiKey=93c51d0b521fd7e6de69"
            downloadData.execute(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SetupCountryPicker()

        recyclerViewAsset.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerViewAsset.adapter = assetAdapter

        connectionWebService()
        presenter.onViewCreated()

        editTextAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString() == "") {
                    amount = 0.0
                    editTextAmountTRY.text.clear()
                } else {
                    amount = s.toString().toDouble()

                    editTextAmountTRY.setText(formatRate.format(rate * amount).toString())
                }
            }
        })

        buttonAddAsset.setOnClickListener {

            presenter.insertDB(
                Asset(
                    0,
                    countryCodeValue,
                    countryNameValue,
                    (editTextAmount.text.toString()).toDouble(),
                    rate,
                    rate,
                    0.0
                )
            )

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_fragment_myasset, container, false)
        //presenter.onViewCreated()

    }

    private fun clearAll() {
        editTextAmount.text.clear()
        editTextAmountTRY.text.clear()
    }

    private fun SetupCountryPicker() {

        val countryPickerData = presenter.getAllCountries()
        val context: Context = context!!
        Log.d("xxx", countryPickerData.toString())
        val pickerAdapter = CustomSpinnerAdapter(context, R.layout.layout_custom_spinner, countryPickerData)
        countrySpinner.setAdapter(pickerAdapter)
        countrySpinner.setSelection(0)
        countryIdValue = countryPickerData.get(0).id!!
        countryCodeValue = countryPickerData.get(0).code!!
        countryNameValue = countryPickerData.get(0).name!!

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                countryIdValue = countryPickerData.get(i).id!!
                countryCodeValue = countryPickerData.get(i).code!!
                countryNameValue = countryPickerData.get(i).name!!
                connectionWebService()
                clearAll()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                //handle when no item selected
            }
        }
    }

    override fun showErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class getCurrency : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {
            var result = ""

            var url: URL
            var HttpURLConnection: HttpURLConnection

            try {
                url = URL(params[0])
                HttpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = HttpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                var data = inputStreamReader.read()

                while (data > 0) {
                    val character = data.toChar()
                    result += character

                    data = inputStreamReader.read()
                }
                return result


            } catch (e: Exception) {
                e.printStackTrace()
                return result
            }
        }

        override fun onPostExecute(result: String?) {
            var getRate: Double
            try {
                var JSONObject = JSONObject(result)
                println(JSONObject)
                getRate = JSONObject.getString(countryNameValue + "_TRY").toDouble()
                val formatRate = DecimalFormat("##.###")
                rate = formatRate.format(getRate).toDouble()
                println(rate)


            } catch (e: Exception) {
                e.printStackTrace()
            }

            super.onPostExecute(result)
        }

    }
}