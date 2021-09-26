package com.halokonsultan.mobile.ui.consultant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.halokonsultan.mobile.R
import com.halokonsultan.mobile.databinding.ActivityConsultantBinding
import com.halokonsultan.mobile.utils.DummyData

class ConsultantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConsultantBinding
    private lateinit var documentationAdapter: DocumentationAdapter
    private lateinit var educationAdapter: EducationAdapter
    private lateinit var experienceAdapter: ExperienceAdapter
    private lateinit var skillAdapter: SkillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        documentationAdapter.differ.submitList(DummyData.getDocumentList())
        educationAdapter.differ.submitList(DummyData.getEducationList())
        experienceAdapter.differ.submitList(DummyData.getExperienceList())
        skillAdapter.differ.submitList(DummyData.getSkillList())
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
}