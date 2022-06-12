package com.victorriddlestone.rickandmorty.characters


import com.google.gson.annotations.Expose

data class Info(
    @Expose
    val count: Int?,
    @Expose
    val next: String?,
    @Expose
    val pages: Int?,
    @Expose
    val prev: Any?
)