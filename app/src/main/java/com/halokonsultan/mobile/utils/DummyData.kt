package com.halokonsultan.mobile.utils

import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.data.model.Consultant
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
}