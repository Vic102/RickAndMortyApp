package com.victorriddlestone.rickandmorty.characters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victorriddlestone.rickandmorty.R
import com.victorriddlestone.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.victorriddlestone.rickandmorty.network.RickAndMortyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.victorriddlestone.rickandmorty.characters.Character as Character

class CharacterDetailsFragment : Fragment() {


    private val args: CharacterDetailsFragmentArgs by navArgs()
    private lateinit var progressBar: ProgressBar
    private lateinit var characterDetErr: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.pb_det_char)
        progressBar.visibility = View.VISIBLE
        characterDetErr = view.findViewById(R.id.tv_char_details_err)
        characterDetErr.visibility = View.GONE

        //Guardo donde voy a meter los datos
        val tvName : TextView = view.findViewById(R.id.tv_name)
        val tvStatus : TextView = view.findViewById(R.id.tv_status)
        val tvSpecies : TextView = view.findViewById(R.id.tv_species)
        val tvType : TextView = view.findViewById(R.id.tv_type)
        val tvGender : TextView = view.findViewById(R.id.tv_gender)
        val imAvatar : ImageView = view.findViewById(R.id.im_avatar)

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val rickAndMortyService : RickAndMortyService = retrofit.create(RickAndMortyService::class.java)


        rickAndMortyService.getCharacterInfo(args.charId.toInt()).enqueue(object : Callback<SingleCharacter> {
             override fun onResponse(call: Call<SingleCharacter>, response: Response<SingleCharacter>) {
                if (response.isSuccessful) {
                    val character = response.body()
                    if (character != null){
                        tvName.text = character.name
                        tvStatus.text = character.status
                        tvSpecies.text = character.species
                        if (character.type == "") {
                            tvType.text = "Unknown"
                        } else {
                            tvType.text = character.type
                        }
                        tvGender.text = character.gender
                        Glide.with(this@CharacterDetailsFragment).load(character.image).into(imAvatar)
                    }
                    progressBar.visibility = View.GONE
                    characterDetErr.visibility = View.GONE
                } else {
                    characterDetErr.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<SingleCharacter>, t: Throwable) {
                characterDetErr.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })

    }


}

