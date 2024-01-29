package tacos.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tacos.ChocolateBarOrder;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.ChocolateBarOrder;

@DataJpaTest
public class OrderRepositoryTests {

  @Autowired
  OrderRepository orderRepo;
  
  @Test
  public void saveOrderWithTwoTacos() {
    ChocolateBarOrder order = new ChocolateBarOrder();
    order.setDeliveryName("Test McTest");
    order.setDeliveryStreet("1234 Test Lane");
    order.setDeliveryCity("Testville");
    order.setDeliveryState("CO");
    order.setDeliveryZip("80123");
    order.setCcNumber("4111111111111111");
    order.setCcExpiration("10/23");
    order.setCcCVV("123");
    Taco taco1 = new Taco();
    taco1.setName("Chocolate Bar One");
    taco1.addIngredient(new Ingredient("MLK", "Milk Chocolate", Type.CHOCOLATE_BASE));
    taco1.addIngredient(new Ingredient("ALMD", "Almonds", Type.NUTS));
    taco1.addIngredient(new Ingredient("CRML", "Caramel", Type.FILLING));
    order.addTaco(taco1);
    Taco taco2 = new Taco();
    taco2.setName("Chocolate Bar Two");
   taco1.addIngredient(new Ingredient("DRK", "Dark Chocolate", Type.CHOCOLATE_BASE));
    taco1.addIngredient(new Ingredient("HZL", "Hazelnuts", Type.NUTS));
    taco1.addIngredient(new Ingredient("CRML", "Caramel", Type.FILLING));
    order.addTaco(taco2);
    
    ChocolateBarOrder savedOrder = orderRepo.save(order);
    assertThat(savedOrder.getId()).isNotNull();
        
    ChocolateBarOrder fetchedOrder = orderRepo.findById(savedOrder.getId()).get();
    assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Test McTest");
    assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("1234 Test Lane");
    assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("Testville");
    assertThat(fetchedOrder.getDeliveryState()).isEqualTo("CO");
    assertThat(fetchedOrder.getDeliveryZip()).isEqualTo("80123");
    assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
    assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/23");
    assertThat(fetchedOrder.getCcCVV()).isEqualTo("123");
    assertThat(fetchedOrder.getPlacedAt().getTime()).isEqualTo(savedOrder.getPlacedAt().getTime());
    List<Taco> tacos = fetchedOrder.getTacos();
    assertThat(tacos.size()).isEqualTo(2);
    assertThat(tacos).containsExactlyInAnyOrder(taco1, taco2);
  }
  
}
