package com.ynavizovskyi.picturestestapp.datastore.remote

import com.ynavizovskyi.picturestestapp.datastore.remote.response.Picture
import retrofit2.http.GET
import retrofit2.http.Query

interface PicturesService {

    @GET("list/")
    suspend fun getContacts(@Query("page") page: Int, @Query("limit") limit: Int = 10): List<Picture>

}