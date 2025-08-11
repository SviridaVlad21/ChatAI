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
        const val DEFAULT_CONDITION = "You are a helpful assistant for a chat application.\n" +
                "When responding to the user, always follow this exact format:\n" +
                "First, provide your full, natural language answer.\n" +
                "On a new line after the answer, output a JSON object enclosed in triple backticks (```json ... ```).\n" +
                "The JSON must contain the key pieces of structured information extracted from your answer, using clear keys and values. You decide which objects and fields are most relevant based on the context of the answer.\n" +
                "Do not include any additional explanations or text outside of the two parts above.\n" +
                "Example:\n" +
                "User: \"Tell me about Paris\"\n" +
                "Assistant:\n" +
                "Paris is the capital of France, known for its art, architecture, and the Eiffel Tower.\n" +
                "{\n" +
                "  \"city\": \"Paris\",\n" +
                "  \"country\": \"France\",\n" +
                "  \"famous_for\": [" +
                "       \"art\", " +
                "       \"architecture\", " +
                "       \"Eiffel Tower\"" +
                "]\n" +
                "}\n" +
                "Follow this exact structure for all future responses."
    }
}