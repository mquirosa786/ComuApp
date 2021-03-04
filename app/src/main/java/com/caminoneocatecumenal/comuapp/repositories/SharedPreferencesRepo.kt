package com.caminoneocatecumenal.comuapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.caminoneocatecumenal.comuapp.models.DufourData
import com.caminoneocatecumenal.comuapp.utils.SharedPreferencesUtil
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Singleton

object SharedPreferencesRepo {

    fun getJson(key : String, classObj : Class<Any?>) : Any?{
        return SharedPreferencesUtil.getJson(key,classObj)
    }

    fun saveJson(key : String, obj : Any){
        SharedPreferencesUtil.saveJson(key,obj)
    }

}