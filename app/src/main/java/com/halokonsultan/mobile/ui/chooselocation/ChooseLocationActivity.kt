package com.halokonsultan.mobile.ui.chooselocation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.afollestad.materialdialogs.utils.MDUtil.getStringArray
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivityChooseLocationBinding
import com.halokonsultan.mobile.ui.main.MainActivity
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseLocationActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
    }

    private lateinit var binding: ActivityChooseLocationBinding
    private val viewModel: ChooseLocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f

        setupProvinceChooser()

        val bundle = intent.extras
        if (bundle != null) {
            val id = intent.getIntExtra(EXTRA_ID, 0)
            val name = intent.getStringExtra(EXTRA_NAME)
        }
    }

    private fun setupProvinceChooser() {
        viewModel.getAllProvince()
        viewModel.provinces.observe(this, { response ->
            when(response) {
                is Resource.Success -> {

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