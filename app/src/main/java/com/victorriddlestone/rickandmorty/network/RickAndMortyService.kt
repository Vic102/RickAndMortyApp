package com.victorriddlestone.rickandmorty.network

import retrofit2.Call
import com.victorriddlestone.rickandmorty.characters.CharacterResponse
import com.victorriddlestone.rickandmorty.characters.SingleCharacter
import com.victorriddlestone.rickandmorty.episodes.EpisodesResponse
import com.victorriddlestone.rickandmorty.episodes.Location
import com.victorriddlestone.rickandmorty.episodes.SingleCharacter2
import com.victorriddlestone.rickandmorty.episodes.SingleEpisode
import com.victorriddlestone.rickandmorty.location.LocationResponse
import com.victorriddlestone.rickandmorty.location.SingleLocation
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {
    @GET("character")
    fun getCharacters() : Call<CharacterResponse>

    @GET("character/{id}")
    fun getCharacterInfo(@Path ("id") id : Int) : Call<SingleCharacter>

    @GET("episode")
    fun getEpisodes() : Call<EpisodesResponse>

    @GET("episode/{id}")
    fun getEpisodeInfo(@Path ("id") id : Int) : Call<SingleEpisode>

    @GET("character/{id}")
    fun getCharacterInfoInEpisodes(@Path ("id") id : Int) : Call<SingleCharacter2>

    @GET("location")
    fun getLocation() : Call<LocationResponse>

    @GET("location/{id}")
    fun getLocationInfo(@Path ("id") id : Int) : Call<SingleLocation>






}

