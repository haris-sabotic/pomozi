package com.ets.pomozi.util

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.children


fun showInputDialog(context: Context, title: String, inputType: Int, lineCount: Int? = null,  onSubmit: (String) -> Unit) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
    builder.setTitle(title)

    val layout = LinearLayout(context)

    val input = EditText(context)
    input.inputType = inputType
    if (lineCount != null) {
        input.setLines(lineCount)
    }

    val lp = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    lp.setMargins(20F.toDp(context), 0, 20F.toDp(context), 0)
    input.layoutParams = lp

    layout.addView(input)

    builder.setView(layout)


    builder.setPositiveButton("OK") { _, _ ->
        val text = input.text.toString()

        onSubmit(text)
    }
    builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

    builder.show()
}

private fun Float.toDp(context: Context): Int {
    val r: Resources = context.resources

    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        r.displayMetrics
    ).toInt()
}