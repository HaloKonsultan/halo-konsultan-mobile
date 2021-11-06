package com.halokonsultan.mobile.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.halokonsultan.mobile.data.model.Profile
import com.halokonsultan.mobile.databinding.FragmentProfileBinding
import com.halokonsultan.mobile.ui.auth.LandingActivity
import com.halokonsultan.mobile.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProfileAdvance().observe(viewLifecycleOwner, { data ->
            when(data) {
                is Resource.Success -> {
                    populateData(data.data)
                }
                is Resource.Error -> {
                    Toast.makeText(context, data.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    populateData(data.data)
                }
            }
        })

        binding.btnEditProfil.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnBankDokumen.setOnClickListener {
            val intent = Intent(context, BankDocumentActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            viewModel.logout.observe(viewLifecycleOwner, { data ->
                if (data is Resource.Success) {
                    val intent = Intent(context, LandingActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    viewModel.resetPref()
                    val intent = Intent(context, LandingActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            })
        }
    }

    private fun populateData(data: Profile?) {
        binding.tvNamaLengkap.text = data?.name
        binding.tvNamaEmail.text = data?.email
        binding.tvNamaKota.text = data?.city
    }
}