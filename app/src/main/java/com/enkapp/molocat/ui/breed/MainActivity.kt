package com.enkapp.molocat.ui.breed

import android.os.Bundle
import android.util.Log
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


class MainActivity : AppCompatActivity(), BreedAdapter.OnBreedClickListener {

    private lateinit var breedShortViewModel: BreedShortViewModel
    private var adapter : BreedAdapter? = null
    private lateinit var bundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bundle = Bundle()
        breedShortViewModel = ViewModelProviders.of(this).get(BreedShortViewModel::class.java)
        breedShortViewModel.getBreeds()

        if(adapter==null){
            adapter = BreedAdapter(emptyList<BreedShort>().toMutableList(), this)
            breeds_view.adapter = adapter
            breeds_view.layoutManager = LinearLayoutManager(this)
        }

        breedShortViewModel.breedShortLiveData.observe(this, Observer {
            adapter?.setBreedList(it)
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
                return true
            }
            R.id.action_sort_by_origin -> {
                adapter?.list?.sortBy { breedShort -> breedShort.origin}
                adapter?.notifyDataSetChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun clickOnBreed(id: String?) {
        Log.d("debugEnis", "id : $id")
        bundle.putSerializable(Constants.TAG_BUNDLE_ID, id)
        customStartActivity(this@MainActivity, DetailActivity::class.java, bundle)
    }
}
