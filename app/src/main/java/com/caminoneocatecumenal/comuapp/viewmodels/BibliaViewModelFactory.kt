package com.caminoneocatecumenal.comuapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.caminoneocatecumenal.comuapp.repositories.BibliaRepo

class BibliaViewModelFactory(private val repo: BibliaRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BibliaViewModel::class.java)){
            return BibliaViewModel(repo) as T
        }

            throw IllegalArgumentException("Unknown View Model class")
    }
}