package com.example.chatai.domain.model

 internal data class ChatMessage(
    val role: Role,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

 internal enum class Role {
    USER,
    SYSTEM
}