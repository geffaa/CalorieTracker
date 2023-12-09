package com.example.calorietracker.GetStarted

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calorietracker.databinding.ActivityGs2InputWeightBinding

class GS2_InputWeightActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGs2InputWeightBinding
    private var selectedSatuan1: String = ""
    private var selectedSatuan2: String = ""
    private var beratBadanTarget: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGs2InputWeightBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val name = intent.getStringExtra("name")
        val intent = Intent(this, GS3_InputGoalActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("selectedSatuan1", selectedSatuan1)
        intent.putExtra("selectedSatuan2", selectedSatuan2)
        intent.putExtra("beratBadanTarget", beratBadanTarget)
        startActivity(intent)

        // Spinner 1 dan berat
        val spinner1 = binding.gs2spinnersatuan1
        val data = listOf("Kg", "Lb")
        var selectedSatuan1 = data[0] // Default selection
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedSatuan1 = data[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected (optional)
            }
        }

        // Spinner 2 dan berat yang diinginkan
        val spinner2 = binding.gs2spinnersatuan2
        val data2 = listOf("Kg", "Lb")
        var selectedSatuan2 = data2[0] // Default selection
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, data2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedSatuan2 = data2[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where nothing is selected (optional)
            }
        }

        // Button Next
        val btnNext: Button = binding.gs2BtnNext
        btnNext.setOnClickListener {
            val intent = Intent(this, GS3_InputGoalActivity::class.java)
            intent.putExtra("selectedSatuan1", selectedSatuan1)
            intent.putExtra("selectedSatuan2", selectedSatuan2)
            startActivity(intent)
        }
    }
}
