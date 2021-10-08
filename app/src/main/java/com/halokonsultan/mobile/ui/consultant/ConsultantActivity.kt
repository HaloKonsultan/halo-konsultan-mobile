package com.halokonsultan.mobile.ui.consultant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.data.model.DetailConsultant
import com.halokonsultan.mobile.databinding.ActivityConsultantBinding
import com.halokonsultan.mobile.ui.booking.BookingActivity
import com.halokonsultan.mobile.utils.DummyData
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsultantActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityConsultantBinding
    private lateinit var documentationAdapter: DocumentationAdapter
    private lateinit var educationAdapter: EducationAdapter
    private lateinit var experienceAdapter: ExperienceAdapter
    private lateinit var skillAdapter: SkillAdapter
    private val viewModel: ConsultantViewModel by viewModels()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.title_text_view)
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        Utils.setTitleTextView(this, "Profil Konsultan")

        val bundle = intent.extras
        if (bundle != null) {
            id = intent.getIntExtra(EXTRA_ID, 0)
            viewModel.getConsultantDetail(id)
        }

        viewModel.profile.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val profileData = response.data
                    populateData(profileData)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, response.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })

        documentationAdapter.differ.submitList(DummyData.getDocumentList())
        educationAdapter.differ.submitList(DummyData.getEducationList())
        experienceAdapter.differ.submitList(DummyData.getExperienceList())
        skillAdapter.differ.submitList(DummyData.getSkillList())

        binding.btnBooking.setOnClickListener {
            val intent = Intent(this@ConsultantActivity, BookingActivity::class.java)
            intent.putExtra(BookingActivity.EXTRA_ID, id)
            startActivity(intent)
        }
    }

    private fun populateData(data: DetailConsultant?) {
        val btnDiscussText = "Diskusi - ${data?.chat_price}"
        with(binding) {
            tvConsultantName.text = data?.name
            tvConsultantCategory.text = data?.category
            tvConsultantTotalLikes.text = data?.likes_total.toString()
            tvConsultantLocation.text = data?.location
            tvConsultantDesc.text = data?.description
            btnDiscuss.text = btnDiscussText

            experienceAdapter.differ.submitList(data?.consultant_experience)
            documentationAdapter.differ.submitList(data?.consultant_doc)
            educationAdapter.differ.submitList(data?.consultant_educations)
            skillAdapter.differ.submitList(data?.consultant_skills)

            Glide.with(this@ConsultantActivity)
                .load(data?.photo)
                .override(120)
                .into(imgConsultant)
        }
    }

    private fun setupRecyclerView() {
        documentationAdapter = DocumentationAdapter()
        educationAdapter = EducationAdapter()
        experienceAdapter = ExperienceAdapter()
        skillAdapter = SkillAdapter()

        with(binding.rvDokumentasi) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = documentationAdapter
        }

        with(binding.rvPendidikan) {
            layoutManager = LinearLayoutManager(context)
            adapter = educationAdapter
        }

        with(binding.rvPengalaman) {
            layoutManager = LinearLayoutManager(context)
            adapter = experienceAdapter
        }

        with(binding.rvKeahlian) {
            layoutManager = LinearLayoutManager(context)
            adapter = skillAdapter
        }
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