package com.udacity.components

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.udacity.R
import kotlin.properties.Delegates
import android.animation.AnimatorListenerAdapter




class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private var progress: Float = 0f

    private var buttonBackgroundColor = R.color.colorPrimary
    private var loadingButtonText: String = ""

    private var valueAnimator = ValueAnimator()

    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Completed -> {
                Log.d("LoadingButton", " ButtonState.Completed")
                loadingButtonText = context.getString(R.string.download)
                isClickable = true
                buttonBackgroundColor = R.color.colorPrimary
                valueAnimator.cancel()
                progress = 0f

            }
            ButtonState.Clicked -> {
                Log.d("LoadingButton", " ButtonState.Clicked")
                isClickable = false
                loadingButtonText = context.getString(R.string.button_loading)
                buttonBackgroundColor = R.color.colorPrimary

                setState(ButtonState.Loading)
            }
            ButtonState.Loading -> {
                Log.d("LoadingButton", " ButtonState.Loading")
                valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
                    addUpdateListener {
                        progress = animatedValue as Float
                        Log.d("LoadingButton", "Progress $progress")
                        invalidate()
                    }
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            setState(ButtonState.Completed)
                        }
                    })
                    duration = 2000
                    start()
                }
            }
        }
        invalidate()
    }

    init {
        setState(ButtonState.Completed)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 45.0f
        typeface = Typeface.create("", Typeface.BOLD)
        color = Color.WHITE
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.progressColor)
    }

    private val progressCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.YELLOW
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("LoadingButton", "Drawing")
        val centerX = measuredWidth.toFloat() / 2
        val centerY = measuredHeight.toFloat() / 2

        //Draw backgroundcolor
        canvas.drawColor(context.getColor(buttonBackgroundColor))

        if (buttonState == ButtonState.Loading) {
            val progression = progress * measuredWidth.toFloat()
            Log.d("LoadingButton", "Drawing Progress $progression")
            //draw progress
            canvas.drawRect(
                0f,
                0f,
                progression,
                measuredHeight.toFloat(),
                progressPaint
            )

            val arcDiameter = 50.0f
            val arcStart = measuredWidth.toFloat() - (arcDiameter * 2.5f)
            val arcTop = arcDiameter
            val arcEnd = measuredWidth.toFloat() - arcDiameter
            val arcBottom = measuredHeight.toFloat() - arcDiameter

            //draw circle progress
            canvas.drawArc(
                arcStart,
                arcTop,
                arcEnd,
                arcBottom,
                0f,
                progression,
                true,
                progressCirclePaint
            )
        }

        //Draw Text
        canvas.drawText(loadingButtonText, centerX, centerY, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    fun setState(state: ButtonState) {
        buttonState = state
    }
}