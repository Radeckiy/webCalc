## WebCalc
Version: 1.0
## Описание
Строковый web калькулятор основанный на технологии RPC.
## О проекте
Язык программирования: **Java**

Сборщик: **Maven**

В проекта два основных модуля: 
- ***web-calc-server*** - сервер;
- ***web-calc-client*** - примитивный клиент для отправки запросов на сервер.

Также в проекте существует встроенная библиотека, которая отвечает за считывания properties файлов - ***local-properties-loader***.

Настройки **сервера/клиента** находятся по пути: `src/main/java/resources/config.properties`.

После запуска по адресу [localhost:8080](https://localhost:8080 "Клик ми") будет доступно само приложение.

! Сервер поддерживает запросы типа `POST`, другие типы не поддерживаются и сервер будет отдавать 405 ошибку.
В тело запроса следует включить вот такой JSON:

```json
{
  "method": "calc",
  "params": "(2+2)/4+2"
}
```
 В ответ сервер отправит строку с результатом вычисления выражения.
 ## 
 Клиент выглядит следующим образом:
 
 ![alt-текст](https://sun9-32.userapi.com/c857032/v857032276/16f0e3/pmN_5_6jFC4.jpg "Клиентское приложение")