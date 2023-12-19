package com.example.calorietracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.text.format.DateUtils
import android.widget.ImageButton
import com.example.calorietracker.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    // Declare ARG_PARAM1 and ARG_PARAM2
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"

    private lateinit var currentDate: Date
    val dateFormat = SimpleDateFormat("EEEE, dd-MM-yyyy", Locale("in"))
    private var targetedCalByDay: Int = 0

    private var dateTrigger: Int = 0

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

        currentDate = Date() // Set tanggal awal
        val backButton = view.findViewById<ImageButton>(R.id.imageButtonBack)
        val nextButton = view.findViewById<ImageButton>(R.id.imageButtonNext)

        backButton.setOnClickListener { onBackButtonClick(it) }
        nextButton.setOnClickListener { onNextButtonClick(it) }

        val sharedPref = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        if (sharedPref != null) {
            targetedCalByDay = sharedPref.getInt("targetedCalByDay", 0)
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
        updateDateOnTextView(currentDate)
    }

    // Fungsi untuk menangani klik tombol back
    fun onBackButtonClick(view: View) {
        dateTrigger -= 1
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, dateTrigger)
        val a = calendar.time
        binding.txtDateNow.text = dateFormat.format(a)
        getAllMenus()
        getRemainingCal(targetedCalByDay)
    }

    // Fungsi untuk menangani klik tombol next
    fun onNextButtonClick(view: View) {
        dateTrigger += 1
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, dateTrigger)
        val a = calendar.time
        binding.txtDateNow.text = dateFormat.format(a)
        getAllMenus()
        getRemainingCal(targetedCalByDay)
    }

    // Fungsi untuk mengurangkan tanggal
    private fun decrementDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        return calendar.time
    }

    // Fungsi untuk menambahkan tanggal
    private fun incrementDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        return calendar.time
    }

    // Fungsi untuk memperbarui TextView dengan tanggal yang baru
    private fun updateDateOnTextView(date: Date) {
        binding.txtDateNow.text = dateFormat.format(date)
    }

    // Fungsi untuk mendapatkan semua menu sesuai tanggal
    private fun getAllMenus() {
        // Implementasikan logika untuk mendapatkan semua menu sesuai tanggal
        // Anda dapat menggunakan DateUtils.getFormattedDate() untuk mendapatkan format tanggal yang diinginkan
    }

    // Fungsi untuk mendapatkan sisa kalori
    private fun getRemainingCal(dayTargetedCal: Int) {
        // Implementasikan logika untuk mendapatkan sisa kalori
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
