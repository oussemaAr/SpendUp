package app.elite.spendup.utils

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import app.elite.spendup.R
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

fun TextInputEditText.transformIntoDatePicker(
    context: Context,
    format: String,
    maxDate: Date? = null
) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.UK)
            setText(sdf.format(myCalendar.time))
        }

    setOnClickListener {
        DatePickerDialog(
            context,
            datePickerOnDataSetListener,
            myCalendar
                .get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            show()
        }
    }
}

fun parseDouble(value: String?): Double {
    return if (value == null || value.isEmpty()) Double.NaN else value.toDouble()
}

fun EditText.stringText(): String = this.text.toString()

fun ImageView.thumbnail(thumbnail: String) {
    when (thumbnail) {
        "ic_food" -> {
            this.setImageResource(R.drawable.ic_food)
        }
        "ic_transport" -> {
            this.setImageResource(R.drawable.ic_transport)
        }
        "ic_utilities" -> {
            this.setImageResource(R.drawable.ic_utilities)
        }
        "ic_insurance" -> {
            this.setImageResource(R.drawable.ic_insurance)
        }
        "ic_medical" -> {
            this.setImageResource(R.drawable.ic_medical)
        }
        "ic_savings" -> {
            this.setImageResource(R.drawable.ic_savings)
        }
        "ic_personal_spending" -> {
            this.setImageResource(R.drawable.ic_personal_spending)
        }
        "ic_entertainment" -> {
            this.setImageResource(R.drawable.ic_entertainment)
        }
        else -> {
            this.setImageResource(R.drawable.ic_others)
        }
    }
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun View.hideView() {
    this.visibility = View.GONE
}

