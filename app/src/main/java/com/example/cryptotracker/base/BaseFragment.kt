package com.example.cryptotracker.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.cryptotracker.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<
        VB : ViewBinding,
        STATE : State,
        VM : BaseViewModel<STATE>,
        > : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!


    protected abstract val viewModel: VM?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeState()
    }

    override fun onResume() {
        super.onResume()
        makeFragmentFullScreen()
        changeStatusBarIconColor(iconsShouldBeLight = true)
    }

    fun makeFragmentFullScreen() = with(binding) {
        root.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = getColor(R.color.transparent)
    }

    private fun observeState() {
        viewModel?.let {
            it.state
                .onEach(this::render)
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .launchIn(lifecycleScope)
        }


    }

    fun getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(requireContext(), colorRes)

    fun navigate(
        screenId: Int,
        navOptions: NavOptions? = null
    ) {

        findNavController().navigate(screenId, Bundle(), navOptions)
    }

    fun changeStatusBarIconColor(iconsShouldBeLight: Boolean) {
        val decorView = requireActivity().window.decorView
        decorView.systemUiVisibility = if (iconsShouldBeLight) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }

    fun navigateBack() {
        findNavController().popBackStack()
    }

    protected abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected abstract fun initViews()

    protected abstract fun render(state: STATE)

}