package com.caminoneocatecumenal.comuapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BibliaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReading(lectura : Biblia)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadingList(lecturas : List<Biblia>)

    @Update
    suspend fun updateReading(lectura : Biblia)

    @Delete
    suspend fun deleteReading (lectura : Biblia)

    @Query( "DELETE FROM Biblia")
    suspend fun deleteAllReadings()

    @Query("SELECT * FROM Biblia")
    fun getAllReadings() : LiveData<List<Biblia>>

    @Query("SELECT * FROM Biblia WHERE cap = :cap AND vers = :vers")
    fun getReadingByCapAndVers(cap : String, vers : String) : LiveData<Biblia>

}