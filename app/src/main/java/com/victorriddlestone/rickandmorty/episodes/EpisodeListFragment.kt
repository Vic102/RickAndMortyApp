package com.victorriddlestone.rickandmorty.episodes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victorriddlestone.rickandmorty.R
import com.victorriddlestone.rickandmorty.episodes.Episodes
import com.victorriddlestone.rickandmorty.network.RickAndMortyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EpisodeListFragment : Fragment() {

    private val adapter: EpisodeAdapter = EpisodeAdapter{
        val action = EpisodeListFragmentDirections.actionEpisodeListFragment2ToEpisodeDetailsFragment(it.id.toString())
        findNavController().navigate(action)
    }
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEpisodeListErr : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episode_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvEpisodes : RecyclerView = view.findViewById(R.id.rv_episodes)

        progressBar = view.findViewById(R.id.pb_episode)
        progressBar.visibility = View.VISIBLE
        tvEpisodeListErr = view.findViewById(R.id.tv_episode_list_err)
        tvEpisodeListErr.visibility = View.GONE

        rvEpisodes.layoutManager = LinearLayoutManager(context)
        rvEpisodes.adapter = adapter

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAndMortyService : RickAndMortyService = retrofit.create(RickAndMortyService::class.java)

        rickAndMortyService.getEpisodes().enqueue(object : Callback<EpisodesResponse> {
            override fun onResponse(call: Call<EpisodesResponse>, response: Response<EpisodesResponse>) {
                if (response.isSuccessful) {
                    adapter.submitList(response.body()?.results)
                    progressBar.visibility = View.GONE
                    tvEpisodeListErr.visibility = View.GONE
                } else {
                    Toast.makeText(context, "Error de red. No se ha podido cargar la lista", Toast.LENGTH_SHORT).show()
                    tvEpisodeListErr.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<EpisodesResponse>, t: Throwable) {
                Toast.makeText(context, "Error de red. No se ha podido cargar la lista", Toast.LENGTH_SHORT).show()
                tvEpisodeListErr.visibility = View.VISIBLE
            }

        })

    }

}