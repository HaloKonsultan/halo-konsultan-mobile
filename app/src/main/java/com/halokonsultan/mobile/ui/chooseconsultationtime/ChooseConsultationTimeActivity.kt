package com.halokonsultan.mobile.ui.chooseconsultationtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.button.MaterialButtonToggleGroup
import com.halokonsultan.mobile.data.model.ConsultationsPrefDate
import com.halokonsultan.mobile.databinding.ActivityChooseConsultationTimeBinding
import com.halokonsultan.mobile.ui.consultation.DetailConsultationActivity
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseConsultationTimeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PREF_DATE = "extra_pref_date"
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityChooseConsultationTimeBinding
    private lateinit var button: MaterialButtonToggleGroup
    private val viewModel: ChooseConsultationTimeViewModel by viewModels()
    private var checkedBtnData: String = ""
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseConsultationTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            id = intent.getIntExtra(EXTRA_ID, 0)
            val prefDates = intent.getParcelableArrayListExtra<ConsultationsPrefDate>(EXTRA_PREF_DATE)
            if (prefDates != null) {
                binding.btnDateOne.text = "${prefDates[0].date} ${prefDates[0].time}"
                binding.btnDateTwo.text = "${prefDates[1].date} ${prefDates[1].time}"
                binding.btnDateThree.text = "${prefDates[2].date} ${prefDates[2].time}"
            }
        }

        button = binding.toggleButtonGroup
        button.isSingleSelection = true
        button.isSelectionRequired = true

        onCheckedButton()
        binding.btnPilihWaktuKonsultasi.setOnClickListener {
            if (checkedBtnData.isEmpty()) {
                Toast.makeText(this, "Silakan pilih salah satu jadwal", Toast.LENGTH_SHORT).show()
            } else {
                sendToApi()
            }
        }
    }

    private fun onCheckedButton() {
        button.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                binding.btnDateOne.id -> {
                    checkedBtnData = binding.btnDateOne.text.toString()
                }
                binding.btnDateTwo.id -> {
                    checkedBtnData = binding.btnDateTwo.text.toString()
                }
                binding.btnDateThree.id -> {
                    checkedBtnData = binding.btnDateThree.text.toString()
                }
            }
        }
    }

    private fun sendToApi() {
        val dateArr = checkedBtnData.split(" ")
        viewModel.getPrefDate(id, dateArr[0], dateArr[1])
        viewModel.date.observe(this, { data ->
            when (data) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    intent = Intent(this, DetailConsultationActivity::class.java)
                    intent.putExtra(DetailConsultationActivity.EXTRA_ID, id)
                    startActivity(intent)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}