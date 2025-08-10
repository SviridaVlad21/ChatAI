package com.example.chatai.domain

import com.example.chatai.data.api.Message
import com.example.chatai.data.repository.ChatRepositoryImpl.Companion.SYSTEM
import com.example.chatai.domain.model.ChatMessage
import com.example.chatai.domain.model.Role
import javax.inject.Inject

internal class ChatMapper @Inject constructor() {
    fun chatMessageToMessage(chatMessage: ChatMessage) = Message(role = chatMessage.role.name.lowercase(), content = chatMessage.content)
    fun messageToChatMessage(message: Message) = ChatMessage(role = if(message.role == SYSTEM) Role.SYSTEM else Role.USER, content = message.content)
}