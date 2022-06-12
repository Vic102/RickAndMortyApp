package com.victorriddlestone.rickandmorty.characters


import com.google.gson.annotations.Expose

data class LocationX(
    @Expose
    val name: String?,
    @Expose
    val url: String?
)