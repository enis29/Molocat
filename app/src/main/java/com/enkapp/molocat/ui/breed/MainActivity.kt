package com.enkapp.molocat.ui.breed

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import com.enkapp.molocat.R
import com.enkapp.molocat.constant.Constants
import com.enkapp.molocat.extension.customStartActivity
import com.enkapp.molocat.model.BreedShort
import com.enkapp.molocat.ui.BreedShortViewModel
import com.enkapp.molocat.ui.detail.DetailActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.enkapp.molocat.extension.isConnected

class MainActivity : AppCompatActivity(), BreedAdapter.OnBreedClickListener {

    private lateinit var breedShortViewModel: BreedShortViewModel
    private var adapter : BreedAdapter? = null
    private lateinit var bundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bundle = Bundle()
        breedShortViewModel = ViewModelProviders.of(this).get(BreedShortViewModel::class.java)

        if(isConnected) {
            breedShortViewModel.getBreeds()
        }else {
            Toast.makeText(this, getString(R.string.warning_no_connection), Toast.LENGTH_LONG).show()
        }

        if(adapter==null){
            adapter = BreedAdapter(this, emptyList<BreedShort>().toMutableList(), this)
            breeds_view.adapter = adapter
            breeds_view.layoutManager = LinearLayoutManager(this)
        }

        initSearchView()

        breedShortViewModel.breedShortLiveData.observe(this, Observer {
            adapter?.setBreedList(it)
            search.visibility = View.VISIBLE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_breed, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort_by_name -> {
                adapter?.list?.sortBy { breedShort -> breedShort.name}
                adapter?.notifyDataSetChanged()
                sort.text = getString(R.string.field_sort_name)
                return true
            }
            R.id.action_sort_by_origin -> {
                adapter?.list?.sortBy { breedShort -> breedShort.origin}
                adapter?.notifyDataSetChanged()
                sort.text = getString(R.string.field_sort_origin)
                return true
            }
        }
        item.isChecked = true
        return super.onOptionsItemSelected(item)
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
}
