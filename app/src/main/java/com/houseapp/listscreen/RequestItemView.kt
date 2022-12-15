package com.houseapp.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.houseapp.R
import com.houseapp.AppContainer
import com.houseapp.MyApplication
import com.houseapp.databinding.FragmentRequestInfoBinding
import com.houseapp.listscreen.RequestAdapter.Companion.REQUEST_KEY

/**
 * Фрагмент отображает выбранную заявку из списка заявок
 */
class RequestItemView : Fragment() {

    private lateinit var binding: FragmentRequestInfoBinding
    private lateinit var appContainer: AppContainer
    private val itemViewModel: RequestItemViewModel by activityViewModels { appContainer.requestsViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        appContainer = (requireActivity().application as MyApplication).appContainer
        itemViewModel.requestId = requireArguments().getInt(REQUEST_KEY)

        binding =
            DataBindingUtil.inflate<FragmentRequestInfoBinding?>(
                inflater,
                R.layout.fragment_request_info,
                container,
                false
            ).apply {
                // Устанавливаем lifecycleOwner, чтобы binding мог прослушивать LiveData
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

        return binding.root
    }
}