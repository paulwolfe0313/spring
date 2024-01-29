package tacos;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;
import tacos.data.OrderRepository;
import tacos.web.DesignTacoController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private List<Ingredient> ingredients;

  private Taco design;

  @MockBean
  private IngredientRepository ingredientRepository;

  @MockBean
  private OrderRepository orderRepository;

  @BeforeEach
  public void setup() {
    ingredients = Arrays.asList(
      new Ingredient("MLK", "Milk Chocolate", Type.CHOCOLATE_BASE),
      new Ingredient("DRK", "Dark Chocolate", Type.CHOCOLATE_BASE),
      new Ingredient("ALMD", "Almonds", Type.NUTS),
      new Ingredient("HAZL", "Hazelnuts", Type.NUTS),
      new Ingredient("CHRY", "Cherries", Type.FRUIT),
      new Ingredient("STWB", "Strawberries", Type.FRUIT),
      new Ingredient("CRML", "Caramel", Type.FILLING),
      new Ingredient("RSPB", "Raspberry Jam", Type.FILLING),
      new Ingredient("SLTS", "Sea Salt", Type.TOPPING),
      new Ingredient("SPKL", "Sparkles", Type.TOPPING)
    );

    when(ingredientRepository.findAll())
        .thenReturn(ingredients);

    when(ingredientRepository.findById("MLK")).thenReturn(Optional.of(new Ingredient("MLK", "Milk Chocolate", Type.CHOCOLATE_BASE)));
    when(ingredientRepository.findById("ALMD")).thenReturn(Optional.of(new Ingredient("ALMD", "Almonds", Type.NUTS)));
    when(ingredientRepository.findById("CRML")).thenReturn(Optional.of(new Ingredient("CRML", "Caramel", Type.FILLING)));
    design = new Taco();
    design.setName("Test Taco");

    design.setIngredients(
        Arrays.asList(
            new Ingredient("MLK", "Milk Chocolate", Type.CHOCOLATE_BASE),
            new Ingredient("ALMD", "Almonds", Type.NUTS),
            new Ingredient("CRML", "Caramel", Type.FILLING)));

  }

  @Test
  public void testShowDesignForm() throws Exception {
    mockMvc.perform(get("/design"))
        .andExpect(status().isOk())
        .andExpect(view().name("design"))
        .andExpect(model().attribute("chocolate_base", ingredients.subList(0, 2)))
        .andExpect(model().attribute("nuts", ingredients.subList(2, 4)))
        .andExpect(model().attribute("fruit", ingredients.subList(4, 6)))
        .andExpect(model().attribute("filling", ingredients.subList(6, 8)))
        .andExpect(model().attribute("toppping", ingredients.subList(8, 10)));
  }

  @Test
  public void processTaco() throws Exception {
    mockMvc.perform(post("/design")
        .content("name=Test+Taco&ingredients=MLK,ALMD,CRML")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().stringValues("Location", "/orders/current"));
  }

}
