package com.heshmat.reqresapidemo.service
import com.heshmat.reqresapidemo.model.ApiResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("users")
    fun fetchUsers(@Query("page") page: Int): Call<ApiResponseModel>
}