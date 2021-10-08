package com.halokonsultan.mobile.ui.consultation

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.res.ColorStateList
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.databinding.ActivityDetailConsultationBinding
import com.halokonsultan.mobile.ui.chooseconsultationtime.ChooseConsultationTimeActivity
import com.halokonsultan.mobile.ui.uploaddocument.UploadDocumentActivity
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailConsultationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailConsultationBinding
    private val viewModel: ConsultationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailConsultationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.title_text_view)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        Utils.setTitleTextView(this, "Detail Konsultasi")

        val bundle = intent.extras
        if (bundle != null) {
            val id = intent.getIntExtra(EXTRA_ID, 0)
            viewModel.getDetailConsultation(id)
        }

        viewModel.consultation.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    populateData(response.data)
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

        // dummy
        binding.btnChooseTime.setOnClickListener {
            val intent = Intent(this@DetailConsultationActivity, ChooseConsultationTimeActivity::class.java)
            startActivity(intent)
        }

        binding.btnChooseDocument.setOnClickListener {
            val intent = Intent(this@DetailConsultationActivity, UploadDocumentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun populateData(data: DetailConsultation?) {
        with(binding) {
            tvFillProblem.text = data?.description
            tvFillPreferences.text = data?.preference
            tvFillLocation.text = data?.location
            tvFillCost.text = Utils.formatPrice(data?.consultation_price ?: 0)

            if (data?.conference_link != null) {
                btnOpenConference.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.primary_blue))
                btnOpenConference.setTextColor(resources.getColor(R.color.white))
                btnOpenConference.setOnClickListener {
                    val intent = Intent(ACTION_VIEW, Uri.parse(data.conference_link))
                    startActivity(intent)
                }
            }

            btnChooseTime.setOnClickListener {
                val intent = Intent(this@DetailConsultationActivity, ChooseConsultationTimeActivity::class.java)
                intent.putParcelableArrayListExtra("pref_date", ArrayList(data?.consultations_pref_date))
                startActivity(intent)
            }

            btnChooseDocument.setOnClickListener {
                val intent = Intent(this@DetailConsultationActivity, UploadDocumentActivity::class.java)
                intent.putParcelableArrayListExtra("doc", ArrayList(data?.consultations_document))
                startActivity(intent)
            }
        }
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