package com.example.android.unscramble.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.MAX_NO_OF_WORDS
import com.example.android.unscramble.data.SCORE_INCREASE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.android.unscramble.data.allWords
import kotlinx.coroutines.flow.update

// Game UI state


class GameViewModel:ViewModel() {
    data class GameUiState(
        val currentScrambledWord: String = "",
        val isGuessedWordWrong: Boolean = false,
        val currentWordCount: Int = 1,
        val score: Int = 0,
        val isGameOver:Boolean = false
    )
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    //2. Set of words used in the game
    private var usedWords: MutableSet<String> = mutableSetOf()
    init {
        resetGame()
    }
    //게임을 초기화하는 함수
    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    //1. 게임 대상이 되는 정답
    private lateinit var currentWord: String

    //3. user Input
    var userGuess by mutableStateOf("")
        private set
    //게임에서 아직 제시되지않은 단어를
    //usedWords에 있는지 판별하고 없다면 currentWord로 선언하는 함수
    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }
    ///정답이 되는 단어를 퀴즈 대상이 되도록
    // 순서를 random하게 섞는 함수
    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }
    //userGuess를 viewmodel 내부에서 업데이트 해주기 위한 함수
    //screen to viewmodel
    fun updateUserGuess(guessWord:String){
        userGuess = guessWord
    }

    ///정답 여부를 확인하는 함수
    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            _uiState.update { currentState ->
                //copy 함수로 객체를 복사하면, 일부 속성만 변경할수있음
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        // Reset user guess
        updateUserGuess("")
    }
    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //Last round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambledWord = pickRandomWordAndShuffle(),
                    currentWordCount = currentState.currentWordCount.inc(),
                    score = updatedScore
                )
            }
        }
    }
    fun skipWord() {
        updateGameState(_uiState.value.score)
        // Reset user guess
        updateUserGuess("")
    }

}