package com.example.chatai.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatai.data.api.Message
import com.example.chatai.domain.model.ChatMessage
import com.example.chatai.domain.model.Role
import com.example.chatai.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    fun sendMessage(text: String) {
        // Запрос к API
        viewModelScope.launch {
            _chatState.value = _chatState.value.copy(
                inputText = "",
                messages = _chatState.value.messages + ChatMessage( Role.USER, text)
            )

            val reply = chatRepository.sendMessage(
                _chatState.value.messages + listOf(
                    ChatMessage(
                        Role.USER,
                        text
                    )
                )
            )
            _chatState.value = _chatState.value.copy(
                messages = _chatState.value.messages + reply
            )
        }
    }

    fun updateInputText(text: String) {
        _chatState.value = _chatState.value.copy(inputText = text)
    }
}

internal data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
)