package example.client.function;

import example.domain.AllBucketIds;
import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnServers;

@OnServers(resultCollector = "getBucketIdsResultCollector")
public interface GetBucketIdsFunctionInvoker {

  @FunctionId("GetBucketIdsFunction")
  AllBucketIds getBucketIds(String regionName);
}
