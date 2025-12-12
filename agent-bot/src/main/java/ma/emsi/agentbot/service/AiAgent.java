package ma.emsi.agentbot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.mcp.client.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class AiAgent {

    private final ChatClient chatClient;

    public AiAgent(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .defaultFunctions(toolCallbackProvider)
                .build();
    }

    public String chat(String userQuery) {
        try {
            return chatClient.prompt()
                    .user(userQuery)
                    .call()
                    .content();
        } catch (Exception e) {
            return "Sorry, I encountered an error: " + e.getMessage();
        }
    }
}
