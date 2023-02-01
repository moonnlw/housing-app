package com.example.houseapp.ui.user.loginscreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.UserActivity
import com.example.houseapp.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private var fragmentSignupBinding: FragmentSignupBinding? = null

    private val signupViewModel by activityViewModels<SignupViewModel> {
        (requireActivity().application as MyApplication).appContainer.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentSignupBinding?>(
            inflater,
            R.layout.fragment_signup,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = signupViewModel
            fragmentSignupBinding = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        listenLoginButton()
    }

    override fun onDestroyView() {
        fragmentSignupBinding = null
        super.onDestroyView()
    }

    private fun listenLoginButton() {
        fragmentSignupBinding?.loginButton?.setOnClickListener {
            findNavController().navigate(R.id.navigate_to_login_fragment)
        }
    }

    private fun observeData() {
        signupViewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }

        signupViewModel.isRegistered.observe(viewLifecycleOwner) { registered ->
            if (registered) {
                val intent = Intent(activity, UserActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}