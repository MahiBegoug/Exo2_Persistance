package com.example.exo2.db

import java.io.Serializable

data class Intervention (
    var id:Int, val interventionDate:String,
    val nomPlombier:String, val typeIntervention: String)
:Serializable
{
}