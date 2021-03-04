package com.caminoneocatecumenal.comuapp.views.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.FragmentMenuDufourBinding
import com.caminoneocatecumenal.comuapp.views.NavigationActivity

/**
 * A simple [Fragment] subclass.
 */
class MenuBaseFragment : Fragment() {
    lateinit var binding : FragmentMenuDufourBinding
    val currentActivity : NavigationActivity = activity!!.parent as NavigationActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu_dufour, container, false);

        return binding.root
    }

}
