package com.halokonsultan.mobile.ui.consultant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.base.ActivityWithCustomToolbar
import com.halokonsultan.mobile.data.model.DetailConsultant
import com.halokonsultan.mobile.databinding.ActivityConsultantBinding
import com.halokonsultan.mobile.ui.booking.BookingActivity
import com.halokonsultan.mobile.ui.chat.ConversationActivity
import com.halokonsultan.mobile.utils.Resource
import com.halokonsultan.mobile.utils.Utils
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConsultantActivity : ActivityWithCustomToolbar<ActivityConsultantBinding>() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    override val bindingInflater: (LayoutInflater) -> ActivityConsultantBinding
        = ActivityConsultantBinding::inflate
    private lateinit var documentationAdapter: DocumentationAdapter
    private lateinit var educationAdapter: EducationAdapter
    private lateinit var experienceAdapter: ExperienceAdapter
    private lateinit var skillAdapter: SkillAdapter
    private val viewModel: ConsultantViewModel by viewModels()
    private var id = 0
    private var profileData: DetailConsultant? = null

    override fun setup() {
        setTitle("Profil Konsultan")
        setupRecyclerView()

        val bundle = intent.extras
        if (bundle != null) {
            id = intent.getIntExtra(EXTRA_ID, 0)
            viewModel.getConsultantDetail(id)
        }

        observeProfile()
        observeChat()
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnBooking.setOnClickListener {
            val intent = Intent(this@ConsultantActivity, BookingActivity::class.java)
            intent.putExtra(BookingActivity.EXTRA_ID, id)
            intent.putExtra(BookingActivity.EXTRA_NAME, profileData?.name)
            intent.putExtra(BookingActivity.EXTRA_PHOTO, profileData?.photo)
            intent.putExtra(BookingActivity.EXTRA_CATEGORY, profileData?.position)
            startActivity(intent)
        }

        binding.btnChat.setOnClickListener {
            viewModel.openConversation(id)
        }
    }

    private fun observeChat() {
        viewModel.chat.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val data = response.data
                    val intent = Intent(this@ConsultantActivity, ConversationActivity::class.java)
                    intent.putExtra(ConversationActivity.EXTRA_ID, data?.id)
                    intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_NAME, data?.name)
                    intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_PHOTO, data?.photo)
                    intent.putExtra(ConversationActivity.EXTRA_CONSULTANT_CATEGORY, data?.category)
                    intent.putExtra(ConversationActivity.EXTRA_IS_ENDED, data?.is_ended)
                    startActivity(intent)
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
    }

    private fun observeProfile() {
        viewModel.profile.observe(this, { response ->
            when(response) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    profileData = response.data
                    populateData()
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
    }

    private fun populateData() {
        val btnDiscussText = "Rp ${profileData?.chat_price}"
        with(binding) {
            tvConsultantName.text = profileData?.name
            tvConsultantCategory.text = profileData?.position
            tvConsultantTotalLikes.text = profileData?.likes_total.toString()
            tvConsultantLocation.text = profileData?.city
            tvConsultantDesc.text = profileData?.description
            tvChatPrice.text = btnDiscussText

            experienceAdapter.differ.submitList(profileData?.consultant_experience)
            documentationAdapter.differ.submitList(profileData?.consultant_documentation)
            educationAdapter.differ.submitList(profileData?.consultant_education)
            skillAdapter.differ.submitList(profileData?.consultant_skill)

            Picasso.get().load(profileData?.photo)
                .resize(120, 120)
                .centerCrop()
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