package com.caminoneocatecumenal.comuapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Biblia::class],version = 1)
abstract class BibliaDatabase : RoomDatabase() {

    abstract val bibliaDao : BibliaDao

    companion object{
        @Volatile
        private var INSTANCE : BibliaDatabase? = null
        fun getInstance(context : Context):BibliaDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BibliaDatabase::class.java,
                        "biblia_db"
                    ).build()
                }
                return instance
            }
        }
    }
}