package com.example.houseapp.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.houseapp.AppContainer
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentRequestInfoBinding
import com.example.houseapp.homescreen.UserViewmodel
import com.example.houseapp.listscreen.RequestAdapter.Companion.REQUEST_KEY
import kotlinx.android.synthetic.main.fragment_request_info.*

/**
 * Фрагмент отображает выбранную заявку из списка заявок
 */
class RequestItemView : Fragment() {

    private lateinit var binding: FragmentRequestInfoBinding
    private lateinit var appContainer: AppContainer
    private val itemViewModel: RequestItemViewModel by activityViewModels { appContainer.requestsViewModelFactory }
    private val userViewModel: UserViewmodel by activityViewModels()

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
                itemVM = itemViewModel
                userVM = userViewModel
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
            answerInputLayout.hint =
                if (hasFocus) ""
                else
                    if (answerInputField.text.isNullOrEmpty()) getString(R.string.your_reply_text)
                    else ""
        }

        return binding.root
    }
}
