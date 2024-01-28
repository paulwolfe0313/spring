package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tacos.Ingredient.Type;
import tacos.data.IngredientRepository;

@SpringBootApplication
public class TacoCloudApplication {

  public static void main(String[] args) {
    SpringApplication.run(TacoCloudApplication.class, args);
  }

  @Bean
  public CommandLineRunner dataLoader(IngredientRepository repo) {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        repo.save(new Ingredient("MLK", "Milk Chocolate", Type.CHOCOLATE_BASE));
        repo.save(new Ingredient("DRK", "Dark Chocolate", Type.CHOCOLATE_BASE));
        repo.save(new Ingredient("ALMD", "Almonds", Type.NUTS));
        repo.save(new Ingredient("HAZL", "Hazelnuts", Type.NUTS));
        repo.save(new Ingredient("CHRY", "Cherries", Type.FRUIT));
        repo.save(new Ingredient("STWB", "Strawberries", Type.FRUIT));
        repo.save(new Ingredient("CRML", "Caramel", Type.FILLING));
        repo.save(new Ingredient("RSPB", "Raspberry Jam", Type.FILLING));
        repo.save(new Ingredient("SLTS", "Sea Salt", Type.TOPPING));
        repo.save(new Ingredient("SPKL", "Sparkles", Type.TOPPING));
      }
    };
  }
  
}
