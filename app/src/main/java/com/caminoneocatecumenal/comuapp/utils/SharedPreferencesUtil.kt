package com.caminoneocatecumenal.comuapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import timber.log.Timber

object SharedPreferencesUtil {

    val gson : Gson = Gson()

    var sf : SharedPreferences? = null

    fun setSharedPreferencesUtil(context: Context){
        if(sf==null){
            sf = EncryptedSharedPreferences.create(
                "comu_main_share",
                 MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                 context,
                 EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                 EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
        }
    }

    fun saveJson(key : String, obj : Any){
        try {
            val json : String = gson.toJson(obj)
            sf?.edit()?.putString(key,json)?.apply()
        }catch (e:Exception){
            Timber.d("Error parsing JSON !!!")
            Timber.e(e)
        }
    }

    fun getJson (key : String, classObj : Class<Any?>): Any? {
        try {
            val json : String? = sf?.getString(key,null)
            if(json==null)
                return null
            else
                return gson.fromJson(json,classObj)
        }catch (e:Exception){
            Timber.d("Error parsing JSON !!!")
            Timber.e(e)
            return null
        }
    }

}