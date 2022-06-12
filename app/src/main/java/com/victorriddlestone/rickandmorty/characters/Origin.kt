package com.victorriddlestone.rickandmorty.characters


import com.google.gson.annotations.Expose

data class Origin(
    @Expose
    val name: String?,
    @Expose
    val url: String?
)