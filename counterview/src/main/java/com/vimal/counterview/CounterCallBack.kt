package com.vimal.counterview

interface CounterCallBack {
    fun onFinish()
    fun onTick(millisUntilFinished: Long)
}