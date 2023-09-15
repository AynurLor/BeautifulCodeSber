package ru.sber.beautifulcode.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Класс Text представляет текст, получаемый с Post запроса. Он содержит текстовое значение и метод
 * для проверки, является ли текст пустым.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Text {
  private String text;

  public boolean isEmpty() {
    if (text == null) return false;
    return text.isEmpty();
  }
}
