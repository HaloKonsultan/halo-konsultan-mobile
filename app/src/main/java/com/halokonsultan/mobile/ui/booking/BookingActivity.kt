package com.halokonsultan.mobile.ui.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.halokonsultan.mobile.databinding.ActivityBookingBinding
import com.halokonsultan.mobile.ui.confirmation.ConfirmationActivity
import com.halokonsultan.mobile.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_CATEGORY = "extra_category"
    }

    private lateinit var binding: ActivityBookingBinding
    private val viewModel: BookingViewModel by viewModels()
    private var consultantId: Int? = null
    private var consultantName: String? = null
    private var consultantPhoto: String? = null
    private var consultantCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            consultantId = intent.getIntExtra(EXTRA_ID, 0)
            consultantName = intent.getStringExtra(EXTRA_NAME)
            consultantPhoto = intent.getStringExtra(EXTRA_PHOTO)
            consultantCategory = intent.getStringExtra(EXTRA_CATEGORY)
        }

        binding.tvConsultantName.text = consultantName
        binding.tvConsultantCategory.text = consultantCategory
        Picasso.get().load(consultantPhoto).into(binding.imgConsultant)

        binding.cbOffline.setOnCheckedChangeListener { _, isChecked ->
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
        val location = if (isOfflineSelected) binding.etChooseLocation.text else "online"

        if (title.isNullOrBlank() || desc.isNullOrBlank() || (!isOnlineSelected && !isOfflineSelected)) {
            throw Exception("Anda harus mengisi field judul, deskripsi, " +
                    "dan memilih salah satu preferensi konsultasi")
        } else if (isOfflineSelected && location.isNullOrBlank()) {
            throw Exception("Ketika memilih offline, Anda harus mengisi lokasi")
        }

        viewModel.bookingConsultation(title.toString(),
                consultantId ?: 0,
                viewModel.getUserId(),
                desc.toString(),
                isOnlineSelected,
                isOfflineSelected,
                location.toString())

        viewModel.consultation.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    intent = Intent(this, ConfirmationActivity::class.java)
                    intent.putExtra(ConfirmationActivity.EXTRA_TITLE, "Booking berhasil!")
                    intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Menunggu konfirmasi konsultan")
                    startActivity(intent)
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