package com.example.calorietracker.GetStarted

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calorietracker.R
import com.example.calorietracker.databinding.ActivityGs1InputNameBinding

class GS1_InputNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGs1InputNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGs1InputNameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val intent = Intent(this, GS2_InputWeightActivity::class.java)

        with(binding){
            binding.gs1BtnNext.setOnClickListener {
                val name = binding.gs1EdtName.text.toString()
                if (name == ""){
                    Toast.makeText(applicationContext, "Nama Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
                }else{
                    intent.putExtra("name", name)
                    startActivity(intent)
                }
            }
        }

    }
}