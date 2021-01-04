package com.vimal.counterview

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.concurrent.TimeUnit

class CounterView : AppCompatTextView {
    private var counter: CountDownTimer?=null
    var timer:Long=0L
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?){
        if(!isInEditMode) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CounterView)
            setTimer(typedArray.getFloat(R.styleable.CounterView_timer, 0f))
            typedArray.recycle()
        }
    }

    fun setTimer(time: Float)
    {
        timer=time.toLong()
        text=getDurationString(timer)
    }

    private fun getDurationString(time: Long):String
    {
        return String.format("%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time),
            TimeUnit.MILLISECONDS.toMinutes(time) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), // The change is in this line
            TimeUnit.MILLISECONDS.toSeconds(time) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)))
    }

    fun startCounter(counterCallBack:CounterCallBack)
    {
        counter?.cancel()
        counter=object : CountDownTimer(timer,1000L){
            override fun onFinish() {
                counterCallBack.onFinish()
            }

            override fun onTick(millisUntilFinished: Long) {
                text=getDurationString(millisUntilFinished)
                counterCallBack.onTick(millisUntilFinished)
            }
        }
        counter?.start()
    }

    fun stopCounter(){
        counter?.cancel()
    }
}