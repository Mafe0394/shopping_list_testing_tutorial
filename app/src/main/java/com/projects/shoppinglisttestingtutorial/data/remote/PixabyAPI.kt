package com.projects.shoppinglisttestingtutorial.data.remote

import com.projects.shoppinglisttestingtutorial.BuildConfig
import com.projects.shoppinglisttestingtutorial.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabyAPI {

    @GET("/api/")
    suspend fun  searchForImage(
            @Query("q")
            searchQuery:String,
            @Query("key")
            apiKey:String=BuildConfig.API_KEY
    ):Response<ImageResponse>
}