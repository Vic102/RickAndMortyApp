package com.victorriddlestone.rickandmorty.location

import android.content.RestrictionEntry
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Scroller
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.victorriddlestone.rickandmorty.R
import com.victorriddlestone.rickandmorty.characters.CharacterResponse
import com.victorriddlestone.rickandmorty.characters.SingleCharacter
import com.victorriddlestone.rickandmorty.episodes.SingleCharacter2
import com.victorriddlestone.rickandmorty.episodes.SingleEpisode
import com.victorriddlestone.rickandmorty.network.RickAndMortyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LocationDetailFragment : Fragment() {

    private val args : LocationDetailFragmentArgs by navArgs()

    private lateinit var progressBar: ProgressBar
    private lateinit var tvLocDetErr : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.pb_det_loc)
        progressBar.visibility = View.VISIBLE
        tvLocDetErr = view.findViewById(R.id.tv_loc_det_err)
        tvLocDetErr.visibility = View.GONE

        val tvName : TextView = view.findViewById(R.id.tv_det_loc_name)
        val tvType : TextView = view.findViewById(R.id.tv_det_loc_type)
        val tvDimensions : TextView = view.findViewById(R.id.tv_det_loc_dim)
        val tvResidents : TextView = view.findViewById(R.id.tv_det_loc_res)

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAndMortyService : RickAndMortyService = retrofit.create(RickAndMortyService::class.java)

        rickAndMortyService.getLocationInfo(args.locationId.toInt()).enqueue(object :
            Callback<SingleLocation> {
            override fun onResponse(
                call: Call<SingleLocation>,
                response: Response<SingleLocation>
            ) {
                if (response.isSuccessful){
                    val location = response.body()
                    if (location != null) {
                        tvName.text = location.name
                        tvType.text = location.type
                        tvDimensions.text = location.dimension

                        fun getResidents():List<String?>?{
                            val mutList : MutableList<String> = arrayListOf()
                            var characterArr : MutableList<String> = arrayListOf()
                            if (location.residents != null) {
                                for ((index, item) in location.residents.withIndex()){
                                    if (item != null) {
                                        var numbers : String = ""
                                        for (s in item){
                                            if (s.isDigit()) {
                                                numbers += s.toString()
                                            }
                                        }
                                        characterArr.add(numbers)

                                        //Esto no funciona y no sé porqué
                                        rickAndMortyService.getCharacterInfo(characterArr[index].toInt()).enqueue(object : Callback<SingleCharacter> {
                                            override fun onResponse(call: Call<SingleCharacter>, response: Response<SingleCharacter>) {
                                                if (response.isSuccessful) {
                                                    val character = response.body()
                                                    if (character != null){

                                                       mutList.add(character.name.toString())
                                                    } else {
                                                        Toast.makeText(context, "Error de respuesta", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            }
                                            override fun onFailure(call: Call<SingleCharacter>, t: Throwable) {
                                                Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()
                                            }
                                        })
                                    }
                                }
                            }
                            return mutList.toList()
                        }

                        //Coger los nombres no funciona, así que pongo los links
                        //tvResidents.text = getResidents().toString()
                        tvResidents.text = location.residents.toString()

                        progressBar.visibility = View.GONE
                        tvLocDetErr.visibility = View.GONE
                    }
                } else {
                    tvLocDetErr.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<SingleLocation>, t: Throwable) {
                tvLocDetErr.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }

        })



    }

}