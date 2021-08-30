package example.server.function;

import example.domain.ServerBucketIds;
import org.apache.geode.cache.Declarable;

import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionContext;

import org.apache.geode.internal.cache.PartitionedRegion;

import org.apache.geode.distributed.internal.InternalDistributedSystem;

import java.util.ArrayList;
import java.util.List;

public class GetBucketIdsFunction implements Function<Object[]>, Declarable {
  
  public void execute(FunctionContext<Object[]> context) {
    // Get the region name
    Object[] arguments = context.getArguments();
    String regionName = (String) arguments[0];
    context.getCache().getLogger().info("GetBucketIdsFunction getting bucket ids for regionName=" + regionName);
    
    // Get the region
    PartitionedRegion region = (PartitionedRegion) context.getCache().getRegion(regionName);
    
    // Create the data structure
    String redundancyZone = ((InternalDistributedSystem) context.getCache().getDistributedSystem()).getConfig().getRedundancyZone();
    int configuredNumberOfBuckets = region.getTotalNumberOfBuckets();
    List<Integer> localBucketIds = new ArrayList<>(region.getDataStore().getAllLocalBucketIds());
    ServerBucketIds ids = new ServerBucketIds(regionName, redundancyZone, configuredNumberOfBuckets, localBucketIds);

    // Return result
    context.getResultSender().lastResult(ids);
  }

  public String getId() {
    return getClass().getSimpleName();
  }
}
