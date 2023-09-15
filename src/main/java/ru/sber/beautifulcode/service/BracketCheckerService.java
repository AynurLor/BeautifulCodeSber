package ru.sber.beautifulcode.service;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Класс BracketCheckerService предоставляет реализацию интерфейса Validation для проверки
 * корректности расстановки скобок в тексте.
 *
 * @see Validation
 */
@Service
public class BracketCheckerService implements Validation<String> {
  /**
   * Проверяет корректность расстановки скобок в указанном текстовом контексте.
   *
   * @param context Текст, который будет проверен на корректность расстановки скобок.
   * @return true, если расстановка скобок в контексте корректна, в противном случае - false.
   */
  @Override
  public boolean isCorrect(String context) {

    if (context == null || context.isEmpty()) {
      return false;
    }
    Deque<Character> stack = new ArrayDeque<>();
    int openBracketCount = 0, closeBracketCount = 0;

    for (char c : context.toCharArray()) {

      switch (c) {
        case '(':
          openBracketCount++;
          stack.push(c);
          break;

        case ')':
          closeBracketCount++;
          if (stack.isEmpty() || stack.peek().equals('(')) {
            return false;
          }
          char symbol = stack.pop();
          while (symbol != '(' && stack.isEmpty()) {
            symbol = stack.pop();
          }

        default:
          if (c > ' ') {
            stack.push(c);
          }
          break;
      }
    }
    return openBracketCount == closeBracketCount;
  }
}
