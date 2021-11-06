package com.halokonsultan.mobile.data.model

data class DetailConsultation(
        val id: Int,
        val title: String,
        val description: String,
        val consultant_id: Int,
        val consultation_price: Int?,
        val conference_link: String?,
        val date: String?,
        val time: String?,
        val is_confirmed: Int,
        val location: String,
        val preference: String,
        val status: String,
        val message: String?,
        val consultant: Consultant?,
        val consultation_document: List<ConsultationsDocument>,
        val consultation_preference_date: List<ConsultationsPrefDate>,
)