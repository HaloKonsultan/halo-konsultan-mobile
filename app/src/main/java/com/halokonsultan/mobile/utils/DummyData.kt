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
}