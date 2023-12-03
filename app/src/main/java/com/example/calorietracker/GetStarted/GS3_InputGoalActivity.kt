package com.example.calorietracker.GetStarted

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import com.example.calorietracker.R
import com.example.calorietracker.databinding.ActivityGs3InputGoalBinding

class GS3_InputGoalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGs3InputGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGs3InputGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gs3BtnNext.setOnClickListener {
            // Handle button click here
            // You can get the text from the EditTexts like this:
            val dietGoal = binding.editTextTujuanDiet.text.toString()
            val targetDate = binding.gs3datePickerTextInputEditText.text.toString()
            val dailyCalories = binding.gs3jumlahkalori.text.toString()

            // TODO: Use the above values

            // Intent to GS4_CongratsActivity
            val intent = Intent(this, GS4_CongratsActivity::class.java)
            startActivity(intent)
        }

        // If you want to show a DatePicker when the user clicks on the date EditText:
        binding.gs3datePickerTextInputEditText.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this)
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                // This gets called when the user sets a date
                // You can set this date to your EditText like this:
                binding.gs3datePickerTextInputEditText.setText("$dayOfMonth/$month/$year")
            }
            datePickerDialog.show()
        }

        // Opsi untuk dropdown
        val options = arrayOf("Bulking", "Cutting", "Maintaining")

        // Buat menu dropdown
        val popupMenu = PopupMenu(this, binding.editTextTujuanDiet)
        for (option in options) {
            popupMenu.menu.add(option)
        }

        // Tampilkan dropdown saat EditText diklik
        binding.editTextTujuanDiet.setOnClickListener {
            popupMenu.show()
        }

        // Tangani item yang dipilih pada dropdown
        popupMenu.setOnMenuItemClickListener { menuItem ->
            val selectedOption = menuItem.title.toString()
            binding.editTextTujuanDiet.setText(selectedOption)
            true
        }
    }
}