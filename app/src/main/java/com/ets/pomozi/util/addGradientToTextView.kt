package com.ets.pomozi.util

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.TextPaint
import android.widget.TextView

fun addGradientToTextView(textView: TextView, color1: String, color2: String) {
    val paint: TextPaint = textView.paint
    val width: Float = paint.measureText(textView.text.toString())
    val colors = intArrayOf(Color.parseColor(color1), Color.parseColor(color2));
    val textShader: Shader = LinearGradient(0F, 0F, width, textView.textSize, colors, null, Shader.TileMode.CLAMP)
    textView.paint.setShader(textShader)
}