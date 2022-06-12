package com.victorriddlestone.rickandmorty.episodes


import com.google.gson.annotations.Expose

data class Episodes(
    @Expose
    val air_date: String?,
    @Expose
    val characters: List<String?>?,
    @Expose
    val created: String?,
    @Expose
    val episode: String?,
    @Expose
    val id: Int?,
    @Expose
    val name: String?,
    @Expose
    val url: String?
)