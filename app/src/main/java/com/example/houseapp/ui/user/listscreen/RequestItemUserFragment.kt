package com.example.houseapp.ui.user.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentRequestInfoUserBinding
import com.example.houseapp.ui.adapters.RequestAdapter.Companion.REQUEST_KEY


/**
 * Фрагмент отображает выбранную заявку из списка заявок
 */
class RequestItemUserFragment : Fragment() {

    private val itemViewModel: RequestItemUserViewModel by activityViewModels {
        (requireActivity().application as MyApplication).appContainer.viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        itemViewModel.requestId.value = requireArguments().getInt(REQUEST_KEY)
        val binding =
            DataBindingUtil.inflate<FragmentRequestInfoUserBinding>(
                inflater,
                R.layout.fragment_request_info_user,
                container,
                false
            ).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = itemViewModel
            }
        return binding.root
    }
}
