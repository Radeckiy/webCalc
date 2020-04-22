package com.radeckiy.calc.client;

import com.radeckiy.localPropsLoader.LocalProps;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        LocalProps localProps = new LocalProps();
        final int CONNECTION_TIMEOUT = 30000;

        // Вывод вводной информации
        System.out.println(localProps.getProperty("client.info"));

        // Формирование URL
        final URL url = new URL(localProps.getProperty("client.host") + ":" + localProps.getProperty("client.port") + "/");

        try {
            // Необходимые переменные;
            HttpURLConnection con;
            PrintWriter out;
            BufferedReader in;
            StringBuilder responseBody, requestBody;

            // Для считывания введенной пользовательм информации
            Scanner scanner = new Scanner(System.in);
            String expression;

            while(true) {
                // Создание и настройка подключения
                con = (HttpURLConnection) url.openConnection();
                // Установка метода отправки
                con.setRequestMethod("POST");
                // Установка свойства header'а
                con.setRequestProperty("Content-Type", "application/json");
                // Установка таймаута на чтение ответа
                con.setReadTimeout(CONNECTION_TIMEOUT);
                // Устанавливаем флаг того, что мы собираемся использовать con для отправки
                con.setDoOutput(true);

                // Конектимся
                con.connect();

                // Получаем выражение от юзера
                System.out.print("Введите выражение: ");
                expression = scanner.nextLine();

                if((expression).equals("exit"))
                    break;

                // Получение потоков ввода
                out = new PrintWriter(con.getOutputStream());

                // Формирование JSON объекта для запроса
                requestBody = new StringBuilder()
                        .append("{")
                        .append("\"method\":\"calc\",")
                        .append("\"params\":\"").append(expression).append("\"")
                        .append("}");

                System.out.println("Сформированный JSON: " + requestBody.toString());

                // Отправляем тело запроса
                out.println(requestBody.toString());
                // Закрываем потом вывода
                out.close();

                // Получение потоков вывода
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                // Получаем и выводим ответ
                responseBody = new StringBuilder();
                while (in.ready())
                    responseBody.append(in.readLine());
                System.out.println(expression + " = " + responseBody.append("\n").toString());

                // Закрываем поток вывода и само подключение
                in.close();
                con.disconnect();
            }

            // Закрываем поток сканера
            scanner.close();

        } catch (ConnectException ex) {
            System.err.println("Сервер недоступен!");
        } catch (SocketTimeoutException ex) {
            System.err.println("Не удалось дождаться ответа от сервера!");
        }

        System.out.println(localProps.getProperty("client.bye"));
    }
}
