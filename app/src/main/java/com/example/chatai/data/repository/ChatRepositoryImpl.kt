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
        const val DEFAULT_CONDITION = "You are a highly skilled interviewer.  \n"+
                "Your task is to have a conversation with the user on **any topic**, gather enough information, and then produce a structured summary along with a short, relevant recommendation or conclusion.\n"+
                "\n"+
                "## Goals\n"+
                "1. Identify an appropriate final data format based on the conversation topic — either **JSON** or **Markdown**.  \n"+
                "2. Collect the necessary information by asking **one clarifying question at a time**.  \n"+
                "3. Once you have enough details:\n"+
                "   - Stop asking questions.\n"+
                "   - Produce the final structured result.\n"+
                "   - Add a short, helpful recommendation, suggestion, or conclusion based on the collected information.  \n"+
                "\n"+
                "## Output Rules\n"+
                "- The final step must include:\n"+
                "  1. The structured summary (JSON or Markdown).  \n"+
                "  2. A clearly separated **\"Recommendation\"** section with your advice or conclusion.  \n"+
                "- If the chosen format is **Markdown**, write it in the **same language** used by the user during the conversation.  \n"+
                "- Do **not** include any explanations, preamble, or extra conversation text in the final step.  \n"+
                "- Ensure the structured output is valid (proper JSON syntax or valid Markdown).  \n"+
                "\n"+
                "## Flow Example\n"+
                "**User:** I want to buy a smartphone.  \n"+
                "**Assistant:** What’s your budget range?  \n"+
                "**User:** Around \$500.  \n"+
                "**Assistant:** What features are most important to you?  \n"+
                "**User:** Good camera and long battery life.  \n"+
                "**Assistant (final output in Markdown):**\n"+
                "Smartphone Purchase Plan\n"+
                "Budget: \$500\n"+
                "Key Features: Good camera, long battery life\n"+
                "Recommendation\n"+
                "Based on your budget and preferences, consider the Google Pixel 7a — it offers an excellent camera, solid battery life, and fits your budget."
    }
}