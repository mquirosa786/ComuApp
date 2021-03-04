package com.mquirosa786.bottommenu

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.util.LayoutDirection
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import java.util.*

/**
 * Created by 1HE on 10/23/2018.
 */

internal typealias IBottomNavigationListener = (model: BottomMenuLayout.Model) -> Unit

class BottomMenuLayout : FrameLayout {

    var models = ArrayList<Model>()
    public var cells = ArrayList<BottomMenuCell>()
    var callListenerWhenIsSelected = false

    var selectedId =  2

    private var mOnClickedListener: IBottomNavigationListener = {}
    private var mOnShowListener: IBottomNavigationListener = {}

    private var heightCell = 0
    private var isAnimating = false

    private var defaultIconColor = Color.BLACK
    private var selectedIconColor = resources.getColor(R.color.orange)
    private var backgroundBottomColor = Color.WHITE
    private var shadowColor = -0x454546
    private var countTextColor = Color.BLACK
    private var countBackgroundColor = Color.parseColor("#ff0000")
    private var countTypeface: Typeface? = null
    private var rippleColor = resources.getColor(R.color.orange)
    private var circleBackgroundColor = resources.getColor(R.color.orange)

    @Suppress("PrivatePropertyName")
    private lateinit var ll_cells: LinearLayout
    private lateinit var bezierView: BezierView

    init {
        heightCell = dip(context,120)//135

    }

    constructor(context: Context) : super(context) {
        initializeViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setAttributeFromXml(context, attrs)
        initializeViews()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setAttributeFromXml(context, attrs)
        initializeViews()
    }

