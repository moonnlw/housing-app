package com.example.houseapp.ui.user.loginscreen

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentSignupBinding
import com.example.houseapp.ui.BaseFragment

class SignupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>
    (R.layout.fragment_signup) {

    override val fragmentViewModel by activityViewModels<SignupViewModel> { appContainer.viewModelFactory }

    override fun onReady(savedInstanceState: Bundle?) { }
}