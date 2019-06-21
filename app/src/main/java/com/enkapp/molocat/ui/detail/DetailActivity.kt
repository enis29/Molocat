package com.enkapp.molocat.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.enkapp.molocat.R
import com.enkapp.molocat.constant.Constants
import com.enkapp.molocat.ui.BreedShortViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity: AppCompatActivity() {

    private lateinit var breedShortViewModel: BreedShortViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = intent.getBundleExtra(Constants.TAG_BUNDLE)
        val breedId = bundle?.getSerializable(Constants.TAG_BUNDLE_ID) as String

        breedShortViewModel = ViewModelProviders.of(this).get(BreedShortViewModel::class.java)
        breedShortViewModel.getBreedById(breedId)

        breedShortViewModel.detailLiveData.observe(this, Observer {
            Glide.with(this).load(it.url).into(image)
        })
    }
}