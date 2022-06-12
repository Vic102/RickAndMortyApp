package com.victorriddlestone.rickandmorty.episodes


import com.google.gson.annotations.Expose

data class EpisodesResponse(
    @Expose
    val info: Info?,
    @Expose
    val results: List<Episodes?>?
)