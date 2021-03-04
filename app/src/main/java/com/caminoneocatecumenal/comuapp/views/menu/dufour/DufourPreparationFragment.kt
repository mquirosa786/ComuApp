package com.caminoneocatecumenal.comuapp.views.menu.dufour

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.FragmentDufourPreparationBinding
import com.caminoneocatecumenal.comuapp.databinding.FragmentDufourReadingsBinding
import com.caminoneocatecumenal.comuapp.models.DufourData
import com.caminoneocatecumenal.comuapp.models.PreparationData
import com.caminoneocatecumenal.comuapp.models.ReadingData
import com.caminoneocatecumenal.comuapp.views.adapters.DufourReadingsAdapter
import com.caminoneocatecumenal.comuapp.views.adapters.SelectDufourAdapter
import com.caminoneocatecumenal.comuapp.views.components.FingerSlider


/**
 * A simple [Fragment] subclass.
 */
class DufourPreparationFragment : Fragment() {
    private lateinit var binding: FragmentDufourPreparationBinding
    private lateinit var dufourData: DufourData
    private lateinit var topActivity : DufourActivity
    private lateinit var preparation : PreparationData
    private lateinit var first : ReadingData
    private lateinit var second : ReadingData
    private lateinit var third : ReadingData
    private lateinit var ev : ReadingData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        topActivity = activity as DufourActivity
        dufourData = topActivity.getDufourData()
        topActivity.hideTopRightIcon()
        topActivity.setBackOnClick(View.OnClickListener { topActivity.supportFragmentManager.popBackStack() })
        topActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dufour_preparation, container, false);

        setUpView()

        return binding.root
    }

    private fun setUpView() {
        binding.btnSave1.setOnClickListener(this::saveData)
        binding.btnSave2.setOnClickListener(this::saveData)
        binding.btnSave3.setOnClickListener(this::saveData)
        binding.btnSave4.setOnClickListener(this::saveData)
    }

    private fun saveData(view : View){
        hideKeyboard(null)
        if(view == binding.btnSave1){
            if(!binding.etReading1.text.isEmpty() && !binding.etReader1.text.isEmpty() && !binding.etMonition1.text.isEmpty()){
                toggleCompletionBox(1)
                setTextToCompletionBox(1,
                    binding.etReading1.text.toString(), binding.etReader1.text.toString(), binding.etMonition1.text.toString()
                )
                first = ReadingData(binding.etReading1.text.toString(), binding.etReader1.text.toString(), binding.etMonition1.text.toString())
            }else{
                //show message saying to complete
            }
        }else if(view == binding.btnSave2){
            if(!binding.etReading2.text.isEmpty() && !binding.etReader2.text.isEmpty() && !binding.etMonition2.text.isEmpty()){
                toggleCompletionBox(2)
                setTextToCompletionBox(2,
                    binding.etReading2.text.toString(), binding.etReader2.text.toString(), binding.etMonition2.text.toString()
                )
                second = ReadingData(binding.etReading2.text.toString(), binding.etReader2.text.toString(), binding.etMonition2.text.toString())
            }else{
                //show message saying to complete
            }
        }else if(view == binding.btnSave3){
            if(!binding.etReading3.text.isEmpty() && !binding.etReader3.text.isEmpty() && !binding.etMonition3.text.isEmpty()){
                toggleCompletionBox(3)
                setTextToCompletionBox(3,
                    binding.etReading3.text.toString(), binding.etReader3.text.toString(), binding.etMonition3.text.toString()
                )
                third = ReadingData(binding.etReading3.text.toString(), binding.etReader3.text.toString(), binding.etMonition3.text.toString())
            }else{
                //show message saying to complete
            }
        }else if(view == binding.btnSave4){
            if(!binding.etReading4.text.isEmpty() && !binding.etReader4.text.isEmpty() && !binding.etMonition4.text.isEmpty()){
                toggleCompletionBox(4)
                setTextToCompletionBox(4,
                    binding.etReading4.text.toString(), binding.etReader4.text.toString(), binding.etMonition4.text.toString()
                )
                ev = ReadingData(binding.etReading4.text.toString(), binding.etReader4.text.toString(), binding.etMonition4.text.toString())
            }else{
                //show message saying to complete
            }
        }
    }

    private fun toggleCompletionBox(box : Int){
        when(box){
            1 -> {
                binding.boxLabelReading.toggleVisibility()
                binding.boxToComplete1.toggleVisibility()
                binding.boxWhenComplete1.toggleVisibility()
            }
            2 -> {
                binding.boxLabelReading2.toggleVisibility()
                binding.boxToComplete2.toggleVisibility()
                binding.boxWhenComplete2.toggleVisibility()
            }
            3 -> {
                binding.boxLabelReading3.toggleVisibility()
                binding.boxToComplete3.toggleVisibility()
                binding.boxWhenComplete3.toggleVisibility()
            }
            4 -> {
                binding.boxLabelReading4.toggleVisibility()
                binding.boxToComplete4.toggleVisibility()
                binding.boxWhenComplete4.toggleVisibility()
            }
        }
    }

    private fun setTextToCompletionBox (box : Int, reading : String, reader : String, monition : String){
        when(box){
            1 -> {
                binding.boxLabelReading.text = reading
                binding.tvReading1.text = reader
                binding.tvMonition1.text = monition
            }
            2 -> {
                binding.boxLabelReading2.text = reading
                binding.tvReading2.text = reader
                binding.tvMonition2.text = monition
            }
            3 -> {
                binding.boxLabelReading3.text = reading
                binding.tvReading3.text = reader
                binding.tvMonition3.text = monition
            }
            4 -> {
                binding.boxLabelReading4.text = reading
                binding.tvReading4.text = reader
                binding.tvMonition4.text = monition
            }
        }
    }

    fun View.toggleVisibility() {
        if (visibility == View.VISIBLE) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
        }
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
