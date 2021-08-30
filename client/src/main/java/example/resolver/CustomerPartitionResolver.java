package example.resolver;

import org.apache.geode.cache.EntryOperation;
import org.apache.geode.cache.PartitionResolver;

public class CustomerPartitionResolver implements PartitionResolver<String,Object> {
  @Override
  public Object getRoutingObject(EntryOperation<String, Object> opDetails) {
    return opDetails.getKey().split("-")[0];
  }

  @Override
  public String getName() {
    return getClass().getName();
  }
}
