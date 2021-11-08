package com.halokonsultan.mobile.ui.chooselocation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import java.util.ArrayList
import android.widget.ArrayAdapter
import android.widget.TextView
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.City
import com.halokonsultan.mobile.data.model.Province
import java.lang.Exception


class ChooseCityAdapter(
    mContext: Context,
    private val layoutResourceId: Int,
    private val mArrayList: ArrayList<City>
) : ArrayAdapter<City?>(mContext, layoutResourceId, mArrayList as List<City?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mConvertView = convertView
        try {
            if (mConvertView == null) {
                mConvertView = LayoutInflater.from(parent.context).inflate(layoutResourceId, parent, false)
            }

            val model: City = mArrayList[position]
            val inputKota: TextView = mConvertView!!.findViewById(R.id.tv_location)
            inputKota.text = model.nama

        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mConvertView!!
    }
}