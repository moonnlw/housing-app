package com.example.houseapp.ui.admin

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentRequestInfoAdminBinding
import com.example.houseapp.ui.BaseFragment


/**
 * Фрагмент отображает выбранную заявку из списка заявок
 */
class RequestItemAdminFragment :
    BaseFragment<FragmentRequestInfoAdminBinding, RequestItemAdminViewModel>(R.layout.fragment_request_info_admin) {

    private val args by navArgs<RequestItemAdminFragmentArgs>()
    override val fragmentViewModel: RequestItemAdminViewModel by viewModels { appContainer.viewModelFactory }

    override fun RequestItemAdminViewModel.initialize() {
        userId.value = args.userId.toString()
        requestId.value = args.requestId
    }
    override fun onReady(savedInstanceState: Bundle?) {
        setUpInputView()
    }

    private fun setUpInputView() {
        binding.answerInputField.setOnFocusChangeListener { _, hasFocus ->
            binding.answerInputLayout.hint =
                if (hasFocus) ""
                else
                    if (binding.answerInputField.text.isNullOrEmpty()) getString(R.string.your_reply_text)
                    else ""
        }
    }
}
