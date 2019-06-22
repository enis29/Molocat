package com.enkapp.molocat.model

data class BreedShort(
    val id : String,
    val name : String,
    val origin : String,
    val temperament : String
){
    override fun toString(): String {
        return "BreedShort(id='$id', name='$name', origin='$origin', temperament=$temperament)"
    }
}
