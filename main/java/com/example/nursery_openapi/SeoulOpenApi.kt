package com.example.nursery_openapi

import com.example.nursery_openapi.data.nursery //CCTV Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class SeoulOpenApi {
    companion object{
        val DOMAIN = "http://openapi.seoul.go.kr:8088/"
        val API_KEY = "58766f6164646f6e33387253717855"
    }
}
//http://openapi.seoul.go.kr:8088/(인증키)/xml/safeOpenCCTV/1/5/
interface SeoulOpenService {
    @GET("{api_key}/json/safeOpenCCTV/1/{end}")
    fun getLibraries(@Path("api_key") key:String, @Path("end") limit:Int) : Call<nursery>
}