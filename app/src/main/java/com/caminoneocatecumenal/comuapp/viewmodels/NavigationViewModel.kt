package com.caminoneocatecumenal.comuapp.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caminoneocatecumenal.comuapp.models.DufourData
import com.caminoneocatecumenal.comuapp.repositories.NavigationRepo
import com.caminoneocatecumenal.comuapp.repositories.SharedPreferencesRepo
import com.caminoneocatecumenal.comuapp.utils.SharedPreferencesUtil
import com.caminoneocatecumenal.comuapp.views.menu.stripAccents
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {

    var dufourData : LiveData<List<DufourData>> = NavigationRepo.getDufourDataList()

    //transformations.switch map --> if (variable) changes, then it gets triggered

    fun getAdapterSortedDufourList (dufourList : List<DufourData>)  : List<DufourData> {
        val listIterator = dufourList.listIterator()
        while (listIterator.hasNext()){
            val index = listIterator.nextIndex()
            val data = listIterator.next()
            if(index == 0 || index!=0 && !getAlphabeticalType(data).equals(getAlphabeticalType(
                    dufourData.value!!.get(index-1)))){
                data.showType = true
            }
        }
        return dufourList
    }

    private fun getAlphabeticalType(data : DufourData) : Char{
        return data.name.stripAccents().get(0).toUpperCase()
    }

    fun getJsonFromShared(key : String, classObj : Class<Any?>) : Any?{
        return SharedPreferencesRepo.getJson(key,classObj)
    }

    fun saveJsonToShared(key : String, obj : Any){
        SharedPreferencesRepo.saveJson(key,obj)
    }

}