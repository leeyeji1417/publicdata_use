package com.example.nursery_openapi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.nursery_openapi.data.nursery
import com.example.nursery_openapi.data2.nursery2

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    //GC_MAPX
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        loadLibraries()
        loadLibraries2()

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun loadLibraries() {
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SeoulOpenService::class.java)

        service.getLibraries(SeoulOpenApi.API_KEY, 1000)
            .enqueue(object : Callback<nursery> {
                override fun onResponse(call: Call<nursery>, response: Response<nursery>) {
                    val result = response.body()
                    showLibraries(result)
                }

                override fun onFailure(call: Call<nursery>, t: Throwable) {
                    Log.e("라이브러리", "error=${t.localizedMessage}")
                    Toast.makeText(this@MapsActivity, "데이터를 가져올 수 없습니다", Toast.LENGTH_LONG).show()
                }

            })
    }

    fun loadLibraries2() {
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi2.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SeoulOpenService2::class.java)

        service.getLibraries(SeoulOpenApi2.API_KEY, 1000)
            .enqueue(object : Callback<nursery2> {
                override fun onResponse(call: Call<nursery2>, response: Response<nursery2>) {
                    val result2 = response.body()
                    showLibraries2(result2)
                }

                override fun onFailure(call: Call<nursery2>, t: Throwable) {
                    Log.e("라이브러리", "error=${t.localizedMessage}")
                    Toast.makeText(this@MapsActivity, "데이터를 가져올 수 없습니다", Toast.LENGTH_LONG).show()
                }

            })
    }

    fun showLibraries(result: nursery?) {

        result?.let {
            val latlngbounds = LatLngBounds.Builder()
            for(Nursery in it.safeOpenCCTV.row) {

                val position = LatLng(Nursery.WGSXPT.toDouble(), Nursery.WGSYPT.toDouble())
                val marker = MarkerOptions().position(position).title(Nursery.ADDR).alpha(0.6F).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                mMap.addMarker(marker)
                latlngbounds.include(position)
            }
            val bounds = latlngbounds.build()
            val padding = 0

            val camera = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mMap.moveCamera(camera)
        }
    }

    fun showLibraries2(result2: nursery2?) {

        result2?.let {
            val latlngbounds = LatLngBounds.Builder()
            for(Nursery2 in it.ChildCareInfo.row) {
                val position = LatLng(Nursery2.LA.toDouble(), Nursery2.LO.toDouble())
                val marker = MarkerOptions().position(position).title(Nursery2.CRNAME).alpha(0.6F)

                mMap.addMarker(marker)

            }
        }
    }


}