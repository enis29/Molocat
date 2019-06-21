package com.enkapp.molocat.api

import android.util.Log
import com.enkapp.molocat.model.BreedShort
import com.enkapp.molocat.model.Detail
import retrofit2.Response
import java.io.IOException

class ApiService(private val api : ApiInterface) {

    sealed class CustomResponse<out T: Any>{
        data class Success<out T: Any>(val data: T) : CustomResponse<T>()
        data class Error(val exception: Exception) : CustomResponse<Nothing>()
    }

    private suspend fun <T : Any> safeCall(call: suspend () -> Response<T>, errorMessage: String): T? {

        val result : CustomResponse<T> = getSafeResult(call,errorMessage)
        var data : T? = null

        when(result) {
            is CustomResponse.Success -> data = result.data
            is CustomResponse.Error -> Log.d(ApiService::class.java.canonicalName, "$errorMessage \n ${result.exception}")
        }
        return data
    }

    private suspend fun <T: Any> getSafeResult(call: suspend ()-> Response<T>, errorMessage: String) : CustomResponse<T>{
        val response = call.invoke()
        if(response.isSuccessful) return CustomResponse.Success(response.body()!!)
        return CustomResponse.Error(IOException("error : $errorMessage"))
    }



    suspend fun getBreeds() : MutableList<BreedShort>?{

        val breedsResponse = safeCall(
            call = {api.getBreeds().await()},
            errorMessage = "Error during fetch Breeds"
        )

        return breedsResponse?.toMutableList()
    }

    suspend fun getBreedById(id: String) : MutableList<Detail>? {
        val detail =  safeCall(
            call = { api.getBreedById(id, 1).await()},
            errorMessage = "Error during fetch Image of cat"
        )

        return detail?.toMutableList()
    }


}