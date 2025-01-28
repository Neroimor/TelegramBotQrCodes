package com.neroimor.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private String token;
    private String name;

    public Config(){
        Properties properties = new Properties();
        try (FileInputStream fileProperties = new FileInputStream("src/main/resources/application.yml")) {
            properties.load(fileProperties);
            this.token = properties.getProperty("token");
            this.name = properties.getProperty("name");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Ошибка загрузки конфигурации");
        }
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }
}
