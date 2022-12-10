package com.example.houseapp.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.houseapp.AppContainer
import com.example.houseapp.MyApplication
import com.example.houseapp.R
import com.example.houseapp.databinding.FragmentRequestInfoBinding
import com.example.houseapp.listscreen.RequestAdapter.Companion.REQUEST_KEY

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

        return binding.root
    }
}
