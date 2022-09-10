package com.example.websiteparser.`interface`

import com.example.websiteparser.model.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    //GET request for fetching the search engine data
    @GET("search-engine/search")
    fun getSearch(@Query("per-page") perPage: Int,
                    @Query("query") search: String,
                    @Query("page") page: Int,
                    @Query("mode") mode: Int) : Call<Result>

    companion object {
        var BASE_URL = "https://pomoc.bluemedia.pl/"

        fun create(): ApiInterface { //creating the retrofit interface

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}