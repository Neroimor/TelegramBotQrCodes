package com.neroimor;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.neroimor.message.SenderTgMessage;
import com.neroimor.settings.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


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
        else if (update.hasMessage() && update.getMessage().hasPhoto()){
            List<PhotoSize> photos = update.getMessage().getPhoto();
            PhotoSize photo = photos.get(photos.size() - 1);  // Выбираем самое большое фото

            // Получаем fileId
            String fileId = photo.getFileId();

            try {
                // Получаем информацию о файле
                GetFile getFile = new GetFile();
                getFile.setFileId(fileId);

                // Запрос на получение файла
                File file = execute(getFile);

                // Скачиваем файл по ссылке
                String filePath = file.getFilePath();
                URL fileUrl = new URL("https://api.telegram.org/file/bot" + getBotToken() + "/" + filePath);
                InputStream inputStream = fileUrl.openStream();

                // Конвертируем InputStream в изображение
                Image image = ImageIO.read(inputStream);

                execute(new SenderTgMessage().sendMessageDecoder(update, image));

            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            } catch (WriterException e) {
                throw new RuntimeException(e);
            } catch (ChecksumException e) {
                throw new RuntimeException(e);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            } catch (FormatException e) {
                throw new RuntimeException(e);
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
