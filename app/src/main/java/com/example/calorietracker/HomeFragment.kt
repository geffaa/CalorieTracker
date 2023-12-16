package com.example.calorietracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import com.example.calorietracker.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    // Declare ARG_PARAM1 and ARG_PARAM2
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // TODO: Handle any arguments here if necessary
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            val name = sharedPref.getString("nama", "")
            val targetKalori = sharedPref.getString("maksimum_kalori", "")
            val targetBerat = sharedPref.getString("bb_target", "")
            val tanggalTarget = sharedPref.getString("tanggal", "")
            val satuanKalori = sharedPref.getString("satuan_kalori", "")

            binding.hmTvName.text = "Hi, $name"
            binding.hmTvTargetkalori.text = "$targetKalori $satuanKalori"
            binding.hmTvTargetberat.text = "$targetBerat Kg"
            binding.hmTvTgltarget.text = tanggalTarget
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}