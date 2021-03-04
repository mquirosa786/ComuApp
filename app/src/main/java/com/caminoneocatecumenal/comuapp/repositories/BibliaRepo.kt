package com.caminoneocatecumenal.comuapp.repositories

import com.caminoneocatecumenal.comuapp.db.Biblia
import com.caminoneocatecumenal.comuapp.db.BibliaDao

class BibliaRepo(private val dao : BibliaDao) {
    val readings = dao.getAllReadings()

    suspend fun insert(lectura : Biblia){
        dao.insertReading(lectura)
    }

    suspend fun insertList(lecturas : List<Biblia>){
        dao.insertReadingList(lecturas)
    }

    suspend fun update(lectura: Biblia){
        dao.updateReading(lectura)
    }

    suspend fun delete(lectura: Biblia){
        dao.deleteReading(lectura)
    }

    suspend fun deleteAll(){
        dao.deleteAllReadings()
    }


}