package com.halokonsultan.mobile.data.model

data class Transaction(
        val id: Int,
        val consultation_id: String,
        val external_id: String,
        val amount: String,
        val expiry_date: String,
        val invoice_url: String,
        val status_invoice: String
)