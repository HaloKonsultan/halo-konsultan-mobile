package com.halokonsultan.mobile.ui.payment

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.halokonsultan.mobile.databinding.ActivityPaymentBinding
import com.halokonsultan.mobile.ui.confirmation.ConfirmationActivity
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "extra_url"
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            val invoiceUrl = intent.getStringExtra(EXTRA_URL).toString()
            id = intent.getIntExtra(EXTRA_ID, 0)
            loadPage(invoiceUrl)
        }
        setupSwiper()
        observeTransaction()
    }

    private fun observeTransaction() {
        viewModel.transaction.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    if (response.data?.status_invoice == "PAID") {
                        intent = Intent(this, ConfirmationActivity::class.java)
                        intent.putExtra(ConfirmationActivity.EXTRA_TITLE, "Pembayaran Berhasil!")
                        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Konsultasi Aktif, Anda bisa melihatnya di list konsultasi")
                        startActivity(intent)
                    } else {
                        loadPage(response.data?.invoice_url ?: "")
                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadPage(invoiceUrl: String) {
        with(binding.wvInvoice) {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url != null) {
                        view?.loadUrl(url)
                    }
                    return true
                }
            }
            loadUrl(invoiceUrl)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupSwiper() {
        binding.swiperPayment.setOnRefreshListener {
            binding.wvInvoice.reload()
            viewModel.getDetailTransaction(id)
        }
    }
}