package example.client.service;

import example.client.function.GetBucketIdsResultCollector;
import example.client.repository.CustomerRepository;
import example.client.repository.OrderRepository;
import example.client.function.GetBucketIdsFunctionInvoker;
import example.domain.AllBucketIds;
import example.domain.Customer;
import example.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private GetBucketIdsFunctionInvoker getBucketIdsFunctionInvoker;

  @Autowired
  private GetBucketIdsResultCollector getBucketIdsResultCollector;

  private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

  public void loadCustomersAndOrders(int numEntries) {
    logger.info("Putting {} customers", numEntries);
    for (int i=0; i<numEntries; i++) {
      Customer customer = new Customer(String.valueOf(i));
      this.customerRepository.save(customer);
      logger.info("Saved {}", customer);
      for (int j=0; j<5; j++) {
        Order order = new Order(customer.getId() + "-" + j);
        this.orderRepository.save(order);
        logger.info("Saved {}", order);
      }
    }
  }

  public void logBuckets(String regionName) {
    AllBucketIds result = this.getBucketIdsFunctionInvoker.getBucketIds(regionName);
    logger.info("\n{}", result.getDisplayString());

    // Since the ResultCollector is reused, the results need to be cleared for the next execution.
    this.getBucketIdsResultCollector.clearResults();
  }
}
