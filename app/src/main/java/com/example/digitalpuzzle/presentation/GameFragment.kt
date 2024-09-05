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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digitalpuzzle.R
import com.example.digitalpuzzle.databinding.FragmentGameBinding
import com.example.digitalpuzzle.domain.entity.GameResult

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

        private  val arguments by navArgs<GameFragmentArgs>()

    private val viewModelFactory by lazy {
//        val arguments = GameFragmentArgs.fromBundle(requireArguments())
        GameViewModelFactory(requireActivity().application, arguments.level)
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)
    }

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

    private fun launchGameOverFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameOverFragment(
                gameResult
            )
        )
    }
}