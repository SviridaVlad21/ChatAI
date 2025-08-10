# ChatAI - Android Chat Application

A Kotlin Android application built with Jetpack Compose, Retrofit, Dagger 2, and Clean Architecture that provides a chat interface with DeepSeek's AI model.

## Features

- Clean Architecture with separate data, domain, and presentation layers
- Jetpack Compose UI with Material 3 design
- Retrofit for API communication with DeepSeek
- Dagger 2 for dependency injection
- Real-time chat with DeepSeek AI model
- Scrollable chat history
- Loading states and error handling

## Setup Instructions

1. **Clone the repository**
2. **Add your DeepSeek API key**:
   - Open `gradle.properties`
   - Replace `YOUR_OPENAI_API_KEY_HERE` with your actual DeepSeek API key
   - Or set it as a project property: `./gradlew -POPENAI_API_KEY=your_key_here`

3. **Build and run** the application

## Architecture

### Domain Layer
- `ChatMessage`: Data model for chat messages
- `ChatRepository`: Interface for chat operations
- `SendMessageUseCase`: Business logic for sending messages

### Data Layer
- `OpenAIApi`: Retrofit interface for DeepSeek API
- `ChatRepositoryImpl`: Implementation of chat repository
- Network configuration with Dagger 2

### Presentation Layer
- `ChatViewModel`: Manages chat state and user interactions
- `ChatScreen`: Compose UI for the chat interface
- `MainActivity`: Entry point with Hilt integration

## Dependencies

- **Jetpack Compose**: Modern UI toolkit
- **Retrofit**: HTTP client for API calls
- **Dagger 2**: Dependency injection
- **Coroutines**: Asynchronous programming
- **Material 3**: Design system

## API Configuration

The app uses DeepSeek's chat completions API:
- Endpoint: `https://api.deepseek.com/v1/chat/completions`
- Model: `deepseek-chat`
- Authentication: Bearer token via API key

## Usage

1. Type your message in the input field
2. Press "Send" to submit
3. The message will be sent to DeepSeek and the response will appear in the chat
4. Chat history is maintained during the session

## Error Handling

- Network errors are displayed in the chat
- API key validation on app startup
- Graceful fallbacks for failed requests 