package com.example.calorietracker.GetStarted

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import com.example.calorietracker.R
import com.example.calorietracker.databinding.ActivityGetStartedBinding
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class GetStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedBinding

    private lateinit var edtTanggal: TextInputEditText

    private val satuanBeratBadan = arrayOf(
        "Kg",
        "Pound"
    )

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_CURRENT_WEIGHT = "cWeight"
        const val EXTRA_CURRENT_WEIGHT_SATUAN = "cWeightSatuan"
        const val EXTRA_TARGET_WEIGHT = "tWeight"
        const val EXTRA_TARGET_WEIGHT_SATUAN = "tWeightSatuan"
        const val EXTRA_CALORIES = "calories"
        const val EXTRA_GOALS = "goals"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)
        setContentView(binding.root)

        with(binding) {
            val adapterSatuanBeratBadan = ArrayAdapter(
                this@GetStartedActivity,
                android.R.layout.simple_spinner_item,
                satuanBeratBadan
            )

            adapterSatuanBeratBadan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinnerSatuanBerat1.adapter = adapterSatuanBeratBadan

            // Tambahkan callback untuk menangani pemilihan item pada Spinner
            spinnerSatuanBerat1.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    // Handle item selection, contohnya:
                    val selectedSatuanBerat = satuanBeratBadan[position]
                    Toast.makeText(this@GetStartedActivity, "Selected: $selectedSatuanBerat", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {
                    // Do nothing here
                }
            })
        }

        edtTanggal = findViewById(R.id.edt_tanggal)
        val iconCalendar: ImageView = findViewById(R.id.iconCalendar)

        edtTanggal.setOnClickListener{
            showDatePickerDialog()
        }

        iconCalendar.setOnClickListener{
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day =  calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
                edtTanggal.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}