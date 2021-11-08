package com.halokonsultan.mobile.ui.chooselocation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import java.util.ArrayList
import android.widget.ArrayAdapter
import android.widget.TextView
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.Province
import java.lang.Exception


class ChooseProvinceAdapter(
    mContext: Context,
    private val layoutResourceId: Int,
    private val mArrayList: ArrayList<Province>
) : ArrayAdapter<Province?>(mContext, layoutResourceId, mArrayList as List<Province?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mConvertView = convertView
        try {
            if (mConvertView == null) {
                mConvertView = LayoutInflater.from(parent.context).inflate(layoutResourceId, parent, false)
            }

            val model: Province = mArrayList[position]
            val inputProvinsi: TextView = mConvertView!!.findViewById(R.id.tv_location)
            inputProvinsi.text = model.nama

        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mConvertView!!
    }

    fun getItemNameAtPosition(position: Int): String {
        return mArrayList[position].nama
    }

    fun getItemIDAtPosition(position: Int): Int {
        return mArrayList[position].id
    }
}