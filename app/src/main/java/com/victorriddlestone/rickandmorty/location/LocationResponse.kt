package com.victorriddlestone.rickandmorty.location


import com.google.gson.annotations.Expose

data class LocationResponse(
    @Expose
    val info: Info?,
    @Expose
    val results: List<Location?>?
)