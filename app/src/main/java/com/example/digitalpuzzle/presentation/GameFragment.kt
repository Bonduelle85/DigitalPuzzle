package com.example.digitalpuzzle.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.example.digitalpuzzle.R
import com.example.digitalpuzzle.databinding.FragmentGameBinding
import com.example.digitalpuzzle.domain.entity.GameResult
import com.example.digitalpuzzle.domain.entity.Level

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)
    }

    lateinit var level: Level

    private val optionTextViewList by lazy {
        mutableListOf<TextView>(
            binding.optionOneTextView,
            binding.optionTwoTextView,
            binding.optionThreeTextView,
            binding.optionFourTextView,
            binding.optionFiveTextView,
            binding.optionSixTextView,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oberveViewModel()
        setClickListenersToOptions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setClickListenersToOptions() {
        optionTextViewList.forEach { optionTextView ->
            optionTextView.setOnClickListener {
                viewModel.chooseAnswer(optionTextView.text.toString().toInt())
            }
        }
    }

    private fun oberveViewModel() {
        viewModel.formattedTimer.observe(viewLifecycleOwner) { time ->
            binding.timerTextView.text = time
        }
        viewModel.question.observe(viewLifecycleOwner) { question ->
            binding.sumTextView.text = question.sumNumber.toString()
            binding.visibleNumberTextView.text = question.visibleNumber.toString()
            optionTextViewList.forEachIndexed { i, textView ->
                textView.text = question.answers[i].toString()
            }
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) { percent ->
            binding.gameProgressBar.setProgress(percent, true)
        }
        viewModel.enoughRightAnswers.observe(viewLifecycleOwner) { enough: Boolean ->
            val color = getStateColor(enough)
            binding.statusTextView.setTextColor(color)
        }
        viewModel.enoughPercent.observe(viewLifecycleOwner) { enough: Boolean ->
            val color = getStateColor(enough)
            binding.gameProgressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.gameProgressBar.secondaryProgress = it
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) { progressAnswers ->
            binding.statusTextView.text = progressAnswers
        }
        viewModel.gameResult.observe(viewLifecycleOwner) { gameResult ->
            launchGameOverFragment(gameResult)
        }
    }

    private fun getStateColor(state: Boolean): Int {
        val colorResId = if (state) R.color.enoughTextColor else R.color.notEnoughTextColor
        val color = ContextCompat.getColor(requireContext(), colorResId)
        return color
    }

    private fun parseArguments() {
        val arguments = requireArguments().parcelable<Level>(LEVEL_KEY)
        arguments?.let { level = it }
    }

    private fun launchGameOverFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, GameOverFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        const val NAME = "GameFragment"
        private const val LEVEL_KEY = "LEVEL_KEY"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(LEVEL_KEY, level)
                }
            }
        }
    }
}