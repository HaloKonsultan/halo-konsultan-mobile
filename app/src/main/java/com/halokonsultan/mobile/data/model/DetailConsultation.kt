package com.halokonsultan.mobile.data.model

data class DetailConsultation(
    val id: Int,
    val title: String,
    val description: String,
    val consultant_id: Int,
    val consultation_price: Int,
    val conference_link: String,
    val date: String,
    val is_confirmed: Boolean,
    val location: String,
    val preference: String,
    val status: String,
    val consultations_document: List<ConsultationsDocument>,
    val consultations_pref_date: List<ConsultationsPrefDate>,
)