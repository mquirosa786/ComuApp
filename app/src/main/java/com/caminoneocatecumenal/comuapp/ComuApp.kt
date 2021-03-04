package com.caminoneocatecumenal.comuapp

import android.app.Application
import com.caminoneocatecumenal.comuapp.utils.SharedPreferencesUtil
import org.greenrobot.eventbus.EventBus

class ComuApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtil.setSharedPreferencesUtil(applicationContext)
    }

}