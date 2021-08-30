package example.client.repository;

import example.domain.Customer;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.data.gemfire.repository.GemfireRepository;

@Region("Customer")
public interface CustomerRepository extends GemfireRepository<Customer, String> {
}