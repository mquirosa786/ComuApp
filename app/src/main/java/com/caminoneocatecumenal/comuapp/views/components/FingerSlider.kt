package com.caminoneocatecumenal.comuapp.views.components

import android.animation.Animator
import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.*
import android.view.accessibility.AccessibilityManager
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.caminoneocatecumenal.comuapp.R
import com.caminoneocatecumenal.comuapp.databinding.FingerSliderBinding
import timber.log.Timber

class FingerSlider : FrameLayout {
    private enum class State {
        STARTED, LOADING, COMPLETED, ERROR, IDLE, DISABLED
    }

    private lateinit var binding: FingerSliderBinding
    private var startX = 0f
    private var callbacks: Callbacks? = null
    private var currentState = State.IDLE
    private var hintTextRes = 0
    private var midTextRes = 0

    interface Callbacks {
        fun onStart()
        fun onSuccess()
        fun onCancel()
    }

    constructor(context: Context) : super(context) {
        inflateLayout(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        inflateLayout(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        inflateLayout(context)
    }

    private fun inflateLayout(context: Context) {
//        LayoutInflater.from(context).inflate(R.layout.view_slide_to_complete, this, true);
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.finger_slider,
            this,
            true
        )
        //        ButterKnife.bind(this, this);
        binding.slideToCompleteLoadingIndicator.setVisibility(View.GONE)
        binding.slideToCompleteLoadingIndicator.getIndeterminateDrawable().setColorFilter(
            ContextCompat.getColor(context, R.color.white),
            PorterDuff.Mode.SRC_IN
        )
    }

    fun setCallbacks(callbacks: Callbacks?) {
        this.callbacks = callbacks
    }

    fun setHint(@StringRes resId: Int) {
        hintTextRes = resId
        binding.slideToCompleteHintText.setText(resId)
        binding.slideToCompleteMidText.setText(resId)
        binding.slideToCompleteHintText.setTextSize(14f)
    }

    fun setProgressText(@StringRes resId: Int) {
        midTextRes = resId
    }

    fun setAccessibilityActionHint(@StringRes resId: Int) {
        val accessibilityManager =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        if (accessibilityManager.isEnabled) {
            // Note: this will be enabled for any kind of accessibility (e.g. switch access) and not only talkback
            // Not sure if this is the behaviour we want
            binding.slideToCompleteHintText.setOnClickListener { v ->
                if (currentState == State.IDLE) {
                    beginLoading()
                }
            }
            binding.slideToCompleteHintText.setAccessibilityDelegate(
                CustomActionAccessibilityDelegate(context.getString(resId))
            )
        }
    }

    fun showCompletion(@StringRes resId: Int) {
        binding.slideToCompleteDoneText.setText(resId)
        binding.slideToCompleteDoneText.setVisibility(View.VISIBLE)
        binding.slideToCompleteMidText.setVisibility(View.GONE)
        binding.slideToCompleteLoadingIndicator.setVisibility(View.GONE)
        binding.slideToCompleteForeground.setBackgroundResource(R.drawable.slider_foreground)
        binding.slideToCompleteThumb.setImageResource(R.drawable.slider_thumb_checkmark)
        binding.slideToCompleteThumb.setVisibility(View.VISIBLE)
        binding.completeThumb.setVisibility(View.VISIBLE)
        binding.slideToCompleteThumb.bringToFront()
        currentState = State.COMPLETED
    }

    fun showError(@StringRes resId: Int) {
        binding.slideToCompleteDoneText.setText(resId)
        binding.slideToCompleteDoneText.setVisibility(View.VISIBLE)
        binding.slideToCompleteMidText.setVisibility(View.GONE)
        binding.slideToCompleteLoadingIndicator.setVisibility(View.GONE)
        binding.slideToCompleteForeground.setBackgroundResource(R.drawable.slider_foreground_error)
        binding.slideToCompleteThumb.setImageResource(R.drawable.slider_thumb_error)
        currentState = State.ERROR
    }

    @JvmOverloads
    fun reset(withAnimation: Boolean = false) {
        binding.slideToCompleteThumb.setImageResource(R.drawable.slider_thumb_arrow)
        binding.slideToCompleteForeground.setBackgroundResource(R.drawable.slider_foreground)
        binding.slideToCompleteHintText.setTextColor(resources.getColor(R.color.title_grey))
        binding.slideToCompleteLoadingIndicator.setVisibility(View.GONE)
        binding.slideToCompleteMidText.setVisibility(View.GONE)
        binding.slideToCompleteDoneText.setVisibility(View.GONE)
        binding.completeThumb.setVisibility(View.GONE)
        moveSlider(0f)
        currentState = State.IDLE
    }

    override fun setEnabled(isEnabled: Boolean) {
        super.setEnabled(isEnabled)
        if (isEnabled) {
            reset()
        } else {
//            binding.slideToCompleteHintText.setTextColor(ContextCompat.getColor(this.getContext(), R.color.scotiaGreyLight));
            binding.slideToCompleteThumb.setImageResource(R.drawable.slider_thumb_arrow_disabled)
            binding.slideToCompleteForeground.setBackgroundResource(R.drawable.slider_foreground_disabled)
            currentState = State.DISABLED
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isEnabled) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    onDownTouchEvent(event)
                    return true
                }
                MotionEvent.ACTION_MOVE -> onMoveTouchEvent(event)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> onReleasedTouchEvent()
                else -> Timber.d("Non tracked event %sss", event.action)
            }
        }
        return super.onTouchEvent(event)
    }

    private fun onDownTouchEvent(event: MotionEvent) {
        if (currentState == State.IDLE) {
            startX = event.x
            if (callbacks != null) {
                callbacks!!.onStart()
            }
            binding.slideToCompleteMidText.setTranslationX(0f)
            currentState = State.STARTED
        }
    }

    private fun onMoveTouchEvent(event: MotionEvent) {
        if (currentState == State.STARTED) {
            val newX = event.x
            if (startX < newX) {
                moveSlider(newX - startX)
                performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            }
            if (reachedMidway()) {
                binding.slideToCompleteMidText.setVisibility(View.VISIBLE)
            } else {
                binding.slideToCompleteMidText.setVisibility(View.GONE)
            }
            if (reachedEnd()) {
                beginLoading()
            }
        }
    }

    private fun onReleasedTouchEvent() {
        if (currentState == State.STARTED) {
            resetSlider()
            if (callbacks != null) {
                callbacks!!.onCancel()
            }
        }
    }

    private fun moveSlider(delta: Float) {
        binding.slideToCompleteThumb.setTranslationX(delta)
        val layoutParams: ViewGroup.LayoutParams =
            binding.slideToCompleteForeground.getLayoutParams()
        layoutParams.width =
            resources.getDimensionPixelOffset(R.dimen.transfer_foreground_default) + Math.round(
                delta
            )
        binding.slideToCompleteForeground.setLayoutParams(layoutParams)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun beginLoading() {
        currentState = State.LOADING
        moveSlider(measuredWidth.toFloat())
        binding.slideToCompleteThumb.setImageResource(R.drawable.slider_thumb_empty)
        val startMargin = context.resources
            .getDimensionPixelOffset(R.dimen.slide_to_complete_start_margin)
        binding.slideToCompleteMidText.animate()
            .translationX(measuredWidth / 2f - binding.slideToCompleteMidText.getMeasuredWidth() / 2f - startMargin)
            .setDuration(300)
            .withEndAction({ binding.slideToCompleteMidText.setText(midTextRes) })
            .start()
        binding.slideToCompleteLoadingIndicator.setVisibility(View.VISIBLE)
        //        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(300);
        callbacks!!.onSuccess()
    }

    private fun resetSlider() {
        binding.slideToCompleteMidText.setVisibility(View.GONE)
        binding.slideToCompleteMidText.setText(hintTextRes)
        binding.slideToCompleteDoneText.setVisibility(View.GONE)
        val interpolator: Interpolator = DecelerateInterpolator(1.5f)
        val anim: Animation = ResizeWidthAnimation(
            binding.slideToCompleteForeground,
            resources.getDimensionPixelOffset(R.dimen.transfer_foreground_default)
        )
        anim.duration = SLIDE_BACK_DURATION.toLong()
        anim.interpolator = interpolator
        binding.slideToCompleteThumb.animate()
            .setDuration(300)
            .setInterpolator(interpolator)
            .translationX(0f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    binding.slideToCompleteForeground.startAnimation(anim)
                }

                override fun onAnimationEnd(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        currentState = State.IDLE
    }

    private fun reachedMidway(): Boolean {
        val endOfHint: Float =
            x + binding.slideToCompleteHintText.getX() + binding.slideToCompleteHintText.getMeasuredWidth()
        return endOfHint - endOfThumb() < COMPARISON_THRESHOLD
    }

    private fun reachedEnd(): Boolean {
        val endOfContainer = x + measuredWidth
        return endOfContainer - endOfThumb() < COMPARISON_THRESHOLD
    }

    private fun endOfThumb(): Float {
        return x.plus(binding.slideToCompleteThumb.getMeasuredWidth()) + binding.slideToCompleteThumb.getTranslationX()
    }

    companion object {
        private const val COMPARISON_THRESHOLD = 4f
        private const val SLIDE_BACK_DURATION = 300
    }
}
