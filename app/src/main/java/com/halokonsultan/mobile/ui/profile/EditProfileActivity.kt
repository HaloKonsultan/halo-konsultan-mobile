package com.halokonsultan.mobile.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.ActivityWithCustomToolbar
import com.halokonsultan.mobile.data.model.City
import com.halokonsultan.mobile.data.model.Profile
import com.halokonsultan.mobile.data.model.Province
import com.halokonsultan.mobile.data.model.dto.BasicResponse
import com.halokonsultan.mobile.data.model.dto.LocationResponse
import com.halokonsultan.mobile.databinding.ActivityEditProfileBinding
import com.halokonsultan.mobile.ui.chooselocation.ChooseLocationViewModel
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : ActivityWithCustomToolbar<ActivityEditProfileBinding>() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PROVINCE = "extra_province"
        const val EXTRA_CITY = "extra_city"
    }

    private val viewModel: ChooseLocationViewModel by viewModels()
    private var id: Int = 0
    private var name: String = ""
    private var email: String = ""
    private var province: String = ""
    private var city: String = ""
    private var idForProvince: String = ""

    override val bindingInflater: (LayoutInflater) -> ActivityEditProfileBinding
        = ActivityEditProfileBinding::inflate

    private val cityObserver by lazy { setupCityObserver() }
    private val provinceObserver by lazy { setupProvinceObserver() }
    private val updateProfileObserver by lazy { setupUpdateProfileObserver() }

    override fun setup() {
        setupSupportActionBar()
        setTitle("Edit Profil")

        val bundle = intent.extras
        if (bundle != null) {
            id = intent.getIntExtra(EXTRA_ID, 0)
            name = intent.getStringExtra(EXTRA_NAME).toString()
            email = intent.getStringExtra(EXTRA_EMAIL).toString()
            province = intent.getStringExtra(EXTRA_PROVINCE).toString()
            city = intent.getStringExtra(EXTRA_CITY).toString()
        }

        binding.etEmail.isEnabled = false

        showProfile()
        setupProvinceChooser()

        binding.inputProvinsi.setOnItemClickListener { adapterView, view, position, l ->
            val adapter = binding.inputProvinsi.adapter
            val item = adapter.getItem(position) as Province
            binding.inputKota.text.clear()
            province = item.name
            setupCityChooser(item.id)
        }

        binding.inputKota.setOnItemClickListener { adapterView, view, position, l ->
            val adapter = binding.inputKota.adapter
            val item = adapter.getItem(position) as City
            city = item.name
            idForProvince = item.id
        }

        binding.btnSaveChange.setOnClickListener {
            val name = binding.etNama.text.toString()
            viewModel.location(viewModel.getUserID(), name, province, city).observe(this, updateProfileObserver)
        }
    }

    private fun setupUpdateProfileObserver() = setObserver<BasicResponse<Profile>>(
        onSuccess = {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    )

    private fun showProfile() {
        binding.etNama.setText(name, TextView.BufferType.EDITABLE)
        binding.etEmail.setText(email)
        binding.inputProvinsi.setText(province)
        binding.inputKota.setText(city)
    }

    private fun setupCityObserver() = setObserver<LocationResponse<City>>(
        onSuccess = { data ->
            val adapter = ArrayAdapter(
                this,
                R.layout.item_location,
                ArrayList(data.data?.value)
            )
            binding.inputKota.setAdapter(adapter)
        }
    )

    private fun setupProvinceObserver() = setObserver<LocationResponse<Province>>(
        onSuccess = { data ->
            val adapter = ArrayAdapter(
                this,
                R.layout.item_location,
                ArrayList(data.data?.value)
            )
            binding.inputProvinsi.setAdapter(adapter)
        }
    )

    private fun setupCityChooser(id: String) {
        viewModel.getAllCities(id).observe(this, cityObserver)
    }

    private fun setupProvinceChooser() {
        viewModel.getAllProvince().observe(this, provinceObserver)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}