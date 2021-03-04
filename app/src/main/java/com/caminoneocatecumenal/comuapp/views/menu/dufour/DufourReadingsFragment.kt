package com.caminoneocatecumenal.comuapp.views.menu.dufour

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.FragmentDufourReadingsBinding
import com.caminoneocatecumenal.comuapp.models.DufourData
import com.caminoneocatecumenal.comuapp.views.adapters.DufourReadingsAdapter
import com.caminoneocatecumenal.comuapp.views.dialogs.AbbrevAlertDialog


/**
 * A simple [Fragment] subclass.
 */
class DufourReadingsFragment : Fragment() {
    private lateinit var binding: FragmentDufourReadingsBinding
    private lateinit var dufourData: DufourData
    private lateinit var topActivity : DufourActivity
    private lateinit var adapter: DufourReadingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        topActivity = activity as DufourActivity
        dufourData = topActivity.getDufourData()
        topActivity.hideTopRightIcon()
        topActivity.setBackOnClick(View.OnClickListener { topActivity.supportFragmentManager.popBackStack() })
        binding = DataBindingUtil.inflate(
            inflater,  R.layout.fragment_dufour_readings, container, false);

        setUpView()

        return binding.root
    }

    private fun setUpView() {
        val readingsList : List<String> = dufourData.texts.split("&").map { it.trim() }
        adapter = DufourReadingsAdapter(View.OnClickListener {
            showPopUp()
        })
        binding.btnContinue.setOnClickListener {
            topActivity.goToDufourPreparationFragment()
        }
        binding.rvSelectDufour.adapter = adapter
        adapter.submitList(readingsList)
    }

    private fun showPopUp(){
        val arrayList : ArrayList<String> = ArrayList()
        arrayList.add("Levitico (Lev)")
        arrayList.add("Exodo (Ex)")
        arrayList.add("San Marcos (Sam)")
        arrayList.add("San Mateo (Mat)")
        val abbrevDialog : AbbrevAlertDialog = AbbrevAlertDialog.Builder(activity).setOnCloseClick({}).setView(R.layout.alert_dialog_abbrev).setReadingList(arrayList).build()
        abbrevDialog.show()
    }



}
