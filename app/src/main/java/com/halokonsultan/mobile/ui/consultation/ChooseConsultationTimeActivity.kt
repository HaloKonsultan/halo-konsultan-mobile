package com.halokonsultan.mobile.ui.consultation

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.ActivityWithBackButton
import com.halokonsultan.mobile.data.model.ConsultationsPrefDate
import com.halokonsultan.mobile.data.model.DetailConsultation
import com.halokonsultan.mobile.databinding.ActivityChooseConsultationTimeBinding
import com.halokonsultan.mobile.utils.Utils.strDate
import com.halokonsultan.mobile.utils.Utils.toString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseConsultationTimeActivity : ActivityWithBackButton<ActivityChooseConsultationTimeBinding>() {

    companion object {
        const val EXTRA_PREF_DATE = "extra_pref_date"
        const val EXTRA_ID = "extra_id"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityChooseConsultationTimeBinding
        = ActivityChooseConsultationTimeBinding::inflate

    private val viewModel: ConsultationViewModel by viewModels()
    private var checkedBtnData: String = ""
    private var id = 0

    override fun setup() {
        setupSupportActionBar()
        populateDataFromBundle()
        setupToggleButtonGroup()
        setupChooseButton()
    }

    private fun setupChooseButton() {
        binding.btnPilihWaktuKonsultasi.setOnClickListener {
            if (checkedBtnData.isEmpty()) {
                showToast("Silakan pilih salah satu jadwal")
            } else {
                val formatted = strDate(checkedBtnData, "EEE, dd MMM yyyy HH:mm")?.
                    toString("dd-MM-yyyy HH:mm")
                val dateArr = formatted?.split(" ")
                viewModel.setPrefDate(id, dateArr?.get(0) ?: "01-01-1970", dateArr?.get(1) ?: "00:00")
                    .observe(this, setupDateObserver())
            }
        }
    }

    private fun setupDateObserver() = setObserverWithBasicResponse<DetailConsultation>(
        onSuccess = {
            binding.progressBar.gone()
            intent = Intent(this, DetailConsultationActivity::class.java)
            intent.putExtra(DetailConsultationActivity.EXTRA_ID, id)
            startActivity(intent)
            finish()
        },

        onError = {
            binding.progressBar.gone()
        },

        onLoading = {
            binding.progressBar.visible()
        }
    )

    private fun setupToggleButtonGroup() {
        binding.toggleButtonGroup.apply {
            isSingleSelection = true
            isSelectionRequired = true
            addOnButtonCheckedListener { _, checkedId, _ ->
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
    }

    private fun populateDataFromBundle() {
        val bundle = intent.extras
        if (bundle != null) {
            id = intent.getIntExtra(EXTRA_ID, 0)
            val prefDates = intent.getParcelableArrayListExtra<ConsultationsPrefDate>(
                EXTRA_PREF_DATE
            )
            if (prefDates != null) {
                when(prefDates.size) {
                    1 -> { binding.btnDateOne.text = getString(R.string.formatter_tanggal_jam, formatDate(prefDates[0].date), prefDates[0].time) }
                    2 -> { enableSecondButton(prefDates[1].date, prefDates[1].time) }
                    3 -> {
                        enableSecondButton(prefDates[1].date, prefDates[1].time)
                        enableThirdButton(prefDates[2].date, prefDates[2].time)
                    }
                }
                if (prefDates.size == 1) {
                    viewModel.setPrefDate(id,
                        prefDates[0].date, prefDates[0].time)
                        .observe(this, setupDateObserver())
                }
            }
        }
    }

    private fun enableSecondButton(date: String, time: String) {
        with(binding.btnDateTwo) {
            visible()
            text = getString(R.string.formatter_tanggal_jam, formatDate(date), time)
        }
    }

    private fun enableThirdButton(date: String, time: String) {
        with(binding.btnDateThree) {
            visible()
            text = getString(R.string.formatter_tanggal_jam, formatDate(date), time)
        }
    }

    private fun formatDate(date: String) = strDate(date)?.toString("EEE, dd MMM yyyy")
}