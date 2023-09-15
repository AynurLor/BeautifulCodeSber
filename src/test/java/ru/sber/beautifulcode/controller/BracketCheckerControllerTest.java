package ru.sber.beautifulcode.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.sber.beautifulcode.model.Response;
import ru.sber.beautifulcode.model.Text;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BracketCheckerControllerTest {
  @InjectMocks private BracketCheckerController bracketCheckerService;

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

    Response res = bracketCheckerService.checkerBracket(new Text(correctText));

    assertNotNull(res);
    assertTrue(res.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsBracketPlacementIncorrect_ReturnsInvalidValue() {

    String inCorrectText =
        """
                Некорректный текст для проверки (аывавыа]
                """;

    Response res = bracketCheckerService.checkerBracket(new Text(inCorrectText));

    assertNotNull(res);
    assertFalse(res.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsNestedBracketsPlacementCorrect_ReturnsValidValue() {

    String correctText =
        """
            Верный (текст с (правильными (которые обязательно должны быть успешными)) скобками).
            """;

    Response res = bracketCheckerService.checkerBracket(new Text(correctText));

    assertNotNull(res);
    assertTrue(res.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsNestedBracketsPlacementIncorrect_ReturnsInvalidValue() {

    String inCorrectText =
        """
            Неверный (текст с (неправильными скобками [которые не должны быть успешными))).
            """;
    Response res = bracketCheckerService.checkerBracket(new Text(inCorrectText));

    assertNotNull(res);
    assertFalse(res.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsEmptyValueInsideBrackets_ReturnsInvalidValue() {

    String inCorrectText = """
            Верный (текст с пустыми(    )) скобками.
            """;

    Response res = bracketCheckerService.checkerBracket(new Text(inCorrectText));

    boolean inCorrectResult = res.getIsCorrect();

    assertNotNull(res);
    assertFalse(inCorrectResult);
  }

  @Test
  public void checkerBracket_ForFloatingPointNumber_ReturnsValidValue() {

    String inCorrectText =
        """
            Верный (текст с пустыми(223.125121)) скобками).
            """;

    Response res = bracketCheckerService.checkerBracket(new Text(inCorrectText));

    assertNotNull(res);
    assertFalse(res.getIsCorrect());
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

    Response res = bracketCheckerService.checkerBracket(new Text(correctText));

    assertNotNull(res);
    assertTrue(res.getIsCorrect());
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

    Response res = bracketCheckerService.checkerBracket(new Text(correctText));

    assertNotNull(res);
    assertTrue(res.getIsCorrect());
  }

  @Test
  public void checkerBracket_NestedBracketsText_ReturnsValidValue() {

    String correctText =
        """
                (((Вложенные скобки (ещё вложенные скобки))))
                """;

    Response res = bracketCheckerService.checkerBracket(new Text(correctText));

    assertNotNull(res);
    assertTrue(res.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsEmptyText_ReturnsInvalidValue() {
    String inCorrectText = "";

    Response res = bracketCheckerService.checkerBracket(new Text(inCorrectText));

    boolean inCorrectResult = res.getIsCorrect();

    assertFalse(inCorrectResult);
  }

  @Test
  public void checkerBracket_IsNullableText_ReturnsInvalidValue() {
    String correctText = null;

    Response res = bracketCheckerService.checkerBracket(new Text(correctText));

    boolean inCorrectResult = res.getIsCorrect();

    assertFalse(inCorrectResult);
  }

  @Test
  public void checkerBracket_IsNullableArgument_ReturnsInvalidValue() {

    Response res = bracketCheckerService.checkerBracket(null);

    boolean inCorrectResult = res.getIsCorrect();

    assertNotNull(res);
    assertFalse(inCorrectResult);
  }
}
