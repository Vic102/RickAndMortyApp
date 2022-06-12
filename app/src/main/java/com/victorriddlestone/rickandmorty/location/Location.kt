package com.victorriddlestone.rickandmorty.location


import com.google.gson.annotations.Expose

data class Location(
    @Expose
    val created: String?,
    @Expose
    val dimension: String?,
    @Expose
    val id: Int?,
    @Expose
    val name: String?,
    @Expose
    val residents: List<String?>?,
    @Expose
    val type: String?,
    @Expose
    val url: String?
)