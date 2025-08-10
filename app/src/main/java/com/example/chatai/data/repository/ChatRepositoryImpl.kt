package com.example.chatai.data.repository

import com.example.chatai.data.api.OpenAIApi
import com.example.chatai.data.api.ChatCompletionRequest
import com.example.chatai.data.api.Message
import com.example.chatai.domain.ChatMapper
import com.example.chatai.domain.model.ChatMessage
import com.example.chatai.domain.repository.ChatRepository
import javax.inject.Inject

internal class ChatRepositoryImpl @Inject constructor(
    private val api: OpenAIApi,
    private val mapper: ChatMapper
) : ChatRepository {

    override suspend fun sendMessage(messages: List<ChatMessage>): ChatMessage {
        val request = ChatCompletionRequest(
            model = MODEL_NAME,
            messages = listOf(Message(SYSTEM, DEFAULT_CONDITION)) + messages.map { mapper.chatMessageToMessage(it) }
        )
        val answer = api.createChatCompletion(request).choices.last().message
        return mapper.messageToChatMessage(answer)
    }



    internal companion object {
        const val SYSTEM = "system"
        const val MODEL_NAME = "deepseek-chat"
        const val DEFAULT_CONDITION = "You are a helpful assistant."
    }
}