    private fun setAttributeFromXml(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.BottomMenuLayout, 0, 0)
        try {
            a.apply {
                defaultIconColor = getColor(R.styleable.BottomMenuLayout_mbn_defaultIconColor, defaultIconColor)
                selectedIconColor = getColor(R.styleable.BottomMenuLayout_mbn_selectedIconColor, selectedIconColor)
                backgroundBottomColor = getColor(R.styleable.BottomMenuLayout_mbn_backgroundBottomColor, backgroundBottomColor)
                countTextColor = getColor(R.styleable.BottomMenuLayout_mbn_countTextColor, countTextColor)
                countBackgroundColor = getColor(R.styleable.BottomMenuLayout_mbn_countBackgroundColor, countBackgroundColor)
                rippleColor = getColor(R.styleable.BottomMenuLayout_mbn_rippleColor, rippleColor)
                shadowColor = getColor(R.styleable.BottomMenuLayout_mbn_shadowColor, shadowColor)
                //circleBackgroundColor = getColor(R.styleable.MeowBottomNavigation_mbn_backgroundBottomColor,circleBackgroundColor)//MQ


            }
        } finally {
            a.recycle()
        }
    }

    private fun initializeViews() {
        ll_cells = LinearLayout(context)
        ll_cells.apply {
            val params = LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightCell)
            params.gravity = Gravity.BOTTOM
            layoutParams = params
            orientation = LinearLayout.HORIZONTAL
            clipChildren = true
            clipToPadding = true
        }

        bezierView = BezierView(context)
        bezierView.apply {
            layoutParams = LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightCell)
            color = backgroundBottomColor
            shadowColor = this@BottomMenuLayout.shadowColor
        }

        addView(bezierView)
        addView(ll_cells)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (selectedId == -1) {
            bezierView.bezierX = if (Build.VERSION.SDK_INT >= 17 && layoutDirection == LayoutDirection.RTL) measuredWidth + dipf(context, 72) else -dipf(context, 72)
        }
        if (selectedId != -1) {
            show(selectedId, false)
        }
    }

    fun add(model: Model) {
        val cell = BottomMenuCell(context)
        cell.apply {
            val params = LinearLayout.LayoutParams(0, heightCell, 1f)
            layoutParams = params
            icon = model.icon
            count = model.count
            iconTitle = model.iconTitle
            circleColor = this@BottomMenuLayout.circleBackgroundColor
            countTextColor = this@BottomMenuLayout.countTextColor
            countBackgroundColor = this@BottomMenuLayout.countBackgroundColor
            countTypeface = this@BottomMenuLayout.countTypeface
            rippleColor = this@BottomMenuLayout.rippleColor
            defaultIconColor = this@BottomMenuLayout.defaultIconColor
            selectedIconColor = this@BottomMenuLayout.selectedIconColor
            onClickListener = {
                if (!cell.isEnabledCell && !isAnimating) {
                    show(model.id)
                    mOnClickedListener(model)
                } else {
                    if (callListenerWhenIsSelected)
                        mOnClickedListener(model)
                }
            }
            disableCell()
            ll_cells.addView(this)
        }

        cells.add(cell)
        models.add(model)
       // show(1)
    }

    fun show(id: Int, enableAnimation: Boolean = true) {
        for (i in models.indices) {
            val model = models[i]
            val cell = cells[i]
            if (model.id == id) {
                anim(cell, id, enableAnimation)
                cell.enableCell()
                mOnShowListener(model)
            } else {
                cell.disableCell()
            }
        }
        selectedId = id
    }

    fun disableMenuItem(id: Int, disableOnClickListener: OnClickListener){
        val cell = getCellById(id)
        cell?.disableMenuItem()
        cell!!.onClickListener = {
            disableOnClickListener.onClick(this);
        }
        cell.defaultIconColor = Color.parseColor("#443c415e");
        cell.countTextColor = Color.parseColor("#443c415e");
        cell.rippleColor = Color.parseColor("#443c415e");
        cell.selectedIconColor = Color.parseColor("#443c415e");
        cell.setTextColor("#443c415e")
        cell.setIconColor("#443c415e")
    }

    private fun anim(cell: BottomMenuCell, id: Int, enableAnimation: Boolean = true) {
        isAnimating = true

        val pos = getModelPosition(id)
        val nowPos = getModelPosition(selectedId)

        val nPos = if (nowPos < 0) 0 else nowPos
        val dif = Math.abs(pos - nPos)
        val d = (dif) * 100L + 150L

        val animDuration = 400L
        val animInterpolator = FastOutSlowInInterpolator()//LinearInterpolator()

        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.apply {
            duration = animDuration
            interpolator = animInterpolator
            val beforeX = bezierView.bezierX
            addUpdateListener {
                val f = it.animatedFraction
                val newX = cell.x + (cell.measuredWidth / 2)
                if (newX > beforeX)
                    bezierView.bezierX = f * (newX - beforeX) + beforeX
                else
                    bezierView.bezierX = beforeX - f * (beforeX - newX)
                if (f == 1f)
                    isAnimating = false
            }
            start()
        }

        if (Math.abs(pos - nowPos) > 1) {
            val progressAnim = ValueAnimator.ofFloat(0f, 1f)
            progressAnim.apply {
                duration = animDuration
                interpolator = animInterpolator
                addUpdateListener {
                    val f = it.animatedFraction
                    bezierView.progress = f * 2f
                }
                start()
            }
        }


        cell.isFromLeft = pos > nowPos
        cells.forEach {
            it.duration = 400L
        }
    }

    fun isShowing(id: Int): Boolean {
        return selectedId == id
    }

    fun getModelById(id: Int): Model? {
        models.forEach {
            if (it.id == id)
                return it
        }
        return null
    }

    fun getCellById(id: Int): BottomMenuCell? {
        return cells[getModelPosition(id)]
    }

    fun getModelPosition(id: Int): Int {
        for (i in models.indices) {
            val item = models[i]
            if (item.id == id)
                return i
        }
        return -1
    }

    fun setCount(id: Int, count: String) {
        val model = getModelById(id) ?: return
        val pos = getModelPosition(id)
        model.count = count
        cells[pos].count = count
    }

    fun setOnShowListener(listener: IBottomNavigationListener) {
        mOnShowListener = listener
    }

    fun setOnClickMenuListener(listener: IBottomNavigationListener) {
        mOnClickedListener = listener
    }

    class Model(var id: Int, var icon: Int, var iconTitle: String) {

        var count: String = BottomMenuCell.EMPTY_VALUE

    }
}