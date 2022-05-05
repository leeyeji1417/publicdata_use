package com.example.nursery_openapi

import com.example.nursery_openapi.data2.nursery2 //Nursery Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class SeoulOpenApi2 {
    companion object{
        val DOMAIN = "http://openapi.seoul.go.kr:8088/"
        val API_KEY = "4b706d6c68646f6e3730516d6a744f"
    }
}

interface SeoulOpenService2 {
    @GET("{api_key}/json/ChildCareInfo/1/{end}")
    fun getLibraries(@Path("api_key") key:String, @Path("end") limit:Int) : Call<nursery2>
}