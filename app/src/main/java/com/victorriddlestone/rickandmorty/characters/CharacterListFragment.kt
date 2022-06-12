package com.victorriddlestone.rickandmorty.characters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.victorriddlestone.rickandmorty.R
import com.victorriddlestone.rickandmorty.network.RickAndMortyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterListFragment : Fragment() {

    private val adapter: CharacterAdapter = CharacterAdapter{
        val action = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(it.id.toString())
        findNavController().navigate(action)
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var characterErr : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvCharacter : RecyclerView = view.findViewById(R.id.rv_character)

        progressBar = view.findViewById(R.id.pb_char)
        progressBar.visibility = View.VISIBLE
        characterErr = view.findViewById(R.id.tv_char_err)
        characterErr.visibility = View.GONE

        rvCharacter.layoutManager = LinearLayoutManager(context)
        rvCharacter.adapter = adapter

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAndMortyService : RickAndMortyService = retrofit.create(RickAndMortyService::class.java)

        rickAndMortyService.getCharacters().enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if (response.isSuccessful) {
                    Log.d("VRA", "users: ${response.body()}")
                    adapter.submitList(response.body()?.results)
                    progressBar.visibility = View.GONE
                    characterErr.visibility = View.GONE
                } else {
                    characterErr.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                characterErr.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

        })

    }

}