package ru.sber.beautifulcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной клсасс BeautifulCodeApplication для запуска Spring Boot приложения. Это приложение
 * реализует REST API по адресу http://localhost:8080/api/checkBrackets, который принимает POST
 * запрос для проверки корректоности текста на скобки.
 *
 * @author Автор: Биктагиров Айнур
 * @version Версия 1.0
 */
@SpringBootApplication
public class BeautifulCodeApplication {

    /**
     * Метод main, который запускает приложение.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(BeautifulCodeApplication.class, args);
    }
}
