package com.victorriddlestone.rickandmorty.characters


import com.google.gson.annotations.Expose

data class Location(
    @Expose
    val name: String?,
    @Expose
    val url: String?
)