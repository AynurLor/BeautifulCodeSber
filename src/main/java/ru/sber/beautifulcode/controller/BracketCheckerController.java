package ru.sber.beautifulcode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sber.beautifulcode.model.Response;
import ru.sber.beautifulcode.model.Text;
import ru.sber.beautifulcode.service.BracketCheckerService;

/**
 * Класс BracketCheckerController представляет контроллер для проверки расстановки скобок в тексте.
 * Он обрабатывает HTTP-запросы и возвращает ответ о корректности расстановки скобок.
 */
@RestController
@RequestMapping("/api")
public class BracketCheckerController {

    /**
     * Сервис для проверки расстановки скобок.
     */
    private final BracketCheckerService checkerService;

    @Autowired
    public BracketCheckerController(BracketCheckerService checkerService) {
        this.checkerService = checkerService;
    }

    /**
     * Обработчик HTTP-запроса POST /api/checkBrackets. Проверяет корректность расстановки скобок в
     * тексте, переданном в теле запроса.
     *
     * @param request Текст для проверки расстановки скобок.
     * @return Ответ о корректности расстановки скобок.
     */
    @PostMapping("/checkBrackets")
    public @ResponseBody Response checkerBracket(@RequestBody Text request) {
        if (request == null || request.isEmpty()) {
            return new Response(false);
        }
        var validation = checkerService.isCorrect(request.getText());
        return new Response(validation);
    }
}
