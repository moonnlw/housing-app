package com.example.houseapp.ui.user.loginscreen


import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentLoginBinding
import com.example.houseapp.ui.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>
    (R.layout.fragment_login) {

    override val fragmentViewModel by activityViewModels<LoginViewModel> { appContainer.viewModelFactory }

    override fun onReady(savedInstanceState: Bundle?) { }
}