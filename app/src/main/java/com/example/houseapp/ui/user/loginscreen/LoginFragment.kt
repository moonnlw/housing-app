package com.example.houseapp.ui.user.loginscreen


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
import com.example.houseapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var fragmentLoginBinding: FragmentLoginBinding? = null

    private val loginViewModel by activityViewModels<LoginViewModel> {
        (requireActivity().application as MyApplication).appContainer.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = DataBindingUtil.inflate<FragmentLoginBinding?>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = loginViewModel
            fragmentLoginBinding = this
        }
        observeData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenSignUpButton()
    }

    override fun onDestroyView() {
        fragmentLoginBinding = null
        super.onDestroyView()
    }

    private fun listenSignUpButton() {
        fragmentLoginBinding?.signupButton?.setOnClickListener {
            findNavController().navigate(R.id.navigate_to_signup_fragment)
        }
    }

    private fun observeData() {
        loginViewModel.isAuthorized.observe(viewLifecycleOwner) { isAuth ->
            if (isAuth) {
                findNavController().navigate(R.id.userActivityDestination)
            }
        }

        loginViewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}