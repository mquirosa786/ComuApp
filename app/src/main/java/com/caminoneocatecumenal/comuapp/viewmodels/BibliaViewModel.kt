package com.caminoneocatecumenal.comuapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caminoneocatecumenal.comuapp.db.Biblia
import com.caminoneocatecumenal.comuapp.repositories.BibliaRepo
import kotlinx.coroutines.launch

class BibliaViewModel(private val repo: BibliaRepo) : ViewModel() {

    val readings = repo.readings

    fun insert(lectura:Biblia){
        viewModelScope.launch {
            repo.insert(lectura = lectura)
        }
    }

    fun delete(lectura:Biblia){
        viewModelScope.launch {
            repo.delete(lectura)
        }
    }

    fun update(lectura: Biblia){
        viewModelScope.launch {
            repo.update(lectura)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            repo.deleteAll()
        }
    }
}