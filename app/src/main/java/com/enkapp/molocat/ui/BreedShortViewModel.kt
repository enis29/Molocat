package com.enkapp.molocat.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enkapp.molocat.api.ApiClient
import com.enkapp.molocat.api.ApiService
import com.enkapp.molocat.model.BreedShort
import com.enkapp.molocat.model.Detail
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BreedShortViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val repo : ApiService = ApiService(ApiClient.api)

    val breedShortLiveData = MutableLiveData<MutableList<BreedShort>>()
    val detailLiveData = MutableLiveData<Detail>()

    fun getBreeds(){
        scope.launch {
            val breeds = repo.getBreeds()
            breedShortLiveData.postValue(breeds)
        }
    }

    fun getBreedById(id : String){
        scope.launch {
            val detail = repo.getBreedById(id)
            detailLiveData.postValue(detail?.get(0))
        }
    }

}