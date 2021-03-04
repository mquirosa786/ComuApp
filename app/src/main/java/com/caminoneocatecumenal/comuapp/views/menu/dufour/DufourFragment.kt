package com.caminoneocatecumenal.comuapp.views.menu.dufour

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.FragmentDufourBinding
import com.caminoneocatecumenal.comuapp.models.DufourData
import com.caminoneocatecumenal.comuapp.views.components.FingerSlider
import com.caminoneocatecumenal.comuapp.views.dialogs.AbbrevAlertDialog
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * A simple [Fragment] subclass.
 */
class DufourFragment : Fragment() {
    private lateinit var binding: FragmentDufourBinding
    private lateinit var dufourData: DufourData
    private lateinit var topActivity : DufourActivity
    private var currentSize : Float = 14f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        topActivity = activity as DufourActivity
        dufourData = topActivity.getDufourData()
        topActivity.setBackOnClick(View.OnClickListener { topActivity.finish(); })
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(
                true // default to enabled
            ) {
                override fun handleOnBackPressed() {
                    topActivity.finish()
                }
            }
        requireActivity().getOnBackPressedDispatcher().addCallback(
            this, // LifecycleOwner
            callback);
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dufour, container, false);

        setUpView()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun setUpView() {
        binding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, currentSize);
        val text = SpannableString(HtmlCompat.fromHtml(dufourData.htmlFile+"@Mario", HtmlCompat.FROM_HTML_MODE_LEGACY))
        val matcher : Matcher = Pattern.compile("\\(([^)]+)\\)").matcher(text)

        while(matcher.find()){
            text.setSpan(ForegroundColorSpan(Color.parseColor("#0000FF")),matcher.start(),matcher.end(),0)
            val tag : String = matcher.group(0)

            val clickableSpan : ClickableSpan = object : ClickableSpan() {
                override fun onClick(p0: View) {
                    val arrayList : ArrayList<String> = ArrayList()
                    arrayList.add("Levitico (Lev)")
                    arrayList.add("Exodo (Ex)")
                    arrayList.add("San Marcos (Sam)")
                    arrayList.add("San Mateo (Mat)")
                    val abbrevDialog : AbbrevAlertDialog = AbbrevAlertDialog.Builder(activity).setOnCloseClick({}).setView(R.layout.alert_dialog_abbrev).setReadingList(arrayList).build()
                    abbrevDialog.show()
                }
            }
            text.setSpan(clickableSpan,matcher.start(),matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.tvContent.setText(text)
        binding.tvContent.movementMethod = LinkMovementMethod.getInstance()


        topActivity.setFontSizeIcon(View.OnClickListener {
            if(currentSize > 6f){
                currentSize -= 2f
                binding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, currentSize);
                topActivity.setFontSizeLabel(getCurrentSizePercentage(currentSize))
            }
        }, View.OnClickListener {
            if(currentSize < 22f){
                currentSize += 2f
                binding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, currentSize);
                topActivity.setFontSizeLabel(getCurrentSizePercentage(currentSize))
            }
        })

        binding.fingerSlider.setHint(R.string.show_label_dufour)
        binding.fingerSlider.setProgressText(R.string.showing_label_dufour)
        binding.fingerSlider.setCallbacks(object : FingerSlider.Callbacks{
            override fun onStart() {

            }

            override fun onSuccess() {
                binding.fingerSlider.showCompletion(R.string.showing_label_dufour)
                topActivity.goToDufourReadingsFragment()
            }

            override fun onCancel() {

            }
        })

    }

    private fun getCurrentSizePercentage (currentSize : Float) : Int = when(currentSize){
        6f -> 10
        8f -> 25
        10f -> 50
        12f -> 75
        14f -> 100
        16f -> 125
        18f -> 150
        20f -> 175
        22f -> 200
        else -> 100
    }

    private fun getPixels(dp : Float) : Int {
        val metrics = context!!.resources.displayMetrics
        val fpixels = metrics.density * dp
        return (fpixels + 0.5f).toInt()
    }

}