package ru.sber.beautifulcode.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * Класс Response представляет ответ на валидность. Он содержит информацию о том, является ли
 * расстановка скобок корректной.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
  /** Флаг, указывающий на корректность расстановки скобок. true - корректно false = некорректно */
  private Boolean isCorrect;
}
