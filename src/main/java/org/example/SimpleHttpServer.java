package org.example;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        // Создание HTTP-сервера на порту 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Добавление обработчика запросов для "/"
        server.createContext("/", new MyHandler());

        // Добавление обработчика запросов для "/saveData"
        server.createContext("/saveData", new SaveDataHandler());

        // Запуск сервера
        server.start();
        System.out.println("Server started on port 8000");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestURI = exchange.getRequestURI().toString();
            if (requestURI.equals("/") || requestURI.equals("/index.html")) {
                // Загрузка и отображение файла filename.html
                String filename = "src/main/resources/static/index.html";
                byte[] response = Files.readAllBytes(Paths.get(filename));

                // Установка кодировки
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");

                exchange.sendResponseHeaders(200, response.length);
                exchange.getResponseBody().write(response);
            } else {
                // Обработка других запросов (если необходимо)
                exchange.sendResponseHeaders(404, 0);
                exchange.getResponseBody().close();
            }
        }
    }

    static class SaveDataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                // Получение данных из тела запроса
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String requestBody = br.readLine();

                // Парсинг JSON из тела запроса
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(requestBody, JsonObject.class);
                int number1 = jsonObject.get("number1").getAsInt();
                int number2 = jsonObject.get("number2").getAsInt();
                String operation = jsonObject.get("operation").getAsString();
                double result = jsonObject.get("result").getAsDouble();

                // Сохранение данных в базе данных
                CalculatorDataHandler.saveData(number1, number2, operation, result);

                // Отправка ответа клиенту
                String response = "Data saved successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                // Если метод запроса не POST, возвращаем ошибку метода
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}

