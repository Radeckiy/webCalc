package com.radeckiy.calc.server;

import com.google.gson.Gson;
import com.radeckiy.calc.server.models.rpc.RPCrequest;
import com.radeckiy.localPropsLoader.LocalProps;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;

public class Main {
    private static LocalProps localProps = new LocalProps();

    // Главная точка запуска программы
    public static void main(String[] args) {
        HttpServer rpcServer = null;

        try {
            rpcServer = getRPCserver(CalcMain.class, "/", Integer.parseInt(localProps.getProperty("server.port")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(rpcServer != null) {
            rpcServer.start();
            System.out.println(localProps.getProperty("server.start") + "\n");
        } else
            System.err.println(localProps.getProperty("server.start.error"));
    }

    //todo: старое - удалить
    private static String getExpressionFromRequestBody(InputStream requestBody) throws IOException {
        final String EXPRESSION_WORD = "\"expression\":";
        InputStreamReader inReqBody = new InputStreamReader(requestBody);
        StringBuilder reqBody = new StringBuilder();

        while (inReqBody.ready()) {
            char c = (char) inReqBody.read();
            if (c != ' ')
                reqBody.append(c);
        }

        int startPoint = reqBody.indexOf("\"", reqBody.indexOf(EXPRESSION_WORD) + EXPRESSION_WORD.length()) + 1;
        int endPoint = reqBody.indexOf("\"", startPoint);

        return reqBody.substring(startPoint, endPoint).trim();
    }

    /**
     * Функция парсит ответ из строки JSON в виде RPCrequest объекта
     * @param requestBody - поток с ответом
     * @return - ответ в виде распарсенного RPCrequest объекта
     * @throws IOException
     */
    private static RPCrequest getRPCrequestFromJsonRequestBody(InputStream requestBody) throws IOException {
        Gson gson = new Gson();
        InputStreamReader inReqBody = new InputStreamReader(requestBody);
        StringBuilder reqBody = new StringBuilder();

        while (inReqBody.ready()) {
            char c = (char) inReqBody.read();
            if (c != ' ')
                reqBody.append(c);
        }

        return gson.fromJson(reqBody.toString(), RPCrequest.class);
    }

    /**
     * Возвращает экземпляр настроенного под RPC HttpServer
     * @param rpcInvokeMethodsClass - класс со статическими методами, которые будут вызыватся посредством RPC
     * @param endpointUrl - урл самого сервера
     * @param port - порт
     * @return - экземпляр настроенного под RPC HttpServer
     * @throws IOException
     */
    private static HttpServer getRPCserver(Class rpcInvokeMethodsClass, String endpointUrl, Integer port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        Invoker invoker = new Invoker(rpcInvokeMethodsClass, System.err);


        server.createContext(endpointUrl, (exchange -> {
            if("POST".equals(exchange.getRequestMethod())) {
                RPCrequest rpcRequest = getRPCrequestFromJsonRequestBody(exchange.getRequestBody());
                String res;

                try {
                    res = invoker.invokeMethod(rpcRequest.getMethodName(), rpcRequest.getParams()).toString();
                } catch(NoSuchMethodException | IllegalAccessException ex) {
                    res = ex.getMessage().trim();
                } catch(InvocationTargetException ex) {
                    res = localProps.getProperty("server.runtime.error");
                }

                exchange.sendResponseHeaders(200, res.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(res.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
            exchange.close();
        }));

        return server;
    }
}
