package example.client.function;

import example.domain.AllBucketIds;
import example.domain.ServerBucketIds;
import org.apache.geode.cache.execute.FunctionException;
import org.apache.geode.cache.execute.ResultCollector;
import org.apache.geode.distributed.DistributedMember;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("getBucketIdsResultCollector")
public class GetBucketIdsResultCollector implements ResultCollector<ServerBucketIds, AllBucketIds> {

  private AllBucketIds allBucketIds;

  public GetBucketIdsResultCollector() {
    clearResults();
  }

  @Override
  public AllBucketIds getResult() throws FunctionException {
    return this.allBucketIds;
  }

  @Override
  public AllBucketIds getResult(long timeout, TimeUnit unit) throws FunctionException {
    return getResult();
  }

  public void addResult(DistributedMember server, ServerBucketIds serverBucketIds) {
    this.allBucketIds.process(server.getName(), serverBucketIds);
  }

  @Override
  public void endResults() {
  }

  @Override
  public void clearResults() {
    this.allBucketIds = new AllBucketIds();
  }
}
