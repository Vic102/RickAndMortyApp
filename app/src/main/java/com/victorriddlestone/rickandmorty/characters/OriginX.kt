package com.victorriddlestone.rickandmorty.characters


import com.google.gson.annotations.Expose

data class OriginX(
    @Expose
    val name: String?,
    @Expose
    val url: String?
)