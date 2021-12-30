package com.halokonsultan.mobile.ui.payment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import com.halokonsultan.mobile.BuildConfig
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.BaseActivity
import com.halokonsultan.mobile.data.model.Transaction
import com.halokonsultan.mobile.databinding.ActivityPaymentBinding
import com.halokonsultan.mobile.ui.confirmation.ConfirmationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentActivity : BaseActivity<ActivityPaymentBinding>() {

    companion object {
        const val EXTRA_URL = "extra_url"
        const val EXTRA_ID = "extra_id"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityPaymentBinding
        = ActivityPaymentBinding::inflate
    private val viewModel: PaymentViewModel by viewModels()
    private var id = 0

    override fun setup() {
        supportActionBar?.title = "Pembayaran"
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
        val bundle = intent.extras
        if (bundle != null) {
            val invoiceUrl = intent.getStringExtra(EXTRA_URL).toString()
            id = intent.getIntExtra(EXTRA_ID, 0)
            loadPage(invoiceUrl)
        }
        setupSwiper()
    }

    private fun setupTransactionObserver() = setObserverWithBasicResponse<Transaction>(
        onSuccess = { response ->
            if (response.data?.data?.status_invoice == "PAID") {
                intent = Intent(this, ConfirmationActivity::class.java)
                intent.putExtra(ConfirmationActivity.EXTRA_TITLE, "Pembayaran Berhasil!")
                intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "Konsultasi Aktif, Anda bisa melihatnya di list konsultasi")
                startActivity(intent)
            } else {
                loadPage(response.data?.data?.invoice_url ?: "")
            }
        }
    )

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadPage(invoiceUrl: String) {
        with(binding.wvInvoice) {
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            settings.javaScriptCanOpenWindowsAutomatically = true
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
            viewModel.getDetailTransaction(id).observe(this, setupTransactionObserver())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miRefresh -> {
                binding.wvInvoice.reload()
                viewModel.getDetailTransaction(id).observe(this, setupTransactionObserver())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.refresh_menu, menu)
        return true
    }
}