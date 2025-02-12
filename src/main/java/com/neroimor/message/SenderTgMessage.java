package com.neroimor.message;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.neroimor.Generator.GenerateQrCode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SenderTgMessage {
    HashMap <String, String> text_map;
    public SenderTgMessage() {
        text_map = new HashMap();
        text_map.put("/start", "Привет, я твой бот для генерации QR кодов!!!\n" +
                "Просто пришли мне текст и я преобразую его в qr код или отправь QR код и я переведу его в текст.");
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

    public SendMessage sendMessageDecoder(Update update, Image QRCode) throws WriterException, IOException, ChecksumException, NotFoundException, FormatException {
        String chatId = update.getMessage().getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(new GenerateQrCode().recodingQrCode(QRCode));
        return sendMessage;
    }


}
