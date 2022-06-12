package com.victorriddlestone.rickandmorty.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.victorriddlestone.rickandmorty.R
import com.victorriddlestone.rickandmorty.network.RickAndMortyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocationListFragment : Fragment() {

    private val adapter : LocationAdapter = LocationAdapter {
        val action = LocationListFragmentDirections.actionLocationListFragmentToLocationDetailFragment(it.id.toString())
        findNavController().navigate(action)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var tvLocationListErr : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvLocation : RecyclerView = view.findViewById(R.id.rv_location)
        progressBar = view.findViewById(R.id.pb_location)
        progressBar.visibility = View.VISIBLE
        tvLocationListErr = view.findViewById(R.id.tv_location_list_err)
        tvLocationListErr.visibility = View.GONE


        rvLocation.layoutManager = LinearLayoutManager(context)
        rvLocation.adapter = adapter


        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAndMortyService : RickAndMortyService = retrofit.create(RickAndMortyService::class.java)
        rickAndMortyService.getLocation().enqueue(object : Callback<LocationResponse> {
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                if (response.isSuccessful) {
                    adapter.submitList(response.body()?.results)
                    progressBar.visibility = View.GONE
                    tvLocationListErr.visibility = View.GONE
                } else {
                    tvLocationListErr.visibility = View.VISIBLE
                    //Snackbar.make(view, "Unable to load the list", Snackbar.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                tvLocationListErr.visibility = View.VISIBLE
                //No estoy seguro de si esto o el TextView en medio de la pantalla
                //Snackbar.make(view, "${t.message}", Snackbar.LENGTH_SHORT).show()
            }
        })



    }

}