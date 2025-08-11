package com.example.chatai.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatai.R
import com.example.chatai.domain.model.Role

@Composable
internal fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {
    val chatState by viewModel.chatState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Chat history
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            reverseLayout = true
        ) {

            items(chatState.messages.reversed()) { message ->
                ChatMessageItem(content = message.content, isUser = message.role == Role.USER)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Input section
        ChatInput(
            value = chatState.inputText,
            onValueChange = { viewModel.updateInputText(it) },
            onSendClick = { viewModel.sendMessage(it) },
            isLoading = chatState.isLoading
        )
    }
}

@Composable
private fun ChatMessageItem(
    content: String,
    isUser: Boolean,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isUser) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Text(
                text = content,
                modifier = Modifier.padding(12.dp),
                color = if (isUser) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun ChatInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: (String) -> Unit,
    isLoading: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .systemBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text(stringResource(R.string.chat_screen_text_field_placeholder)) },
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = { onSendClick(value) },
            enabled = value.isNotBlank() && !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(stringResource(R.string.chat_screen_send_btn))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatScreenPreview() {
    MaterialTheme {
        ChatScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatMessageItemPreview() {
    MaterialTheme {
        Column {
            ChatMessageItem(content = "Hello, how are you?", isUser = true)
            Spacer(modifier = Modifier.height(8.dp))
            ChatMessageItem(
                content = "I'm doing well, thank you for asking! How can I help you today?",
                isUser = false
            )
        }
    }
}