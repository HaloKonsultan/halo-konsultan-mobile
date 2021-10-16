package com.halokonsultan.mobile.utils

import com.halokonsultan.mobile.data.model.*
import kotlin.collections.ArrayList


object DummyData {
    fun getCategoryList(): List<Category> {
        val list: MutableList<Category> = ArrayList()
        list.add(Category(1,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan"))

        list.add(Category(2,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan"))

        list.add(Category(3,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan"))

        list.add(Category(4,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan"))

        list.add(Category(5,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Perpajakan"))

        list.add(
            Category(
                999,
                "https://res.cloudinary.com/anongtf/image/upload/v1632140913/Perpajakan_sa3sbc.svg",
                "Lainnya",
            )
        )

        return list
    }

    fun getParentCategoryList(): List<ParentCategory> {
        val list: MutableList<ParentCategory> = ArrayList()
        list.add(
            ParentCategory(
            1,
            "Agama",
            getCategoryList())
        )

        list.add(
            ParentCategory(
                2,
                "Keuangan",
                getCategoryList())
        )

        list.add(
            ParentCategory(
                3,
                "Bangunan",
                getCategoryList())
        )

        list.add(
            ParentCategory(
                4,
                "Kesehatan",
                getCategoryList())
        )
        return list
    }

    fun getConsultantList(): List<Consultant> {
        val list: MutableList<Consultant> = ArrayList()
        list.add(Consultant(
                1,
                "nama lengkap",
                "Konsultan Hukum Perdata",
                100,
                "Sidoarjo",
        "https://res.cloudinary.com/anongtf/image/upload/v1632140731/nuupzj8zr4ws0j7hiykq.png"))

        list.add(Consultant(
                2,
                "nama lengkap",
                "Konsultan Hukum Perdata",
                100,
                "Sidoarjo",
                "https://res.cloudinary.com/anongtf/image/upload/v1632140731/nuupzj8zr4ws0j7hiykq.png"))
        list.add(Consultant(
                3,
                "nama lengkap",
                "Konsultan Hukum Perdata",
                100,
                "Sidoarjo",
                "https://res.cloudinary.com/anongtf/image/upload/v1632140731/nuupzj8zr4ws0j7hiykq.png"))

        return list
    }

//    fun getConsultationList(): List<Consultation> {
//        val list: MutableList<Consultation> = ArrayList()
//        list.add(
//            Consultation(
//                1,
//                1,
//                "Konsultanku",
//                "Konsultasi hati",
//                "aktif",
//                1,
//                "1632398841000"
//            )
//        )
//        list.add(
//            Consultation(
//                1,
//                1,
//                "Konsultanku",
//                "Konsultasi hati",
//                "aktif",
//                1,
//                "1632398841000"
//            )
//        )
//        list.add(
//            Consultation(
//                1,
//                1,
//                "Konsultanku",
//                "Konsultasi hati",
//                "aktif",
//                1,
//                "1632398841000"
//            )
//        )
//        return list
//    }

//    fun getWaitingConsultationList(): List<Consultation> {
//        val list: MutableList<Consultation> = ArrayList()
//        list.add(
//            Consultation(
//                1,
//                1,
//                "Konsultanku",
//                "Konsultasi hati",
//                "menunggu",
//                1,
//                "1632398841000"
//            )
//        )
//        list.add(
//            Consultation(
//                1,
//                1,
//                "Konsultanku",
//                "Konsultasi hati",
//                "menunggu",
//                1,
//                "1632398841000"
//            )
//        )
//        list.add(
//            Consultation(
//                1,
//                1,
//                "Konsultanku",
//                "Konsultasi hati",
//                "menunggu",
//                1,
//                "1632398841000"
//            )
//        )
//        return list
//    }
//
//    fun getDoneConsultationList(): List<Consultation> {
//        val list: MutableList<Consultation> = ArrayList()
//        list.add(
//            Consultation(
//                1,
//                1,
//                "Konsultanku",
//                "Konsultasi hati",
//                "selesai",
//                1,
//                "1632398841000"
//            )
//        )
//        list.add(
//            Consultation(
//                1,
//                1,
//                "Konsultanku",
//                "Konsultasi hati",
//                "selesai",
//                1,
//                "1632398841000"
//            )
//        )
//        list.add(
//            Consultation(
//                1,
//                1,
//                "Konsultanku",
//                "Konsultasi hati",
//                "selesai",
//                1,
//                "1632398841000"
//            )
//        )
//        return list
//    }

    fun getDocumentList(): List<ConsultantDoc> {
        val list: MutableList<ConsultantDoc> = ArrayList()
        list.add(ConsultantDoc(
                1,
                "https://res.cloudinary.com/anongtf/image/upload/v1632654470/rgv0nn0dbm6ekvwpxaw7.png"
        ))

        list.add(ConsultantDoc(
                2,
                "https://res.cloudinary.com/anongtf/image/upload/v1632654507/pxjxngj3brpbylzitjkn.png"
        ))
        return list
    }

    fun getEducationList(): List<ConsultantEducation> {
        val list: MutableList<ConsultantEducation> = ArrayList()
        list.add(ConsultantEducation(
                1,
                "Universitas Atas Bawah",
                "Sarjana Filsahat Hukum Percintaan",
                "2016",
                "2020"
        ))
        list.add(ConsultantEducation(
                2,
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
                "Konsultan Percintaan",
                "2020",
                "Sekarang"
        ))
        list.add(ConsultantExperience(
                2,
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

    fun getConsultationTime(): List<ConsultationsPrefDate> {
        val list: MutableList<ConsultationsPrefDate> = ArrayList()
        list.add(
            ConsultationsPrefDate("Senin, 21 Juni 2021, 12.00", "")
        )

        list.add(
            ConsultationsPrefDate("Selasa, 22 Juni 2021, 12.00", "")
        )

        list.add(
            ConsultationsPrefDate("Rabu, 23 Juni 2021, 15.00", "")
        )

        return list
    }
}