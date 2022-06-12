package com.victorriddlestone.rickandmorty.characters


import com.google.gson.annotations.Expose

data class SingleCharacter(
    @Expose
    val created: String?,
    @Expose
    val episode: List<String>?,
    @Expose
    val gender: String?,
    @Expose
    val id: Int?,
    @Expose
    val image: String?,
    @Expose
    val location: LocationX?,
    @Expose
    val name: String?,
    @Expose
    val origin: OriginX?,
    @Expose
    val species: String?,
    @Expose
    val status: String?,
    @Expose
    val type: String?,
    @Expose
    val url: String?
)