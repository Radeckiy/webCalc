package com.radeckiy.localPropsLoader;

import java.net.URL;

// Возможные типы Properties
public enum PropsType {
    MESSAGES("messages.properties"),
    CONFIG("config.properties"),
    APP("app.properties");

    private String fileName;

    PropsType(String fileName) {
        this.fileName = fileName;
    }

    public String getPatch() {
        URL resultPatch = Thread.currentThread().getContextClassLoader().getResource(fileName);

        if(resultPatch != null)
            return resultPatch.getFile();

        return null;
    }
}
