package com.halokonsultan.mobile.ui.chooseconsultationtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.button.MaterialButtonToggleGroup
import com.halokonsultan.mobile.databinding.ActivityChooseConsultationTimeBinding
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseConsultationTime : AppCompatActivity() {

    private lateinit var binding: ActivityChooseConsultationTimeBinding
    private lateinit var button: MaterialButtonToggleGroup
    private val viewModel: ChooseConsultationTimeViewModel by viewModels()
    private var checkedBtnData: String = ""
    private val id = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseConsultationTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button = binding.toggleButtonGroup
        button.isSingleSelection = true
        button.isSelectionRequired = true

        onCheckedButton()
        binding.btnPilihWaktuKonsultasi.setOnClickListener {
            if (checkedBtnData.isEmpty()) {
                Toast.makeText(this, "Silakan pilih salah satu jadwal", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    sendToApi()
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun onCheckedButton() {
        button.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                binding.btnDateOne.id -> {
                    checkedBtnData = binding.btnDateOne.text.toString()
                    Toast.makeText(this, checkedBtnData, Toast.LENGTH_SHORT).show()
                }
                binding.btnDateTwo.id -> {
                    checkedBtnData = binding.btnDateTwo.text.toString()
                    Toast.makeText(this, checkedBtnData, Toast.LENGTH_SHORT).show()
                }
                binding.btnDateThree.id -> {
                    checkedBtnData = binding.btnDateThree.text.toString()
                    Toast.makeText(this, checkedBtnData, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun sendToApi() {
        viewModel.getPrefDate(id, checkedBtnData)
        viewModel.date.observe(this, { data ->
            when (data) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    // save profile to sharedPref
                    Toast.makeText(
                        this, checkedBtnData,
                        Toast.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, data.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }
}