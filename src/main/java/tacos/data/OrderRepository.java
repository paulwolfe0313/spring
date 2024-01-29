package tacos.data;

import org.springframework.data.repository.CrudRepository;

import tacos.ChocolateBarOrder;

public interface OrderRepository 
         extends CrudRepository<ChocolateBarOrder, Long> {

}
