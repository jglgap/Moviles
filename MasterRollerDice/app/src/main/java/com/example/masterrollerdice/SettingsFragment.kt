package com.example.masterrollerdice
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.masterrollerdice.databinding.FragmentSettingsBinding
import kotlin.getValue


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    val model: SettingsViewModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isUpdatingFromViewModel = false

        binding.switchNight.setOnCheckedChangeListener { _, isChecked ->
            if (!isUpdatingFromViewModel) {
                model.nightMode.value = isChecked
            }
        }

        model.nightMode.observe(viewLifecycleOwner) { nightModeEnabled ->
            isUpdatingFromViewModel = true
            binding.switchNight.isChecked = nightModeEnabled
            isUpdatingFromViewModel = false
        }


        model.nightMode.observe(viewLifecycleOwner) { isNightMode ->
            val color = if (isNightMode) {
                ContextCompat.getColor(requireContext(), R.color.white)

            } else {
                ContextCompat.getColor(requireContext(), R.color.dark_blue)
            }

            binding.nightText.setTextColor(color)
            binding.soundText.setTextColor(color)
        }
        model.nightMode.observe(viewLifecycleOwner) { isNightMode ->
            val backColor = if (isNightMode) {
                ContextCompat.getColor(requireContext(), R.color.dark_blue)

            } else {
                ContextCompat.getColor(requireContext(), R.color.lavender)
            }

            binding.layoutSettings.setBackgroundColor(backColor)

        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}