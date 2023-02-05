package com.example.houseapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.houseapp.BR
import com.example.houseapp.MyApplication
import com.example.houseapp.data.models.NavigationCommand
import com.example.houseapp.data.models.UIMessage
import com.example.houseapp.utils.AppContainer

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {

    protected abstract val fragmentViewModel: VM
    protected lateinit var appContainer: AppContainer

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!


    protected abstract fun onReady(savedInstanceState: Bundle?)
    protected open fun VM.initialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appContainer = (requireActivity().application as MyApplication).appContainer
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, fragmentViewModel)
        }

        fragmentViewModel.initialize()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigationEvent()
        observeMessageEvent()

        onReady(savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * Прослушивает на наличие [NavigationCommand] от [fragmentViewModel].
     */
    private fun observeNavigationEvent() {
        fragmentViewModel.navigation.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navCom ->
                when (navCom) {
                    is NavigationCommand.NavigateToDirection ->
                        findNavController().navigate(navCom.directions)
                    is NavigationCommand.Back ->
                        findNavController().navigateUp()
                }
            }
        }
    }

    /**
     * Прослушивает на наличие [UIMessage] от [fragmentViewModel].
     */
    private fun observeMessageEvent() {
        fragmentViewModel.message.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { message ->
                when (message) {
                    is UIMessage.DynamicString ->
                        Toast.makeText(activity, message.value, Toast.LENGTH_LONG).show()
                    is UIMessage.StringResource ->
                        Toast.makeText(
                            activity,
                            context?.getString(message.resId),
                            Toast.LENGTH_LONG
                        ).show()
                }
            }
        }
    }
}