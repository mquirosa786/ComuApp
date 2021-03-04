package com.caminoneocatecumenal.comuapp.views.menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.FragmentMenuDufourBinding
import com.caminoneocatecumenal.comuapp.models.DufourData
import com.caminoneocatecumenal.comuapp.viewmodels.NavigationViewModel
import com.caminoneocatecumenal.comuapp.views.menu.dufour.DufourActivity
import com.caminoneocatecumenal.comuapp.views.adapters.SelectDufourAdapter
import com.caminoneocatecumenal.comuapp.views.adapters.SelectFromSearchDufourAdapter
import timber.log.Timber
import java.text.Normalizer


/**
 * A simple [Fragment] subclass.
 */
class MenuDufourFragment : Fragment() {
    lateinit var binding : FragmentMenuDufourBinding
    private lateinit var navigationViewModel : NavigationViewModel
    private lateinit var adapter: SelectDufourAdapter
    private lateinit var adapterForSearch: SelectFromSearchDufourAdapter
    private lateinit var dufourDataListFromViewModel: List<DufourData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu_dufour, container, false);

        navigationViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)
        setView()
        return binding.root
    }

    private fun setView() {
        setAdapter()
        navigationViewModel.dufourData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(navigationViewModel.getAdapterSortedDufourList(it.sortedBy { it.name.stripAccents() }))
            dufourDataListFromViewModel = it
        })

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if(s.isEmpty()){
                    binding.llEditSearch.clearFocus()
                    binding.edtSearch.clearFocus()
                    binding.edtSearch.clearFocus()
                    binding.imageClose.visibility = View.GONE
                    hideSearchResults()
                }else{
                    binding.imageClose.visibility = View.VISIBLE
                    showSearchResults(dufourDataListFromViewModel.filter { it.name.stripAccents().contains(
                        s.toString().stripAccents()
                    ,ignoreCase = true) })
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.imageClose.setOnClickListener {
                binding.edtSearch.setText("")
                binding.imageClose.visibility = View.GONE
                hideKeyboard(binding.edtSearch)
                binding.rvSelectDufour.requestFocus()
                hideSearchResults()
        }

        binding.edtSearch.setOnFocusChangeListener  { v,hasFocus ->
                if(hasFocus)
                    binding.llEditSearch.background = resources.getDrawable(R.drawable.card_search_dufour_onfocus,null)
                else
                    binding.llEditSearch.background = resources.getDrawable(R.drawable.card_search_dufour,null)
        }

        binding.llEditSearch.setOnClickListener  {
            onFocusAndShowKeyboardOf(binding.edtSearch)
        }

        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_DONE) {
                hideKeyboard(binding.edtSearch)
            }
            false
        }

    }

    private fun setAdapter() {
        adapter = SelectDufourAdapter(View.OnClickListener {
                val dufourData : DufourData = it.tag as DufourData
                val intent = Intent(activity?.getBaseContext(), DufourActivity::class.java)
                intent.putExtra("EXTRA_DUFOUR_DATA", dufourData)
                startActivity(intent)
        })
        adapterForSearch = SelectFromSearchDufourAdapter(View.OnClickListener {
                val dufourData : DufourData = it.tag as DufourData
                val intent = Intent(activity?.getBaseContext(), DufourActivity::class.java)
                intent.putExtra("EXTRA_DUFOUR_DATA", dufourData)
                startActivity(intent)
        })
        binding.rvSelectDufour.adapter = adapter
        binding.rvSelectFromSearchDufour.adapter = adapterForSearch
    }

    private fun showSearchResults(dufourDataList : List<DufourData>){
        adapterForSearch.submitList(dufourDataList.sortedBy { it.name })
        binding.rvSelectDufour.visibility = View.GONE
        binding.svSearchResults.visibility = View.VISIBLE
    }

    private fun hideSearchResults(){
        binding.rvSelectDufour.visibility = View.VISIBLE
        binding.svSearchResults.visibility = View.GONE
    }

    protected fun hideKeyboard(editText: EditText?) {
//        if (editText!=null) editText.clearFocus();
        val imm = activity?.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager?
        var view: View?
        if (editText != null) {
            view = editText
        } else {
            view = activity?.getCurrentFocus()
        }
        //Find the currently focused view, so we can grab the correct window token from it.
        //view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun onFocusAndShowKeyboardOf(editText: EditText) {
        editText.requestFocusFromTouch()
        val imm = (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?)!!
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}

 fun String.stripAccents(): String {
     val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
     val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
     return REGEX_UNACCENT.replace(temp, "")
}
