package ru.sber.beautifulcode.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BracketCheckerServiceTest {

  @InjectMocks private BracketCheckerService checkerService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void checkerBracket_IsBracketPlacementCorrect_ReturnsValidValue() {

    String correctText =
        """
                    {
                     Вчера я отправился в поход в лес (это мое любимое место для отдыха)\
                     вместе с друзьями. Мы выбрали маршрут, который проходил через\
                     горные потоки и поля (для разнообразия). В начале пути погода была\
                     отличной, солнце светило ярко, и птицы радостно пели. Однако, когда\
                     мы подошли ближе к вершине горы, небо стало покрываться облаками,\
                     (как будто природа готовила нам небольшой сюрприз). Несмотря на это,\
                     виды были захватывающими, особенно когда мы достигли высшей точки и\
                     увидели прекрасный вид на долину (я почувствовал, что все усилия стоили того)."\
                     }
                     """;

    var value = checkerService.isCorrect(correctText);

    assertTrue(value);
  }

  @Test
  public void checkerBracket_IsBracketPlacementIncorrect_ReturnsInvalidValue() {

    String incorrectText =
        """
                        Некорректный текст для проверки (аывавыа]
                        """;

    var value = checkerService.isCorrect(incorrectText);

    assertFalse(value);
  }

  @Test
  public void checkerBracket_IsNestedBracketsPlacementCorrect_ReturnsValidValue() {

    String correctText =
        """
                    Верный (текст с (правильными (которые обязательно должны быть успешными)) скобками).
                    """;

    var value = checkerService.isCorrect(correctText);

    assertTrue(value);
  }

  @Test
  public void checkerBracket_IsNestedBracketsPlacementIncorrect_ReturnsInvalidValue() {

    String incorrectText =
        """
                    Неверный (текст с (неправильными скобками [которые не должны быть успешными))).
                    """;
    var value = checkerService.isCorrect(incorrectText);

    assertFalse(value);
  }

  @Test
  public void checkerBracket_IsEmptyValueInsideBrackets_ReturnsInvalidValue() {

    String incorrectText = """
            Верный (текст с пустыми(    )) скобками.
            """;

    var value = checkerService.isCorrect(incorrectText);

    assertFalse(value);
  }

  @Test
  public void checkerBracket_ForFloatingPointNumber_ReturnsValidValue() {

    String incorrectText =
        """
                    Верный (текст с пустыми(223.125121)) скобками).
                    """;

    var value = checkerService.isCorrect(incorrectText);

    assertFalse(value);
  }

  @Test
  public void checkerBracket_LongTextWithBrackets_ReturnsValidValue() {

    String correctText =
        """
                        Вчера я отправился в поход в лес (это мое любимое место для отдыха)
                        вместе с друзьями. Мы выбрали маршрут, который проходил через
                        горные потоки и поля (для разнообразия). В начале пути погода была
                        отличной, солнце светило ярко, и птицы радостно пели. Однако, когда
                        мы подошли ближе к вершине горы, небо стало покрываться облаками,
                        (как будто природа готовила нам небольшой сюрприз). Несмотря на это,
                        виды были захватывающими, особенно когда мы достигли высшей точки и
                        увидели прекрасный вид на долину (я почувствовал, что все усилия стоили того).
                        """;

    var value = checkerService.isCorrect(correctText);

    assertTrue(value);
  }

  @Test
  public void checkerBracket_LongTextWithBrackets2_ReturnsValidValue() {

    String correctText =
        """
                        На горизонте (где небо сливается с морем) сияло яркое солнце. Оно казалось
                        (даже через солнечные очки) ослепительным и невероятно красивым. Море,
                        распростершееся передо мной, представляло собой бескрайнюю гладь
                        (как будто бесконечное зеркало), и ветры (проносящиеся над ним)
                        приносили аромат соли и свободы.
                        """;

    var value = checkerService.isCorrect(correctText);

    assertTrue(value);
  }

  @Test
  public void checkerBracket_NestedBracketsText_ReturnsValidValue() {

    String correctText =
        """
                        (((Вложенные скобки (ещё вложенные скобки))))
                        """;

    var value = checkerService.isCorrect(correctText);

    assertTrue(value);
  }

  @Test
  public void checkerBracket_IsEmptyText_ReturnsInvalidValue() {
    String incorrectText = "";

    var value = checkerService.isCorrect(incorrectText);

    assertFalse(value);
  }

  @Test
  public void checkerBracket_IsNullableText_ReturnsInvalidValue() {
    String correctText = null;

    var value = checkerService.isCorrect(correctText);

    assertFalse(value);
  }

  @Test
  public void checkerBracket_IsNullableArgument_ReturnsInvalidValue() {

    var value = checkerService.isCorrect(null);

    assertFalse(value);
  }
}
