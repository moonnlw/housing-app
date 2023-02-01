package com.example.houseapp.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.houseapp.utils.AppContainer
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.ui.adapters.RequestAdapter.Companion.REQUEST_KEY
import com.example.houseapp.databinding.FragmentRequestInfoAdminBinding


/**
 * Фрагмент отображает выбранную заявку из списка заявок
 */
class RequestItemAdminFragment : Fragment() {

    private lateinit var binding: FragmentRequestInfoAdminBinding
    private lateinit var appContainer: AppContainer
    private val itemViewModel: RequestItemAdminViewModel by activityViewModels { appContainer.viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        appContainer = (requireActivity().application as MyApplication).appContainer
        itemViewModel.requestId = requireArguments().getInt(REQUEST_KEY)

        binding =
            DataBindingUtil.inflate<FragmentRequestInfoAdminBinding>(
                inflater,
                R.layout.fragment_request_info_admin,
                container,
                false
            ).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = itemViewModel
            }

        binding.acceptButton.setOnClickListener {
            val answer = binding.answerInputField.text.toString()
            if (answer.isEmpty()) {
                Toast.makeText(
                    activity,
                    "Please fill the answer field before accepting",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                itemViewModel.updateRequest(answer, true)
            }
        }

        binding.declineButton.setOnClickListener {
            val answer = binding.answerInputField.text.toString()
            if (answer.isEmpty()) {
                Toast.makeText(
                    activity,
                    "Please fill the answer field before declining",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                itemViewModel.updateRequest(answer, false)
            }
        }

        binding.answerInputField.setOnFocusChangeListener { _, hasFocus ->
            binding.answerInputLayout.hint =
                if (hasFocus) ""
                else
                    if (binding.answerInputField.text.isNullOrEmpty()) getString(R.string.your_reply_text)
                    else ""
        }

        return binding.root
    }
}
