package com.enkapp.molocat.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.enkapp.molocat.R
import com.enkapp.molocat.constant.Constants
import com.enkapp.molocat.model.Detail
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
            updateUI(it)
        })
    }

    private fun updateUI(detail: Detail){
        Glide.with(this).load(detail.url).into(image)
        val breed = detail.breeds[0]

        title_detail.text = breed.name
        tv_description.text = breed.description
        tv_origin.text = breed.origin.plus(" (${breed.country_code})")
        tv_life_span.text = breed.life_span
        tv_temperament.text = breed.temperament
        tv_weight.text = breed.weight.metric
        tv_wikipedia.text = breed.wikipedia_url
        tv_wikipedia.setOnClickListener {
            goToWikipedia(breed.wikipedia_url)
        }

        tv_hairless.text = interpret(breed.hairless)
        tv_health_issues.text = interpret(breed.health_issues)
        tv_hypoallergenic.text = interpret(breed.hypoallergenic)
        tv_rare.text = interpret(breed.rare)


        rb_adaptability.rating = breed.adaptability.toFloat()
        rb_affection_level.rating = breed.affectionLevel.toFloat()
        rb_child_friendly.rating = breed.child_friendly.toFloat()
        rb_energy_level.rating = breed.energy_level.toFloat()
        rb_grooming.rating = breed.grooming.toFloat()
        rb_intelligence.rating = breed.intelligence.toFloat()
        rb_shedding_level.rating = breed.shedding_level.toFloat()
        rb_social_needs.rating = breed.social_needs.toFloat()
        rb_stranger_friendly.rating = breed.stranger_friendly.toFloat()
    }

    private fun interpret(value: Int): String {
        return if(value==1) this.resources.getString(R.string.field_yes) else this.resources.getString(R.string.field_no)
    }

    private fun goToWikipedia(url: String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}