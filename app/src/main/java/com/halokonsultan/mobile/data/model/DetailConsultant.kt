package com.halokonsultan.mobile.data.model

data class DetailConsultant(
        val id: Int,
        val name: String,
        val description: String,
        val chat_price: Int,
        val likes_total: Int,
        val location: String,
        val photo: String,
        val category: String,
        val consultant_doc: List<ConsultantDoc>,
        val consultant_educations: List<ConsultantEducation>,
        val consultant_experience: List<ConsultantExperience>,
        val consultant_skills: List<ConsultantSkill>
)