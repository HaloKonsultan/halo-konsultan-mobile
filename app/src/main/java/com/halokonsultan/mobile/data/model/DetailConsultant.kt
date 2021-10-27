package com.halokonsultan.mobile.data.model

data class DetailConsultant(
        val id: Int,
        val name: String,
        val description: String,
        val gender: String,
        val email: String,
        val consultation_price: Int,
        val chat_price: Int,
        val firebase_id: String,
        val likes_total: Int,
        val location: String,
        val photo: String,
        val position: String,
        val consultant_documentation: List<ConsultantDoc>,
        val consultant_education: List<ConsultantEducation>,
        val consultant_experience: List<ConsultantExperience>,
        val consultant_skill: List<ConsultantSkill>,
        val consultant_virtual_accounts: List<ConsultantVirtualAccount>,
)