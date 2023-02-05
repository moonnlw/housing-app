package com.example.houseapp.ui.user.newrequestscreen

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentCreateRequestBinding
import com.example.houseapp.ui.BaseFragment

/**
 * Форма создания новой заявки
 */
class CreateRequestFragment :
    BaseFragment<FragmentCreateRequestBinding, CreateRequestViewModel>
        (R.layout.fragment_create_request) {

    private val args by navArgs<CreateRequestFragmentArgs>()

    override val fragmentViewModel:
            CreateRequestViewModel by activityViewModels { appContainer.viewModelFactory }

    override fun CreateRequestViewModel.initialize() {
        userId.value = args.userId
    }

    override fun onReady(savedInstanceState: Bundle?) { }
}