package com.neroimor.message;

import com.google.zxing.WriterException;
import com.neroimor.Generator.GenerateQrCode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.HashMap;

public class SenderTgMessage {
    HashMap <String, String> text_map;
    public SenderTgMessage() {
        text_map = new HashMap();
        text_map.put("/start", "Привет, я твой бот для генерации qr кодов!!!\n" +
                "Просто пришли мне текст и я преобразую его в qr код");
        text_map.put("hello", "Привет");
    }

    public SendMessage sendMassage(Update update){
        String chatId = update.getMessage().getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text_map.get("/start"));
        return sendMessage;
    }
    public SendPhoto sendMassageQrCode(Update update) throws WriterException, IOException {
        String chatId = update.getMessage().getChatId().toString();
        String data = update.getMessage().getText();
        SendPhoto send = new SendPhoto();
        send.setChatId(chatId);
        send.setPhoto(new GenerateQrCode().generateQR(data));
        return send;
    }
}
