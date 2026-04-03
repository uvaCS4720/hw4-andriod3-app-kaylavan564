package edu.nd.pmcburne.hello

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://www.cs.virginia.edu/"
    val api: PlacemarkApi by lazy{
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(PlacemarkApi::class.java)

    }
}