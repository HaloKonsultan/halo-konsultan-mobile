package com.halokonsultan.mobile.ui.chooselocation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.halokonsultan.mobile.data.model.City


@AndroidEntryPoint
class ChooseLocationActivity() : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
    }

    private lateinit var binding: ActivityChooseLocationBinding
    private val viewModel: ChooseLocationViewModel by viewModels()
    private var id: Int = 0
    private var name: String = ""
    private var provinceName: String = ""
    private var cityName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            provinceName = itemProvince.nama
            setupCityChooser(itemProvince.id)
        }

        binding.inputKota.setOnItemClickListener { adapterView, view, position, l ->
            val adapter = binding.inputKota.adapter
            val itemCity = adapter.getItem(position) as City
            cityName = itemCity.nama
        }

        binding.btnSelesai.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            viewModel.location(id, name, provinceName, cityName)
            viewModel.profile.observe(this, { response ->
                if (response is Resource.Success) {
                    startActivity(intent)
                    finish()
                }
            })
        }
    }

    private fun setupCityChooser(id: Int) {
        viewModel.getAllCities(id)
        viewModel.cities.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    val adapter = ArrayAdapter(
                        this,
                        R.layout.item_location,
                        ArrayList(response.data)
                    )
                    binding.inputKota.setAdapter(adapter)
                }
            }
        })
    }

    private fun setupProvinceChooser() {
        viewModel.getAllProvince()
        viewModel.provinces.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    val adapter = ArrayAdapter(
                        this,
                        R.layout.item_location,
                        ArrayList(response.data)
                    )
                    binding.inputProvinsi.setAdapter(adapter)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.next_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miNext -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }
}