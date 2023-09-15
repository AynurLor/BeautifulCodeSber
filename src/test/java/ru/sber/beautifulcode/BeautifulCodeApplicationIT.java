package ru.sber.beautifulcode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class BeautifulCodeApplicationIT {

  @Autowired MockMvc mockMvc;

  @Test
  public void checkerBracket_IsBracketPlacementCorrect_ReturnsValidResponseEntity()
      throws Exception {

    String requestBody =
        """
        {
        "text" : "Вчера я отправился в поход в лес (это мое любимое место для отдыха)\
         вместе с друзьями. Мы выбрали маршрут, который проходил через\
         горные потоки и поля (для разнообразия). В начале пути погода была\
         отличной, солнце светило ярко, и птицы радостно пели. Однако, когда\
         мы подошли ближе к вершине горы, небо стало покрываться облаками,\
         (как будто природа готовила нам небольшой сюрприз). Несмотря на это,\
         виды были захватывающими, особенно когда мы достигли высшей точки и\
         увидели прекрасный вид на долину (я почувствовал, что все усилия стоили того)."\
         }
         """;

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/checkBrackets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.isCorrect").value(true));
  }

  @Test
  public void checkerBracket_IsBracketPlacementIncorrect_ReturnsInvalidResponseEntity()
      throws Exception {

    String requestBody =
        """
        {
        "text": "Некорректный текст для проверки (аывавыа]"
        }
        """;

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/checkBrackets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.isCorrect").value(false));
  }

  @Test
  public void checkerBracket_IsNestedBracketsPlacementCorrect_ReturnsValidResponseEntity()
      throws Exception {

    String requestBody =
        """
        {
        "text": "Верный (текст с (правильными (которые обязательно должны быть успешными)) скобками)."
        }
        """;

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/checkBrackets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.isCorrect").value(true));
  }

  @Test
  public void checkerBracket_IsNestedBracketsPlacementIncorrect_ReturnsInvalidResponseEntity()
      throws Exception {

    String requestBody =
        """
               {
               "text": "Неверный (текст с (неправильными скобками [которые не должны быть успешными)))."
               }
               """;

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/checkBrackets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.isCorrect").value(false));
  }

  @Test
  public void checkerBracket_IsEmptyValueInsideBrackets_ReturnsInvalidResponseEntity()
      throws Exception {

    String requestBody =
        """
               {
               "text": "Неверный (текст с пустыми()) скобками)."
               }
               """;

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/checkBrackets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.isCorrect").value(false));
  }

  @Test
  public void checkerBracket_ValidTextWithBrackets_ReturnsValidResponseEntity() throws Exception {

    String requestBody =
        """
               {\
               "text": "\
                   Для обработки исключений в Java используются ключевые слова try, catch и finally.\
                   Конструкция try-catch позволяет перехватывать и обрабатывать исключения,\
                   которые могут возникнуть в блоке кода. Пример использования:\
                   try {\
                       int result = divide(10, 0);\
                   } catch (ArithmeticException e) {\
                       System.err.println(\\"Деление на ноль!\\");\
                   }\
               "\
               }\
               """;

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/checkBrackets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.isCorrect").value(true));
  }

  @Test
  public void checkerBracket_LongTextWithBrackets_ReturnsValidResponseEntity() throws Exception {
    String requestBody =
        """
            "Вчера я отправился в поход в лес (это мое любимое место для отдыха)"
                вместе с друзьями. Мы выбрали маршрут, который проходил через
                горные потоки и поля (для разнообразия). В начале пути погода была
                отличной, солнце светило ярко, и птицы радостно пели. Однако, когда
                мы подошли ближе к вершине горы, небо стало покрываться облаками,
                (как будто природа готовила нам небольшой сюрприз). Несмотря на это,
                виды были захватывающими, особенно когда мы достигли высшей точки и
                увидели прекрасный вид на долину (я почувствовал, что все усилия стоили того).
            """;

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/checkBrackets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk()) // Ожидаем HTTP-статус 200 OK
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.isCorrect").value(true)); // Проверяем значение поля "isCorrect"
  }

  @Test
  public void checkerBracket_EmptyText_ReturnsInvalidResponseEntity() throws Exception {
    String requestBody =
        """
               {
               "text": ""
               }
               """;

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/checkBrackets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.isCorrect").value(false));
  }

  @Test
  public void checkerBracket_EmptyText_ReturnsBadRequest() throws Exception {
    String requestBody = "";

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/checkBrackets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isBadRequest());
  }
}
