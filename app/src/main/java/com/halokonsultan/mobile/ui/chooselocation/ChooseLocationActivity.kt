package com.halokonsultan.mobile.ui.chooselocation

import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.Province
import com.halokonsultan.mobile.databinding.ActivityChooseLocationBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

import android.widget.ArrayAdapter
import com.halokonsultan.mobile.base.BaseActivity
import com.halokonsultan.mobile.data.model.City
import com.halokonsultan.mobile.data.model.Profile
import com.halokonsultan.mobile.data.model.dto.BasicResponse
import com.halokonsultan.mobile.data.model.dto.LocationResponse


@AndroidEntryPoint
class ChooseLocationActivity() : BaseActivity<ActivityChooseLocationBinding>() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
    }

    private val viewModel: ChooseLocationViewModel by viewModels()
    private var id: Int = 0
    private var name: String = ""
    private var provinceName: String = ""
    private var cityName: String = ""

    override val bindingInflater: (LayoutInflater) -> ActivityChooseLocationBinding
        = ActivityChooseLocationBinding::inflate

    private val cityObserver by lazy { setupCityObserver() }
    private val provinceObserver by lazy { setupProvinceObserver() }
    private val profileObserver by lazy { setupProfileObserver() }

    override fun setup() {
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f

        setupProvinceChooser()

        val bundle = intent.extras
        if (bundle != null) {
            id = intent.getIntExtra(EXTRA_ID, 0)
            name = intent.getStringExtra(EXTRA_NAME)!!
        }

        binding.inputProvinsi.setOnItemClickListener { adapterView, view, position, l ->
            val adapter = binding.inputProvinsi.adapter
            val itemProvince = adapter.getItem(position) as Province
            provinceName = itemProvince.name
            setupCityChooser(itemProvince.id)
        }

        binding.inputKota.setOnItemClickListener { adapterView, view, position, l ->
            val adapter = binding.inputKota.adapter
            val itemCity = adapter.getItem(position) as City
            cityName = itemCity.name
        }

        binding.btnSelesai.setOnClickListener {
            viewModel.location(id, name, provinceName, cityName).observe(this, profileObserver)
        }
    }

    private fun setupProfileObserver() = setObserver<BasicResponse<Profile>>(
        onSuccess = {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    )

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.next_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miNext -> {
                viewModel.location(id, name, "DKI JAKARTA", "KOTA ADM. JAKARTA PUSAT")
                return true
            }
        }
        return false
    }
}