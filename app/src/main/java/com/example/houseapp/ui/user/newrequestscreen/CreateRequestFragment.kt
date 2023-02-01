package com.example.houseapp.ui.user.newrequestscreen

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
import com.example.houseapp.databinding.FragmentCreateRequestBinding

/**
 * Форма создания новой заявки
 */
class CreateRequestFragment : Fragment() {

    private val createRequestViewModel: CreateRequestViewModel by activityViewModels {
        (requireActivity().application as MyApplication).appContainer.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentCreateRequestBinding?>(
            inflater,
            R.layout.fragment_create_request,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = createRequestViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        createRequestViewModel.isEnoughData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { isEnough ->
                if (!isEnough) {
                    findNavController().navigate(R.id.action_navigate_home)
                }
            }
        }
        createRequestViewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}