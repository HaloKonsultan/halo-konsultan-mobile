package com.halokonsultan.mobile.ui.consultation

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.res.ColorStateList
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.ActivityWithCustomToolbar
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.data.model.Transaction
import com.halokonsultan.mobile.databinding.ActivityDetailConsultationBinding
import com.halokonsultan.mobile.ui.chooseconsultationtime.ChooseConsultationTimeActivity
import com.halokonsultan.mobile.ui.payment.PaymentActivity
import com.halokonsultan.mobile.ui.reviewconsultation.ReviewConsultationActivity
import com.halokonsultan.mobile.ui.uploaddocument.UploadDocumentActivity
import com.halokonsultan.mobile.utils.PERSON_PLACEHOLDER
import com.halokonsultan.mobile.utils.Utils
import com.halokonsultan.mobile.utils.Utils.addHttpIfNeeded
import com.halokonsultan.mobile.utils.Utils.addRootDomainIfNeeded
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailConsultationActivity : ActivityWithCustomToolbar<ActivityDetailConsultationBinding>() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityDetailConsultationBinding
        = ActivityDetailConsultationBinding::inflate
    private val viewModel: ConsultationViewModel by viewModels()
    private var data: DetailConsultation? = null
    private val consultationObserver by lazy { setupConsultationObserver() }
    private val paymentObserver by lazy { setupPaymentObserver() }

    override fun setup() {
        setupSupportActionBar()
        setTitle("Detail Konsultasi")

        val bundle = intent.extras
        if (bundle != null) {
            val id = intent.getIntExtra(EXTRA_ID, 0)
            viewModel.getDetailConsultation(id).observe(this, consultationObserver)
        }

        binding.btnRefresh.setOnClickListener {
            finish()
            startActivity(this.intent)
        }
    }

    private fun setupPaymentObserver() = setObserverWithBasicResponse<Transaction>(
        onSuccess = { response ->
            val paymentData = response.data?.data
            val intent = Intent(baseContext, PaymentActivity::class.java)
            intent.putExtra(PaymentActivity.EXTRA_URL, paymentData?.invoice_url)
            intent.putExtra(PaymentActivity.EXTRA_ID, paymentData?.id)
            startActivity(intent)
        }
    )

    private fun setupConsultationObserver() = setObserverWithBasicResponse<DetailConsultation>(
        onSuccess = { response ->
            binding.progressBar.gone()
            binding.layNoInet.gone()
            data = response.data?.data
            populateData()
            setStatus()
        },
        onError = { response ->
            binding.progressBar.gone()
            binding.layNoInet.gone()
            Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
        },
        onLoading = { binding.progressBar.visible() }
    )

    private fun populateData() {
        binding.apply {
            tvFillTitle.text = data?.title
            tvFillProblem.text = data?.description
            tvFillPreferences.text = data?.preference
            tvFillLocation.text = data?.location
            tvFillCost.text = Utils.formatPrice(data?.consultation_price ?: 0)
            tvConsultantName.text = data?.consultant?.name
            tvConsultantCategory.text = data?.consultant?.position
            Picasso.get().load(addRootDomainIfNeeded(data?.consultant?.photo ?: PERSON_PLACEHOLDER)).into(imgConsultant)

            btnChooseTime.setOnClickListener {
                val intent = Intent(this@DetailConsultationActivity, ChooseConsultationTimeActivity::class.java)
                intent.putParcelableArrayListExtra(ChooseConsultationTimeActivity.EXTRA_PREF_DATE,
                        ArrayList(data?.consultation_preference_date?.toMutableList() ?: mutableListOf()))
                intent.putExtra(ChooseConsultationTimeActivity.EXTRA_ID, data?.id)
                startActivity(intent)
            }

            btnChooseDocument.setOnClickListener {
                val intent = Intent(this@DetailConsultationActivity, UploadDocumentActivity::class.java)
                intent.putParcelableArrayListExtra(UploadDocumentActivity.EXTRA_DOC,
                        ArrayList(data?.consultation_document?.toMutableList() ?: mutableListOf()))
                intent.putExtra(UploadDocumentActivity.EXTRA_CONSULTATION_ID, data?.id)
                startActivity(intent)
            }

            btnPay.setOnClickListener {
                if (data != null && data?.id != null && data?.consultation_price != null) {
                    viewModel.pay(data?.id!!, data?.consultation_price!!)
                        .observe(this@DetailConsultationActivity, paymentObserver)
                }
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
        binding.apply {
            svMain.setPadding(0,0,0,
                resources.getDimensionPixelOffset(R.dimen.padding_bottom_detail_consultation))
            cardPayment.visibility = View.VISIBLE
            cardMessage.isVisible = false
            if (data?.date != null) {
                btnChooseTime.text = getString(R.string.formatter_tanggal_jam, data?.date, data?.time)
                btnChooseTime.icon = AppCompatResources
                    .getDrawable(this@DetailConsultationActivity, R.drawable.ic_check_circle)
            }
            if (data?.consultation_document?.all { it.file != null } == true) {
                btnChooseDocument.icon = AppCompatResources
                    .getDrawable(this@DetailConsultationActivity, R.drawable.ic_check_circle)
            }
        }
    }

    private fun handleWaitingConfirmation() {
        binding.apply {
            tvMessage.text = getString(R.string.sedang_menunggu_konfirmasi_dari_konsultan)
            tvMessage.visibility = View.VISIBLE
            svMain.setPadding(0,
                resources.getDimensionPixelOffset(R.dimen.padding_top_detail_consultation),
                0,
                0)
            cardMessage.isVisible = false
        }
        disableChooseTimeAndDocument()
    }

    private fun handleActive() {
        binding.apply {
            if (data?.conference_link != null) {
                btnOpenConference.backgroundTintList = ColorStateList.valueOf(getColorResource(R.color.primary_blue))
                btnOpenConference.setTextColor(getColorResource(R.color.white))
                btnOpenConference.setOnClickListener {
                    val intent = Intent(ACTION_VIEW, Uri.parse(addHttpIfNeeded(data?.conference_link)))
                    startActivity(intent)
                }
            }

            cardMessage.isVisible = false

            btnChooseTime.text = getString(R.string.formatter_tanggal_jam, data?.date, data?.time)
        }
        disableChooseTimeAndDocument()
    }

    private fun handleRejected() {
        binding.apply {
            tvMessage.text = getString(R.string.permintaan_konsultasi_anda_ditolak)
            tvMessage.setBackgroundColor(getColorResource(R.color.danger))
            tvMessage.visibility = View.VISIBLE
            svMain.setPadding(0,
                resources.getDimensionPixelOffset(R.dimen.padding_top_detail_consultation),
                0,
                0)
            cardMessage.setStrokeColor(ColorStateList.valueOf(getColorResource(R.color.danger)))
            tvMessageTitle.text = getString(R.string.alasan_penolakan)
            tvConsultantMessage.text = data?.message
            btnReview.isVisible = false
        }
        disableChooseTimeAndDocument()
    }

    private fun handleDone() {
        binding.apply {
            tvMessage.text = getString(R.string.konsultasi_telah_berakhir)
            tvMessage.setBackgroundColor(getColorResource(R.color.green))
            tvMessage.visibility = View.VISIBLE
            svMain.setPadding(0,
                resources.getDimensionPixelOffset(R.dimen.padding_top_detail_consultation),
                0,
                0)
            btnChooseTime.text = getString(R.string.formatter_tanggal_jam, data?.date, data?.time)
            tvConsultantMessage.text = data?.message
            disableChooseTimeAndDocument()
            btnReview.setOnClickListener {
                intent = Intent(this@DetailConsultationActivity, ReviewConsultationActivity::class.java)
                intent.putExtra(ReviewConsultationActivity.EXTRA_ID, data?.id)
                startActivity(intent)
            }
        }
    }

    private fun disableChooseTimeAndDocument() {
        binding.btnChooseTime.apply {
            isEnabled = false
            setTextColor(getColorResource(R.color.label))
            setStrokeColorResource(R.color.label)
        }
        binding.btnChooseDocument.apply {
            isEnabled = false
            setTextColor(getColorResource(R.color.label))
            setStrokeColorResource(R.color.label)
        }
    }
}