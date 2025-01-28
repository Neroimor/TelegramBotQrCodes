package com.neroimor.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TextMessage {
    HashMap <String, String> text_map;
    public TextMessage() {
        text_map = new HashMap();
        text_map.put("/start", "Привет, я твой бот для генерации qr кодов!!!");
        text_map.put("hello", "Привет");
    }

    public SendMessage sendMassage(Update update){
        String chatId = update.getMessage().getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text_map.get("/start"));
        return sendMessage;
    }
    public SendMessage sendMassageDefault(Update update){
        String chatId = update.getMessage().getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text_map.get("hello"));
        return sendMessage;
    }
}
