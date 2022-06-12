package com.victorriddlestone.rickandmorty.episodes


import com.google.gson.annotations.Expose

data class SingleCharacter2(
    @Expose
    val created: String?,
    @Expose
    val episode: List<String?>?,
    @Expose
    val gender: String?,
    @Expose
    val id: Int?,
    @Expose
    val image: String?,
    @Expose
    val location: Location?,
    @Expose
    val name: String?,
    @Expose
    val origin: Origin?,
    @Expose
    val species: String?,
    @Expose
    val status: String?,
    @Expose
    val type: String?,
    @Expose
    val url: String?
)