package example.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

@Data
@Region("Customer")
public class Customer {

  @Id
  @NonNull
  private final String id;
}
