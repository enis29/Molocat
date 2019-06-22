package com.enkapp.molocat.model

data class Breed(
    val id : String,
    val adaptability : Int,
    val affectionLevel : Int,
    val child_friendly : Int,
    val country_code : String,
    val description : String,
    val dog_friendly : Int,
    val energy_level : Int,
    val grooming : Int,
    val hairless : Int,
    val health_issues : Int,
    val hypoallergenic : Int,
    val intelligence : Int,
    val life_span : String,
    val name : String,
    val origin : String,
    val rare : Int,
    val shedding_level : Int,
    val social_needs : Int,
    val stranger_friendly : Int,
    val temperament : String,
    val wikipedia_url : String,
    val weight : Weight
)