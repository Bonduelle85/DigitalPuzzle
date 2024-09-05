package com.example.digitalpuzzle.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.digitalpuzzle.R
import com.example.digitalpuzzle.databinding.FragmentChooseLevelBinding
import com.example.digitalpuzzle.domain.entity.Level
import com.example.digitalpuzzle.presentation.GameFragment.Companion.LEVEL_KEY

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            testButton.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            easyButton.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            normalButton.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            hardButton.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFragment(level: Level) {
        val arguments = Bundle().apply {
            putParcelable(LEVEL_KEY, level)
        }
        findNavController().navigate(R.id.action_chooseLevelFragment_to_gameFragment, arguments)
    }

    companion object {

        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }
}