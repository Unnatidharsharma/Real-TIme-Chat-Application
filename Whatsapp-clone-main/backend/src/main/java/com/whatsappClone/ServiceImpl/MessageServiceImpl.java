package com.whatsappClone.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.whatsappClone.Exception.ChatException;
import com.whatsappClone.Exception.MessageException;
import com.whatsappClone.Exception.UserException;
import com.whatsappClone.Model.Chat;
import com.whatsappClone.Model.Message;
import com.whatsappClone.Model.User;
import com.whatsappClone.Payload.SendMessageRequest;
import com.whatsappClone.Repository.MessageRepository;
import com.whatsappClone.Service.MessageService;
import com.whatsappClone.Service.TranslationService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ChatServiceImpl chatService;

     @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private TranslationService translationService;

    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
        User user = this.userService.findUserById(req.getUserId());
        Chat chat = this.chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);

        // Translate message content to recipient's language if not group chat
        String originalContent = req.getContent();
        String translatedContent = originalContent;

        if (!chat.isGroup()) {
            // For direct chat, get the other user's language preference
            User recipient = chat.getUsers().stream()
                .filter(u -> u.getId() != user.getId())
                .findFirst()
                .orElse(null);

            if (recipient != null && recipient.getLanguage() != null && !recipient.getLanguage().isEmpty()) {
                translatedContent = translationService.translateText(originalContent, recipient.getLanguage());
            }
        } else {
            // For group chat, optionally implement translation logic per user if needed
            // For now, keep original content
        }

        message.setContent(translatedContent);
        message.setTimestamp(LocalDateTime.now());

        message = this.messageRepository.save(message);

        // Send message to WebSocket topic based on chat type
        if (chat.isGroup()) {
            messagingTemplate.convertAndSend("/group/" + chat.getId(), message);
        } else {
            messagingTemplate.convertAndSend( "/user/" + chat.getId(), message);
        }

        return message;
    }

    @Override
    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {

        Chat chat = this.chatService.findChatById(chatId);

        if (!chat.getUsers().contains(reqUser)) {
            throw new UserException("You are not related to this chat");
        }

        List<Message> messages = this.messageRepository.findByChatId(chat.getId());

        return messages;

    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Message message = this.messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageException("The required message is not found"));
        return message;
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException {
        Message message = this.messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageException("The required message is not found"));

        if (message.getUser().getId() == reqUser.getId()) {
            this.messageRepository.delete(message);
        } else {
            throw new MessageException("You are not authorized for this task");
        }
    }

}
