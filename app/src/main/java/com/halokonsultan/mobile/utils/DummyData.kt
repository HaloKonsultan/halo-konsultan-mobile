package com.halokonsultan.mobile.utils

import com.halokonsultan.mobile.data.model.*
import kotlin.collections.ArrayList


object DummyData {
    fun getCategoryList(): List<Category> {
        val list: MutableList<Category> = ArrayList()
        list.add(Category(1,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan",
                "Keuangan"))

        list.add(Category(2,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan",
                "Keuangan"))

        list.add(Category(3,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan",
                "Keuangan"))

        list.add(Category(4,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan",
                "Keuangan"))

        list.add(Category(5,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan",
                "Keuangan"))

        list.add(Category(6,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan",
                "Keuangan"))

        return list
    }

    fun getConsultantList(): List<Consultant> {
        val list: MutableList<Consultant> = ArrayList()
        list.add(Consultant("Konsultan Hukum Perdata",
                1,
                100,
                "Sidoarjo",
        "nama lengkap",
        "https://res.cloudinary.com/anongtf/image/upload/v1632140731/nuupzj8zr4ws0j7hiykq.png"))

        list.add(Consultant("Konsultan Hukum Perdata",
                2,
                100,
                "Sidoarjo",
                "nama lengkap",
                "https://res.cloudinary.com/anongtf/image/upload/v1632140731/nuupzj8zr4ws0j7hiykq.png"))

        list.add(Consultant("Konsultan Hukum Perdata",
                2,
                100,
                "Sidoarjo",
                "nama lengkap",
                "https://res.cloudinary.com/anongtf/image/upload/v1632140731/nuupzj8zr4ws0j7hiykq.png"))

        return list
    }

    fun getConsultationList(): List<Consultation> {
        val list: MutableList<Consultation> = ArrayList()
        list.add(
            Consultation(
                1,
                1,
                "Konsultanku",
                "Konsultasi hati",
                "aktif",
                true,
                1632398841000
            )
        )
        list.add(
            Consultation(
                1,
                1,
                "Konsultanku",
                "Konsultasi hati",
                "aktif",
                true,
                1632398841000
            )
        )
        list.add(
            Consultation(
                1,
                1,
                "Konsultanku",
                "Konsultasi hati",
                "aktif",
                true,
                1632398841000
            )
        )
        return list
    }

    fun getWaitingConsultationList(): List<Consultation> {
        val list: MutableList<Consultation> = ArrayList()
        list.add(
            Consultation(
                1,
                1,
                "Konsultanku",
                "Konsultasi hati",
                "menunggu",
                false,
                1632398841000
            )
        )
        list.add(
            Consultation(
                1,
                1,
                "Konsultanku",
                "Konsultasi hati",
                "menunggu",
                true,
                1632398841000
            )
        )
        list.add(
            Consultation(
                1,
                1,
                "Konsultanku",
                "Konsultasi hati",
                "menunggu",
                false,
                1632398841000
            )
        )
        return list
    }

    fun getDoneConsultationList(): List<Consultation> {
        val list: MutableList<Consultation> = ArrayList()
        list.add(
            Consultation(
                1,
                1,
                "Konsultanku",
                "Konsultasi hati",
                "selesai",
                true,
                1632398841000
            )
        )
        list.add(
            Consultation(
                1,
                1,
                "Konsultanku",
                "Konsultasi hati",
                "selesai",
                false,
                1632398841000
            )
        )
        list.add(
            Consultation(
                1,
                1,
                "Konsultanku",
                "Konsultasi hati",
                "selesai",
                false,
                1632398841000
            )
        )
        return list
    }

    fun getDocumentList(): List<ConsultantDoc> {
        val list: MutableList<ConsultantDoc> = ArrayList()
        list.add(ConsultantDoc(
                1,
                1,
                "https://res.cloudinary.com/anongtf/image/upload/v1632654470/rgv0nn0dbm6ekvwpxaw7.png"
        ))

        list.add(ConsultantDoc(
                2,
                1,
                "https://res.cloudinary.com/anongtf/image/upload/v1632654507/pxjxngj3brpbylzitjkn.png"
        ))
        return list
    }

    fun getEducationList(): List<ConsultantEducation> {
        val list: MutableList<ConsultantEducation> = ArrayList()
        list.add(ConsultantEducation(
                1,
                1,
                "Universitas Atas Bawah",
                "Sarjana Filsahat Hukum Percintaan",
                "2016",
                "2020"
        ))
        list.add(ConsultantEducation(
                2,
                1,
                "University of Up and Down",
                "Magister of Love Law Philosophical",
                "2020",
                "2021"
        ))
        return list
    }

    fun getExperienceList(): List<ConsultantExperience> {
        val list: MutableList<ConsultantExperience> = ArrayList()
        list.add(ConsultantExperience(
                1,
                1,
                "Konsultan Percintaan",
                "2020",
                "Sekarang"
        ))
        list.add(ConsultantExperience(
                2,
                1,
                "Freelance Konsultan",
                "2021",
                "2021"
        ))
        return list
    }

    fun getSkillList(): List<ConsultantSkill> {
        val list: MutableList<ConsultantSkill> = ArrayList()
        list.add(ConsultantSkill(
                1,
                1,
                "Konflik internal percintaan"
        ))
        list.add(ConsultantSkill(
                2,
                1,
                "Teknik meningkatkan kepercayaan diri"
        ))
        list.add(ConsultantSkill(
                3,
                1,
                "Love Language Detection"
        ))
        return list
    }

    fun getUploadDocumentList(): List<Document> {
        val list: MutableList<Document> = ArrayList()
        list.add(
            Document(1,"Foto KTP",
                "Ini adalah deskripsi dari dokumen foto KTP", "")
        )

        list.add(
            Document(2,"Foto NPWP",
                "Ini adalah deskripsi dari dokumen foto NPWP", "")
        )

        list.add(
            Document(3,"Foto NPWP",
                "Ini adalah deskripsi dari dokumen foto NPWP", "ini adalah link file")
        )

        return list
    }
}