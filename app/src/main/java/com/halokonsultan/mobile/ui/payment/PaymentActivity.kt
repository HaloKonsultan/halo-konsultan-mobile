package com.halokonsultan.mobile.ui.payment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.halokonsultan.mobile.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            val invoiceUrl = intent.getStringExtra(EXTRA_URL).toString()
            loadPage(invoiceUrl)
        }
        setupSwiper()
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
            with(binding.wvInvoice) {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        if (url != null) {
                            view?.reload()
                        }
                        return true
                    }
                }
            }
        }
    }
}