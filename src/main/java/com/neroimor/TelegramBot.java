package com.neroimor;

import com.google.zxing.WriterException;
import com.neroimor.message.SenderTgMessage;
import com.neroimor.settings.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;


public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);
    private String token;
    private String botName;

    public TelegramBot() {
        var Config = new Config();
        token = Config.getToken();
        botName = Config.getName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();

            switch (message) {
                case "/start":
                    try {
                        sendMessage(update);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                        try {  
                        sendImage(update);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (WriterException e) {
                        throw new RuntimeException(e);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }

        }
    }

    public void sendMessage(Update update) throws TelegramApiException {
        execute(new SenderTgMessage().sendMassage(update));
    }

    public void sendImage(Update update) throws IOException, WriterException, TelegramApiException {
        execute(new SenderTgMessage().sendMassageQrCode(update));
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
