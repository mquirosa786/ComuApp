package com.caminoneocatecumenal.comuapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Biblia (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val libro: String,
    val cap : String,
    val vers : String
)