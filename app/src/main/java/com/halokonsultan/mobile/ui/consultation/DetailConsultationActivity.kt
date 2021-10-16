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
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailConsultationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailConsultationBinding
    private val viewModel: ConsultationViewModel by viewModels()
    private var data: DetailConsultation? = null

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
                    data = response.data
                    populateData()
                    setStatus()
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

    private fun populateData() {
        with(binding) {
            tvFillTitle.text = data?.title
            tvFillProblem.text = data?.description
            tvFillPreferences.text = data?.preference
            tvFillLocation.text = data?.location
            tvFillCost.text = Utils.formatPrice(data?.consultation_price ?: 0)
            tvConsultantName.text = data?.consultant?.name
            tvConsultantCategory.text = data?.consultant?.position
            Picasso.get().load(data?.consultant?.photo).into(imgConsultant)

            btnChooseTime.setOnClickListener {
                val intent = Intent(this@DetailConsultationActivity, ChooseConsultationTimeActivity::class.java)
                intent.putParcelableArrayListExtra(ChooseConsultationTimeActivity.EXTRA_PREF_DATE,
                        ArrayList(data?.consultation_preference_date))
                intent.putExtra(ChooseConsultationTimeActivity.EXTRA_ID, data?.id)
                startActivity(intent)
            }

            btnChooseDocument.setOnClickListener {
                val intent = Intent(this@DetailConsultationActivity, UploadDocumentActivity::class.java)
                intent.putParcelableArrayListExtra(UploadDocumentActivity.EXTRA_DOC,
                        ArrayList(data?.consultation_document))
                intent.putExtra(UploadDocumentActivity.EXTRA_CONSULTATION_ID, data?.id)
                startActivity(intent)
            }
        }
    }

    private fun setStatus() {
        if (data?.status == "waiting" && data?.is_confirmed == 1) {
            handleWaitingPayment()
        }

        if (data?.status == "waiting" && data?.is_confirmed == 0) {
            handleWaitingConfirmation()
        }

        if (data?.status == "done" && data?.is_confirmed == 0) {
            handleRejected()
        }

        if (data?.status == "done" && data?.is_confirmed == 1) {
            handleDone()
        }

        if (data?.status == "active") {
            handleActive()
        }
    }

    private fun handleWaitingPayment() {
        binding.svMain.setPadding(0,0,0,
                resources.getDimensionPixelOffset(R.dimen.padding_bottom_detail_consultation))
        binding.cardPayment.visibility = View.VISIBLE
        if (data?.date != null) {
            binding.btnChooseTime.text = "${data?.date} ${data?.time}"
        }
    }

    private fun handleWaitingConfirmation() {
        binding.tvMessage.text = "Sedang menunggu konfirmasi dari konsultan"
        binding.tvMessage.visibility = View.VISIBLE
        binding.svMain.setPadding(0,
                resources.getDimensionPixelOffset(R.dimen.padding_top_detail_consultation),
                0,
                0)
        disableChooseTimeAndDocument()
    }

    private fun handleActive() {
        if (data?.conference_link != null) {
            binding.btnOpenConference.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.primary_blue))
            binding.btnOpenConference.setTextColor(resources.getColor(R.color.white))
            binding.btnOpenConference.setOnClickListener {
                val intent = Intent(ACTION_VIEW, Uri.parse(data?.conference_link))
                startActivity(intent)
            }
        }

        binding.btnChooseTime.text = "${data?.date} ${data?.time}"
        disableChooseTimeAndDocument()
    }

    private fun handleRejected() {
        binding.tvMessage.text = "Permintaan konsultasi Anda ditolak"
        binding.tvMessage.setBackgroundColor(resources.getColor(R.color.danger))
        binding.tvMessage.visibility = View.VISIBLE
        binding.svMain.setPadding(0,
                resources.getDimensionPixelOffset(R.dimen.padding_top_detail_consultation),
                0,
                0)
        disableChooseTimeAndDocument()
    }

    private fun handleDone() {
        binding.tvMessage.text = "Konsultasi telah berakhir"
        binding.tvMessage.setBackgroundColor(resources.getColor(R.color.green))
        binding.tvMessage.visibility = View.VISIBLE
        binding.svMain.setPadding(0,
                resources.getDimensionPixelOffset(R.dimen.padding_top_detail_consultation),
                0,
                0)
        binding.btnChooseTime.text = "${data?.date} ${data?.time}"
        disableChooseTimeAndDocument()
    }

    private fun disableChooseTimeAndDocument() {
        binding.btnChooseTime.isEnabled = false
        binding.btnChooseTime.setTextColor(resources.getColor(R.color.label))
        binding.btnChooseTime.setStrokeColorResource(R.color.label)
        binding.btnChooseDocument.isEnabled = false
        binding.btnChooseDocument.setTextColor(resources.getColor(R.color.label))
        binding.btnChooseDocument.setStrokeColorResource(R.color.label)
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