package com.example.calorietracker.HomePage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calorietracker.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val beratyangdiinginkan = intent.getStringExtra("beratyangdiinginkan")
        val satuanberatyangdiinginkan = intent.getStringExtra("satuanberatyangdiinginkan")
        val jumlahkalori = intent.getStringExtra("jumlahkalori")
        val name = intent.getStringExtra("name")

        val resmakan = intent.getStringExtra("resmakan")
        val reswor = intent.getStringExtra("reswor")

        var sisakalori = intent.getStringExtra("sisakalori")

        val jumlahkalorimakan = intent.getStringExtra("jumlahkalorimakan")?.toIntOrNull() ?: 0
        val jumlahkaloriworkout = intent.getStringExtra("jumlahkaloriworkout")?.toIntOrNull() ?: 0

        if (sisakalori == null) {
            sisakalori = jumlahkalori
        }

        binding.hmTvTargetkalori.text = jumlahkalori
        binding.hmTvTargetberat.text = "$beratyangdiinginkan $satuanberatyangdiinginkan"
        binding.hmTvName.text = "Hi!, $name"
        binding.hmTvSisakalori.text = sisakalori
        binding.hmTvMakanan.text = "${jumlahkalorimakan} Kcal"
        binding.hmTvWor.text = "${jumlahkaloriworkout} kcal"
    }
}