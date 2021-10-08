package com.halokonsultan.mobile.ui.booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivityBookingBinding
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityBookingBinding
    private val viewModel: BookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.cbOffline.setOnCheckedChangeListener { _, isChecked ->
//            binding.tfChooseLocation.isEnabled = isChecked
            binding.tvLocationText.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
            binding.tfChooseLocation.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
        }

        binding.btnBooking.setOnClickListener {
            try {
                booking()
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun booking() {
        val title = binding.etTitle.text
        val desc = binding.etDescProblem.text
        val isOnlineSelected = binding.cbOnline.isChecked
        val isOfflineSelected = binding.cbOffline.isChecked
        val location = if (isOfflineSelected) binding.etChooseLocation.text else ""

        if (title.isNullOrBlank() || desc.isNullOrBlank() || (!isOnlineSelected && !isOfflineSelected)) {
            throw Exception("Anda harus mengisi field judul, deskripsi, " +
                    "dan memilih salah satu preferensi konsultasi")
        } else if (isOfflineSelected && location.isNullOrBlank()) {
            throw Exception("Ketika memilih offline, Anda harus mengisi lokasi")
        }

        viewModel.bookingConsultation(title.toString(), desc.toString(),
            isOnlineSelected, isOfflineSelected, location.toString())

        viewModel.consultation.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
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