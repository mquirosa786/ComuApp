package com.caminoneocatecumenal.comuapp.views.menu.dufour

import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.ActivityDufourBinding
import com.caminoneocatecumenal.comuapp.models.DufourData
import com.caminoneocatecumenal.comuapp.viewmodels.NavigationViewModel
import kotlinx.android.synthetic.main.activity_dufour.*


class DufourActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDufourBinding
    private lateinit var dufourData: DufourData
    private lateinit var navigationActivityViewModel : NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dufour)
        navigationActivityViewModel = ViewModelProvider(this).get(NavigationViewModel::class.java)

        dufourData = intent.extras!!.getParcelable("EXTRA_DUFOUR_DATA")!!
        setView(dufourData)
    }

    private fun setView(dufourData: DufourData?) {
        if (dufourData != null) {
            this.topText.setText(Html.fromHtml(dufourData.name))
        }
        binding.topRightIcon.setOnClickListener {
            binding.boxFontSizeChange.toggleVisibility()
        }
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.contentBox, DufourFragment()).addToBackStack(null).commit()
        setBackOnClick(View.OnClickListener { finish(); })
    }

    fun setBackOnClick(onClick : View.OnClickListener){
        topLeftIcon.setOnClickListener(onClick)
    }

    fun getDufourData() : DufourData{
        return this.dufourData
    }

    fun goToDufourReadingsFragment(){
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.contentBox, DufourReadingsFragment()).addToBackStack(null).commit()
        setBackOnClick(View.OnClickListener { finish(); })
    }

    fun goToDufourPreparationFragment(){
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.contentBox, DufourPreparationFragment()).addToBackStack(null).commit()
        setBackOnClick(View.OnClickListener { finish(); })
    }

    fun setFontSizeIcon(onClickWhenToLowSize : View.OnClickListener,onClickWhenToBigSize : View.OnClickListener){
        binding.topRightIcon.visibility = View.VISIBLE
        binding.tvToLowSize.setOnClickListener(onClickWhenToLowSize)
        binding.tvToBigSize.setOnClickListener(onClickWhenToBigSize)
    }

    fun View.toggleVisibility() {
        if (visibility == View.VISIBLE) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
        }
    }

    fun hideTopRightIcon(){
        binding.topRightIcon.visibility = View.GONE
        binding.boxFontSizeChange.visibility = View.GONE
    }

    fun setFontSizeLabel(currentSize : Int){
        binding.tvCurrentSize.text = String.format("%s%%",currentSize)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
