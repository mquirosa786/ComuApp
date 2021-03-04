package com.caminoneocatecumenal.comuapp.views.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.FragmentHomeDufourBinding
import com.caminoneocatecumenal.comuapp.databinding.FragmentMenuDufourBinding
import com.caminoneocatecumenal.comuapp.viewmodels.NavigationViewModel
import com.caminoneocatecumenal.comuapp.views.adapters.SelectDufourAdapter

/**
 * A simple [Fragment] subclass.
 */
class MenuHomeFragment : Fragment() {
    lateinit var binding : FragmentHomeDufourBinding
    private lateinit var navigationViewModel : NavigationViewModel
    private lateinit var adapter: SelectDufourAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home_dufour, container, false);

        navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)

        return binding.root
    }


}
