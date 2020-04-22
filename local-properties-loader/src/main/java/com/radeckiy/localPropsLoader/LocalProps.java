package com.radeckiy.localPropsLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Загрузчик Properties из файлов.
 * @author - Radeckiy Andrey
 */
public class LocalProps {
    // 'Локальный контекст' Properties
    private Properties localProps = new Properties();

    public LocalProps() {
        loadAllProps();
    }

    /**
     * Загрузить properties из файла и запись в переменную localProps.
     * Если не существует - IOException.
     * @param patch - путь до файла.
     */
    private void loadProps(String patch) {
        try {
            localProps.load(new InputStreamReader(new FileInputStream(patch), "cp1251"));
        } catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Загрузка всех существующих файлов properties из enum'а PropertiesType.
     */
    private void loadAllProps() {
        for(PropsType propsType : PropsType.values())
            if(propsType.getPatch() != null)
                loadProps(propsType.getPatch());
    }

    /**
     * получение свойства из переменной localProps.
     * @param key - ключ свойства.
     * @return - значение свойства (если не существует - NULL!).
     */
    public String getProperty(String key) {
        return localProps.getProperty(key);
    }
}
