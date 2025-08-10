package com.example.chatai.domain.repository

import com.example.chatai.domain.model.ChatMessage

internal interface ChatRepository {
    suspend fun sendMessage(messages: List<ChatMessage>): ChatMessage
}