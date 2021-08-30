package example.client.repository;

import example.domain.Customer;
import example.domain.Order;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.gemfire.repository.GemfireRepository;

@Region("Order")
public interface OrderRepository extends GemfireRepository<Order, String> {
}