package com.example.calorietracker.GetStarted

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calorietracker.MainActivity
// Pastikan package sesuai dengan struktur proyek Anda
import com.example.calorietracker.databinding.ActivityGetStartedBinding

class GetStartedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetStartedBinding
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val satuanBerat = arrayOf("kg", "pound")
        val tujuanDiet = arrayOf("Pilih tujuan Diet anda", "bulking", "cutting", "maintaining")
        val satuanKalori = arrayOf("kal", "kkal") // Array baru untuk satuan kalori

        binding.gsdatePickerTextInputEditText.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this)
            datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                selectedDate = "$dayOfMonth/$month/$year"
                binding.gsdatePickerTextInputEditText.setText(selectedDate)
            }
            datePickerDialog.show()
        }

        binding.spinnerMaksimumKalori.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, satuanKalori)

        binding.spinnerSatuanBerat1.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, satuanBerat)
        binding.spinnerWeightUnit2.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, satuanBerat)
        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            tujuanDiet
        ) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(Color.GRAY)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                return view
            }
        }
        binding.spinnerTujuanDiet.adapter = adapter

        binding.btnSubmit.setOnClickListener {
            val sharedPref =
                getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("nama", binding.edtNama.text.toString())
                putString("bb_sekarang", binding.edtBbsekarang.text.toString())
                putString("bb_target", binding.edtBbtarget.text.toString())
                putString("tanggal", binding.gsdatePickerTextInputEditText.text.toString())
                putString("maksimum_kalori", binding.edtMaksimumkalori.text.toString())
                putString("satuan_berat_1", binding.spinnerSatuanBerat1.selectedItem.toString())
                putString("satuan_berat_2", binding.spinnerWeightUnit2.selectedItem.toString())
                putString("tujuan_diet", binding.spinnerTujuanDiet.selectedItem.toString())
                putString("satuan_kalori", binding.spinnerMaksimumKalori.selectedItem.toString())
                apply()
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
