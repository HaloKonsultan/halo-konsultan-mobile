package com.halokonsultan.mobile.ui.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.halokonsultan.mobile.base.BaseFragment
import com.halokonsultan.mobile.data.model.Profile
import com.halokonsultan.mobile.data.model.dto.LogoutResponse
import com.halokonsultan.mobile.databinding.FragmentProfileBinding
import com.halokonsultan.mobile.ui.auth.LandingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        = FragmentProfileBinding::inflate
    private val viewModel: ProfileViewModel by activityViewModels()
    private var idUser: Int = 0
    private var name: String = ""
    private var email: String = ""
    private var province: String = ""
    private var city: String = ""

    override fun setup() {
        viewModel.getProfileAdvance().observe(viewLifecycleOwner, setupProfileObserver())

        binding.btnEditProfil.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            intent.putExtra(EditProfileActivity.EXTRA_ID, id)
            intent.putExtra(EditProfileActivity.EXTRA_NAME, name)
            intent.putExtra(EditProfileActivity.EXTRA_EMAIL, email)
            intent.putExtra(EditProfileActivity.EXTRA_PROVINCE, province)
            intent.putExtra(EditProfileActivity.EXTRA_CITY, city)
            startActivity(intent)
        }

        binding.btnBankDokumen.setOnClickListener {
            val intent = Intent(context, BankDocumentActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout().observe(viewLifecycleOwner, setupLogoutObserver())
        }
    }

    private fun setupProfileObserver() = setObserver<Profile>(
        onSuccess = { data ->
            populateData(data.data)
            idUser = data.data?.id!!
            name = data.data.name
            email = data.data.email
            province = data.data.province ?: "DKI JAKARTA"
            city = data.data.city ?: "KOTA ADM. JAKARTA PUSAT"
        },
        onError = { data ->  showToast(data.message.toString()) },
        onLoading = { data -> populateData(data.data) }
    )

    private fun setupLogoutObserver() = setObserver<LogoutResponse>(
        onSuccess = {
            val intent = Intent(context, LandingActivity::class.java)
            startActivity(intent)
            activity?.finish()
        },
        onError = {
            viewModel.resetPref()
            val intent = Intent(context, LandingActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    )

    private fun populateData(data: Profile?) {
        binding.tvNamaLengkap.text = data?.name
        binding.tvNamaEmail.text = data?.email
        binding.tvNamaProvinsi.text = data?.province
        binding.tvNamaKota.text = data?.city
    }
}