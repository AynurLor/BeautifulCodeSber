package ru.sber.beautifulcode.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sber.beautifulcode.model.Response;
import ru.sber.beautifulcode.model.Text;
import ru.sber.beautifulcode.service.BracketCheckerService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BracketCheckerServiceTest {

  @InjectMocks
  private BracketCheckerController controller;

  @Mock
  private BracketCheckerService checkerService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCheckerBracket_ValidInput_ReturnsTrueResponse() {
    // Arrange
    String correctText = "Some valid text with brackets ()";
    Text request = new Text(correctText);
    when(checkerService.isCorrect(correctText)).thenReturn(false);

    // Act
    Response response = controller.checkerBracket(request);

    // Assert
    assertNotNull(response);
    assertFalse(response.getIsCorrect());
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

    Text request = new Text(correctText);
    when(checkerService.isCorrect(correctText)).thenReturn(true);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertTrue(response.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsBracketPlacementIncorrect_ReturnsInvalidValue() {

    String incorrectText =
        """
                Некорректный текст для проверки (аывавыа]
                """;

    Text request = new Text(incorrectText);
    when(checkerService.isCorrect(incorrectText)).thenReturn(false);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertFalse(response.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsNestedBracketsPlacementCorrect_ReturnsValidValue() {

    String correctText =
        """
            Верный (текст с (правильными (которые обязательно должны быть успешными)) скобками).
            """;

    Text request = new Text(correctText);
    when(checkerService.isCorrect(correctText)).thenReturn(true);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertTrue(response.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsNestedBracketsPlacementIncorrect_ReturnsInvalidValue() {

    String incorrectText =
        """
            Неверный (текст с (неправильными скобками [которые не должны быть успешными))).
            """;
    Text request = new Text(incorrectText);
    when(checkerService.isCorrect(incorrectText)).thenReturn(false);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertFalse(response.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsEmptyValueInsideBrackets_ReturnsInvalidValue() {

    String incorrectText = """
            Верный (текст с пустыми(    )) скобками.
            """;

    Text request = new Text(incorrectText);
    when(checkerService.isCorrect(incorrectText)).thenReturn(false);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertFalse(response.getIsCorrect());
  }

  @Test
  public void checkerBracket_ForFloatingPointNumber_ReturnsValidValue() {

    String incorrectText =
        """
            Верный (текст с пустыми(223.125121)) скобками).
            """;

    Text request = new Text(incorrectText);
    when(checkerService.isCorrect(incorrectText)).thenReturn(false);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertFalse(response.getIsCorrect());
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

    Text request = new Text(correctText);
    when(checkerService.isCorrect(correctText)).thenReturn(true);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertTrue(response.getIsCorrect());
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

    Text request = new Text(correctText);
    when(checkerService.isCorrect(correctText)).thenReturn(true);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertTrue(response.getIsCorrect());
  }

  @Test
  public void checkerBracket_NestedBracketsText_ReturnsValidValue() {

    String correctText =
        """
                (((Вложенные скобки (ещё вложенные скобки))))
                """;

    Text request = new Text(correctText);
    when(checkerService.isCorrect(correctText)).thenReturn(true);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertTrue(response.getIsCorrect());
  }

  @Test
  public void checkerBracket_IsEmptyText_ReturnsInvalidValue() {
    String incorrectText = "";

    var value = checkerService.isCorrect(incorrectText);


    assertFalse(value);
  }

  @Test
  public void checkerBracket_IsNullableText_ReturnsInvalidValue() {
    String incorrectText = null;

    Text request = new Text(incorrectText);
    when(checkerService.isCorrect(incorrectText)).thenReturn(false);

    Response response = controller.checkerBracket(request);

    assertNotNull(response);
    assertFalse(response.getIsCorrect());
  }

}
