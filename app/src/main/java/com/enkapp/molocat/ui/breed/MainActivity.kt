package com.enkapp.molocat.ui.breed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.enkapp.molocat.R
import com.enkapp.molocat.constant.Constants
import com.enkapp.molocat.extension.customStartActivity
import com.enkapp.molocat.ui.detail.DetailActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.*
import com.enkapp.molocat.extension.isConnected
import com.enkapp.molocat.model.Breed
import com.enkapp.molocat.ui.BreedViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), BreedAdapter.OnBreedClickListener {

    private lateinit var breedViewModel: BreedViewModel
    private var adapter : BreedAdapter? = null
    private lateinit var bundle : Bundle

    private lateinit var initialList : MutableList<Breed>

    private var selectionSort = 0
    private val emptyArray = intArrayOf()
    private var selectionsFilter : IntArray = emptyArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bundle = Bundle()
        breedViewModel = ViewModelProviders.of(this).get(BreedViewModel::class.java)

        if(isConnected) {
            breedViewModel.getBreeds()
        }else {
            Toast.makeText(this, getString(R.string.warning_no_connection), Toast.LENGTH_LONG).show()
        }

        if(adapter==null){
            adapter = BreedAdapter(this, emptyList<Breed>().toMutableList(), this)
            breeds_view.adapter = adapter
            breeds_view.layoutManager = LinearLayoutManager(this)
        }

        initSearchView()

        breedViewModel.breedsLiveData.observe(this, Observer {
            initialList = it
            adapter?.setBreedList(initialList)
            search.visibility = View.VISIBLE
            initListeners()
        })
    }

    override fun clickOnBreed(id: String?) {
        bundle.putSerializable(Constants.TAG_BUNDLE_ID, id)
        customStartActivity(this@MainActivity, DetailActivity::class.java, bundle)
    }

    private fun initSearchView(){
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                adapter!!.filter.filter(editable.toString())
            }
        })
    }

    private fun initListeners(){
        btn_sort.setOnClickListener {
            MaterialDialog(this).show {
                listItemsSingleChoice(R.array.array_sort, initialSelection = selectionSort) { _, index, _ ->
                    if(selectionSort != index) {
                        selectionSort = index
                        sort(selectionSort)
                    }
                }
            }
        }
        btn_filter.setOnClickListener {
            MaterialDialog(this)
                .cancelOnTouchOutside(true)
                .title(R.string.title)
                .positiveButton(R.string.field_select) {
                    filter()
                }
                .negativeButton(R.string.field_cancel) {
                    selectionsFilter = emptyArray
                    noFilter()
                }
                .show {
                    listItemsMultiChoice(R.array.array_filter, initialSelection = selectionsFilter) { _, array, _ ->
                        selectionsFilter = array
                    }
                }
        }
    }

    private fun sort(choice: Int){
        when(choice){
            0 -> {
                adapter?.list?.sortBy { breedShort -> breedShort.name}
            }
            1 -> {
                adapter?.list?.sortBy { breedShort -> breedShort.origin}
            }
        }
        adapter?.notifyDataSetChanged()
    }

    private fun filter(){
        GlobalScope.launch {
            val list = adapter?.list?.filter {
                    //Test
                    breed -> breed.shedding_level>3
            }
            adapter?.setBreedList(list?.toMutableList())
            filterWithList()
        }

        adapter?.notifyDataSetChanged()
    }

    private fun filterWithList(){

    }

    private fun noFilter(){
        adapter?.setBreedList(initialList)
        adapter?.notifyDataSetChanged()
    }
}
