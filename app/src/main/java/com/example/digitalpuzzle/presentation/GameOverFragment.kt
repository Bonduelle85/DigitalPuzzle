package com.example.digitalpuzzle.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.digitalpuzzle.R
import com.example.digitalpuzzle.databinding.FragmentGameOverBinding
import com.example.digitalpuzzle.domain.entity.GameResult

class GameOverFragment : Fragment() {

    private var _binding: FragmentGameOverBinding? = null
    private val binding: FragmentGameOverBinding
        get() = _binding ?: throw RuntimeException("FragmentGameOverBinding == null")

    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        setupClickListeners()
    }

    private fun bindViews() {
        binding.smileImage.setImageResource(getImageId())
        binding.scoreTextView.text = getString(R.string.your_score, gameResult.countRightAnswers)
        binding.percentTextView.text = getString(R.string.your_percent, getPercentOfRightAnswers())
        binding.rightAnswersTextView.text = getString(
            R.string.required_answers,
            gameResult.gameSettings.minCountOfRightAnswers
        )
        binding.requiredPercentTextView.text = getString(
            R.string.required_percent,
            gameResult.gameSettings.minPercentOfRightAnswers
        )
    }

    private fun setupClickListeners() {
        binding.tryAgainButton.setOnClickListener { retryGame() }
    }

    private fun getImageId(): Int =
        if (gameResult.isWinnner) R.drawable.winner else R.drawable.loser

    private fun getPercentOfRightAnswers() = with(gameResult) {
        val percentOfRightAnswers = ((countRightAnswers / countQuestions.toDouble()) * 100).toInt()
        if (countQuestions == 0) 0 else percentOfRightAnswers
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArguments() {
        val arguments = requireArguments().parcelable<GameResult>(GAME_RESULT_KEY)
        arguments?.let { gameResult = it }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    companion object {

        const val GAME_RESULT_KEY = "GAME_RESULT_KEY"

        fun newInstance(gameResult: GameResult): GameOverFragment {
            return GameOverFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GAME_RESULT_KEY, gameResult)
                }
            }
        }
    }
}