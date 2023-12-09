package com.example.calorietracker.GetStarted

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import com.example.calorietracker.R
import com.example.calorietracker.databinding.ActivityGs3InputGoalBinding

class GS3_InputGoalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGs3InputGoalBinding
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGs3InputGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gs3BtnNext.setOnClickListener {
            val dietGoal = binding.editTextTujuanDiet.text.toString().trim()
            val targetDate = selectedDate // Menggunakan variabel yang menyimpan tanggal
            val dailyCalories = binding.gs3jumlahkalori.text.toString().trim()

            if (dietGoal.isEmpty() || targetDate.isEmpty() || dailyCalories.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, GS4_CongratsActivity::class.java)
                intent.putExtra("dietGoal", dietGoal)
                intent.putExtra("targetDate", targetDate)
                intent.putExtra("dailyCalories", dailyCalories)
                startActivity(intent)
            }
        }

        // If you want to show a DatePicker when the user clicks on the date EditText:
        binding.gs3datePickerTextInputEditText.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this)
            datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                // This gets called when the user sets a date
                // You can set this date to your EditText like this:
                selectedDate = "$dayOfMonth/$month/$year"
                binding.gs3datePickerTextInputEditText.setText(selectedDate)
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
