package com.victorriddlestone.rickandmorty.characters


import com.google.gson.annotations.Expose

data class CharacterResponse(
    @Expose
    val info: Info?,
    @Expose
    val results: List<Character?>?
)