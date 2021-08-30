package example.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

@Data
@Region("Order")
public class Order {

  @Id
  @NonNull
  private final String id;
}
