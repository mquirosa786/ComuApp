package com.mquirosa786.bottommenu

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import com.mquirosa786.bottommenu.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.menu_cell.view.*


/**
 * Created by 1HE on 2/23/2019.
 */


class BottomMenuCell : RelativeLayout, LayoutContainer {
    companion object {
        const val EMPTY_VALUE = "empty"
    }

    var defaultIconColor = 0
    var selectedIconColor = 0
    var circleColor = 0
    var enableMenuItem = true;

    var icon = 0
        set(value) {
            field = value
            if (allowDraw)
                iv.resource = value
        }

    var count: String? = EMPTY_VALUE
        set(value) {
            field = value
            if (allowDraw) {
                if (count != null && count == EMPTY_VALUE) {
//                    tv_count.text = ""
//                    tv_count.visibility = View.INVISIBLE
                } else {
                    if (count != null && count?.length ?: 0 >= 3) {
                        field = count?.substring(0, 1) + ".."
                    }
//                    tv_count.text = count
//                    tv_count.visibility = View.VISIBLE
                    //val scale = if (count?.isEmpty() == true) 0.5f else 1f
//                    tv_count.scaleX = scale
//                    tv_count.scaleY = scale
                }
            }
        }

    private var iconSize = dip(context, 44)
        set(value) {
            field = value
            if (allowDraw) {
                iv.size = value
                iv.y = 10f;//MQ
                iv.setBackgroundColor(Color.TRANSPARENT)
            }
        }

    var countTextColor = 0
        set(value) {
            field = value
            if (allowDraw){}
//                tv_count.setTextColor(field)
        }

    var countBackgroundColor = 0
        set(value) {
            field = value
            if (allowDraw) {
                val d = GradientDrawable()
                d.setColor(field)
                d.shape = GradientDrawable.OVAL
//                ViewCompat.setBackground(tv_count, d)
            }
        }

    var countTypeface: Typeface? = null
        set(value) {
            field = value
            if (allowDraw && field != null){}
            /*tv_icon_title.typeface = field*/
        }

    var rippleColor = 0
        set(value) {
            field = value
            if (allowDraw) {

            }
        }

    var iconTitle: String? = ""
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        set(value) {
            field = value
            tv_icon_title.setText(iconTitle)
//            var tv_icon_obj = findViewById<TextView>(R.id.tv_icon_title)
//            var fl_obj = findViewById<ConstraintLayout>(R.id.fl)
//            var lp: ViewGroup.LayoutParams = tv_icon_obj.layoutParams;
//            lp.apply {
//                textAlignment = View.TEXT_ALIGNMENT_CENTER
//                width = fl_obj.layoutParams.width
//            }
//            tv_icon_obj.setLayoutParams(lp);

        }

    var isFromLeft = false
    var duration = 0L
    private var progress = 0f //when on click, progress = 1f
        set(value) {
            field = value
            iv.color = if(enableMenuItem) {if (progress > 0.5f) selectedIconColor else defaultIconColor} else {Color.parseColor("#443c415e")}
            //var typeFaceInt = if (progress == 1f) Typeface.NORMAL else Typeface.BOLD
            if (progress == 1f){
                tv_icon_title.setTypeface(tv_icon_title.getTypeface(), Typeface.BOLD);
                tv_icon_title.setTextColor(resources.getColor(R.color.orange))
            }else{
                if(enableMenuItem){
                    tv_icon_title.setTypeface(tv_icon_title.getTypeface(), Typeface.NORMAL);
                    tv_icon_title.setTextColor(Color.BLACK)
                }else{
                    setTextColor("#443c415e")
                }
            }
            iv.y = (1f - progress) * dip(context, 20) + dip(context, 20)//Y location of icons
            val scale = (1f - progress) * (-0.2f) + 1f
            iv.scaleX = scale
            iv.scaleY = scale
            //tv_icon_title.layoutParams.width =  fl.layoutParams.width

            val d = GradientDrawable()
            d.setColor(circleColor)
            d.shape = GradientDrawable.OVAL
            ViewCompat.setBackground(v_circle, d)
            if(progress > 0.5f){
                v_circle.visibility = View.VISIBLE
            }else{
                v_circle.visibility = View.INVISIBLE
            }

            ViewCompat.setElevation(v_circle, if (progress > 0.7f) dipf(context, progress * 6f) else 0f)

            val m = dip(context, 1)
            v_circle.x = (1f - progress) * (if (isFromLeft) -m else m) + ((measuredWidth - dip(context, 48)) / 2f)
            //v_circle.y = (1f - progress) * measuredHeight + dip(context, 17)//location Y of the circle MQ
            v_circle.y = dipf(context, 18)//location Y of the circle MQ
            v_circle.scaleY = scale+0.03f
            v_circle.scaleX = scale+0.03f
        }

    var isEnabledCell = false
        set(value) {
            field = value
            val d = GradientDrawable()
            d.setColor(circleColor)
            d.shape = GradientDrawable.OVAL
            if (Build.VERSION.SDK_INT >= 21 && !isEnabledCell) {
                fl.setBackgroundColor(Color.TRANSPARENT)//background color on click
            } else {
                fl.runAfterDelay(200) {
                    fl.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }

    var onClickListener: () -> Unit = {}
        set(value) {
            field = value
            if(enableMenuItem){
                iv?.setOnClickListener {
                    onClickListener()
                }
                fl?.setOnClickListener {
                    onClickListener()
                }
            }
            //above is onclick listener for the menu items
            /*rl_tv?.setOnClickListener {
                onClickListener()
            }*/
        }

    override lateinit var containerView: View
    private var allowDraw = false

    fun disableMenuItem(){
        enableMenuItem = false;
        onClickListener = {
            //nada
        }
    }

    fun setTextColor(color: String){
        tv_icon_title.setTextColor(Color.parseColor(color))
    }

    fun setIconColor(color: String){
        defaultIconColor = Color.parseColor(color)
        selectedIconColor = Color.parseColor(color)
        iv.color = defaultIconColor
    }

    constructor(context: Context) : super(context) {
        initializeView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setAttributeFromXml(context, attrs)
        initializeView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setAttributeFromXml(context, attrs)
        initializeView()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun setAttributeFromXml(context: Context, attrs: AttributeSet) {
    }

    private fun initializeView() {
        allowDraw = true
        containerView = LayoutInflater.from(context).inflate(R.layout.menu_cell, this)
        draw()
    }

    private fun draw() {
        if (!allowDraw)
            return

        icon = icon
        count = count
        iconTitle = iconTitle
        iconSize = iconSize
        countTextColor = countTextColor
        countBackgroundColor = countBackgroundColor
        countTypeface = countTypeface
        rippleColor = rippleColor
        onClickListener = onClickListener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        progress = progress
    }

    fun disableCell() {
        if (isEnabledCell)
            animateProgress(false)
        isEnabledCell = false
    }

    fun enableCell(isAnimate: Boolean = true) {
        if (!isEnabledCell)
            animateProgress(true, isAnimate)
        isEnabledCell = true
    }

    private fun animateProgress(enableCell: Boolean, isAnimate: Boolean = true) {
        val d = 400L
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.apply {
            startDelay = 0L
            duration = d
            interpolator = null
            addUpdateListener {
                val f = it.animatedFraction
                progress = if (enableCell)
                    f
                else
                    1f - f
            }
            start()
        }
    }

    fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
        if (v.layoutParams is MarginLayoutParams) {
            val p = v.layoutParams as MarginLayoutParams
            p.setMargins(l, t, r, b)
            v.requestLayout()
        }
    }

}