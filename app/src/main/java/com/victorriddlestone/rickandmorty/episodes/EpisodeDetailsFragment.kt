package com.victorriddlestone.rickandmorty.episodes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.strictmode.SetRetainInstanceUsageViolation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.victorriddlestone.rickandmorty.R
import com.victorriddlestone.rickandmorty.characters.SingleCharacter
import com.victorriddlestone.rickandmorty.network.RickAndMortyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EpisodeDetailsFragment : Fragment() {

    private val args: EpisodeDetailsFragmentArgs by navArgs()

    private lateinit var progressBar: ProgressBar
    private lateinit var tvEpiDetErr : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episode_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.pb_epi_det)
        progressBar.visibility = View.VISIBLE
        tvEpiDetErr = view.findViewById(R.id.tv_epi_det_err)
        tvEpiDetErr.visibility = View.GONE

        val tvName : TextView = view.findViewById(R.id.tv_name)
        val tvCreated : TextView = view.findViewById(R.id.tv_date_created)
        val tvAirDate : TextView = view.findViewById(R.id.tv_date)
        val tvEpisodeName : TextView = view.findViewById(R.id.tv_episode)
        val tvCharacters : TextView = view.findViewById(R.id.tv_characters)

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAndMortyService : RickAndMortyService = retrofit.create(RickAndMortyService::class.java)

        rickAndMortyService.getEpisodeInfo(args.epiId.toInt()).enqueue(object : Callback<SingleEpisode> {
            override fun onResponse(call: Call<SingleEpisode>, response: Response<SingleEpisode>) {
                if (response.isSuccessful) {
                    val episode = response.body()
                    if (episode != null){
                        tvName.text = episode.name
                        tvCreated.text = episode.created
                        tvAirDate.text = episode.air_date
                        tvEpisodeName.text = episode.episode

                            fun getChars():List<String?>?{
                                val mutList : MutableList<String> = arrayListOf()
                                var characterArr : MutableList<String> = arrayListOf()
                                if (episode.characters != null) {
                                    for ((index, item) in episode.characters.withIndex()){
                                        if (item != null) {
                                            var numbers : String = ""
                                            for (s in item){
                                                if (s.isDigit()) {
                                                numbers += s.toString()
                                                }
                                            }
                                            characterArr.add(numbers)

                                            //Esto no funciona y no sé porqué
                                            rickAndMortyService.getCharacterInfoInEpisodes(characterArr[index].toInt()).enqueue(object : Callback<SingleCharacter2> {
                                                override fun onResponse(call: Call<SingleCharacter2>, response: Response<SingleCharacter2>) {
                                                    if (response.isSuccessful) {
                                                        val character = response.body()
                                                        if (character != null){
                                                            mutList.add(character.name.toString())
                                                        } else {
                                                            Toast.makeText(context, "Error de respuesta", Toast.LENGTH_SHORT).show()
                                                            tvEpiDetErr.visibility = View.VISIBLE
                                                        }
                                                    }
                                                }
                                                override fun onFailure(call: Call<SingleCharacter2>, t: Throwable) {
                                                    Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()
                                                    tvEpiDetErr.visibility = View.VISIBLE
                                                }
                                            })
                                        }
                                    }
                                }
                                return mutList.toList()
                            }

                        //Coger los nombres no funciona, así que pongo los links
                        //tvCharacters.text = getChars().toString()
                        tvCharacters.text = episode.characters.toString()

                        progressBar.visibility = View.GONE
                        tvEpiDetErr.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(context, "Error de red. No se han podido cargar los detalles", Toast.LENGTH_SHORT).show()
                    tvEpiDetErr.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<SingleEpisode>, t: Throwable) {
                Toast.makeText(context, "Error de red. No se han podido cargar los detalles", Toast.LENGTH_SHORT).show()
                tvEpiDetErr.visibility = View.VISIBLE
            }
        })

    }
}