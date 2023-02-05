package com.example.houseapp.ui.user.listscreen

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentRequestInfoUserBinding
import com.example.houseapp.ui.BaseFragment


/**
 * Фрагмент отображает выбранную заявку из списка заявок
 */
class RequestItemUserFragment :
    BaseFragment<FragmentRequestInfoUserBinding, RequestItemUserViewModel>
        (R.layout.fragment_request_info_user) {

    private val args by navArgs<RequestItemUserFragmentArgs>()

    override val fragmentViewModel: RequestItemUserViewModel by viewModels { appContainer.viewModelFactory }

    override fun RequestItemUserViewModel.initialize() {
        userId.value = args.userId.toString()
        requestId.value = args.requestId
    }

    override fun onReady(savedInstanceState: Bundle?) {}
}
