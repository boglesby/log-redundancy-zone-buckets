package example.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.stream.IntStream;

public class AllBucketIds {

  private String regionName;

  private int configuredNumberOfBuckets;
  
  private final Map<String,String> redundancyZonesPerServer;
  
  private final Map<String,List<Integer>> allBucketIdsPerRedundancyZone;
  
  private final Map<String,List<Integer>> allBucketIdsPerServer;

  private final Map<String,List<Integer>> primaryBucketIdsPerRedundancyZone;

  private final Map<String,List<Integer>> primaryBucketIdsPerServer;

  private int totalNumberOfBucketIds;

  private int totalNumberOfPrimaryBucketIds;

  public AllBucketIds() {
    this.redundancyZonesPerServer = new HashMap<>();
    this.allBucketIdsPerRedundancyZone = new HashMap<>();
    this.allBucketIdsPerServer = new HashMap<>();
    this.primaryBucketIdsPerRedundancyZone = new HashMap<>();
    this.primaryBucketIdsPerServer = new HashMap<>();
    this.totalNumberOfBucketIds = 0;
    this.totalNumberOfPrimaryBucketIds = 0;
  }

  public void process(String server, ServerBucketIds serverBucketIds) {
    this.regionName = serverBucketIds.getRegionName();
    this.configuredNumberOfBuckets = serverBucketIds.getConfiguredNumberOfBuckets();
    this.redundancyZonesPerServer.put(server, serverBucketIds.getRedundancyZone());
    updateBucketsPerServer(server, serverBucketIds);
    updateBucketsPerRedundancyZone(serverBucketIds);
  }
  
  private void updateBucketsPerServer(String server, ServerBucketIds serverBucketIds) {
    // Sort and add all bucket ids
    Collections.sort(serverBucketIds.getAllBucketIds());
    this.allBucketIdsPerServer.put(server, serverBucketIds.getAllBucketIds());
    this.totalNumberOfBucketIds += serverBucketIds.getTotalNumberOfBucketIds();

    // Sort and add primary bucket ids
    Collections.sort(serverBucketIds.getPrimaryBucketIds());
    this.primaryBucketIdsPerServer.put(server, serverBucketIds.getPrimaryBucketIds());
    this.totalNumberOfPrimaryBucketIds += serverBucketIds.getTotalNumberOfPrimaryBucketIds();
  }
  
  private void updateBucketsPerRedundancyZone(ServerBucketIds serverBucketIds) {
    // Sort and add all bucket ids
    String redundancyZone = serverBucketIds.getRedundancyZone();
    List<Integer> redundancyZoneAllBucketIds = this.allBucketIdsPerRedundancyZone.computeIfAbsent(redundancyZone, k -> new ArrayList<>());
    redundancyZoneAllBucketIds.addAll(serverBucketIds.getAllBucketIds());
    Collections.sort(redundancyZoneAllBucketIds);

    // Sort and add primary bucket ids
    List<Integer> redundancyZonePrimaryBucketIds = this.primaryBucketIdsPerRedundancyZone.computeIfAbsent(redundancyZone, k -> new ArrayList<>());
    redundancyZonePrimaryBucketIds.addAll(serverBucketIds.getPrimaryBucketIds());
    Collections.sort(redundancyZonePrimaryBucketIds);
  }
  
  public String getDisplayString() {
    StringBuilder builder = new StringBuilder();
    builder
      .append("Region: ")
      .append(this.regionName)
      .append("\nConfigured Number of Buckets: ")
      .append(this.configuredNumberOfBuckets);
      
    // Add all bucket ids per server
    addAllServerBucketIds(builder);
    
    // Add primary bucket ids per server
    addPrimaryServerBucketIds(builder);
    
    // Add all bucket ids per redundancy zone
    addAllRedundancyZoneBucketIds(builder);
    
    // Add primary bucket ids per redundancy zone
    addPrimaryRedundancyZoneBucketIds(builder);
    
    // Add missing bucket ids in each redundancy zone
    addMissingBucketsInRedundancyZone(builder);
    
    // Add extra bucket ids in each redundancy zone
    addExtraBucketsInRedundancyZone(builder);
    
    return builder.toString();
  }
  
  private void addAllServerBucketIds(StringBuilder builder) {
    builder
      .append("\nThe ")
      .append(this.allBucketIdsPerServer.size())
      .append(" servers contain the following ")
      .append(this.totalNumberOfBucketIds)
      .append(" bucket ids:");
    this.allBucketIdsPerServer.forEach((key, value) -> builder
      .append("\n\t Server ")
      .append(key)
      .append(" in zone ")
      .append(this.redundancyZonesPerServer.get(key))
      .append(" contains ")
      .append(value.size())
      .append(" bucket ids: ")
      .append(value));
  }
  
  private void addPrimaryServerBucketIds(StringBuilder builder) {
    builder
      .append("\nThe ")
      .append(this.primaryBucketIdsPerServer.size())
      .append(" servers contain the following ")
      .append(this.totalNumberOfPrimaryBucketIds)
      .append(" primary bucket ids:");
    this.primaryBucketIdsPerServer.forEach((key, value) -> builder
      .append("\n\t Server ")
      .append(key)
      .append(" in zone ")
      .append(this.redundancyZonesPerServer.get(key))
      .append(" contains ")
      .append(value.size())
      .append(" primary bucket ids: ")
      .append(value));
  }
  
  private void addAllRedundancyZoneBucketIds(StringBuilder builder) {
    builder
      .append("\nThe ")
      .append(this.allBucketIdsPerRedundancyZone.size())
      .append(" redundancy zones contain the following bucket ids:");
    this.allBucketIdsPerRedundancyZone.forEach((key, value) -> builder
      .append("\n\tZone ")
      .append(key)
      .append(" contains ")
      .append(value.size())
      .append(" bucket ids: ")
      .append(value));
  }
  
  private void addPrimaryRedundancyZoneBucketIds(StringBuilder builder) {
    builder
      .append("\nThe ")
      .append(this.primaryBucketIdsPerRedundancyZone.size())
      .append(" redundancy zones contain the following primary bucket ids:");
    this.primaryBucketIdsPerRedundancyZone.forEach((key, value) -> builder
      .append("\n\tZone ")
      .append(key)
      .append(" contains ")
      .append(value.size())
      .append(" primary bucket ids: ")
      .append(value));
  }
  
  private void addMissingBucketsInRedundancyZone(StringBuilder builder) {
    Map<String,List<Integer>> missingBucketIdsPerRedundancyZone = new HashMap<>();
    this.allBucketIdsPerRedundancyZone.forEach((key, value) -> {
      List<Integer> missingBucketIds = missingBucketIdsPerRedundancyZone.computeIfAbsent(key, k -> new ArrayList<>());
      IntStream.range(0, this.configuredNumberOfBuckets).forEach(bucketId -> {
        if (!value.contains(bucketId)) {
          missingBucketIds.add(bucketId);
        }        
      });
    });
    addMissingOrExtraBucketIds(builder, missingBucketIdsPerRedundancyZone, "missing");
  }
  
  private void addExtraBucketsInRedundancyZone(StringBuilder builder) {
    Map<String,List<Integer>> extraBucketIdsPerRedundancyZone = new HashMap<>();
    this.allBucketIdsPerRedundancyZone.forEach((key, value) -> {
      List<Integer> extraBucketIds = extraBucketIdsPerRedundancyZone.computeIfAbsent(key, k -> new ArrayList<>());
      Set<Integer> distinctBucketIds = new HashSet<>();
      value.forEach(bucketId -> {
        if (!distinctBucketIds.add(bucketId)) {
          extraBucketIds.add(bucketId);
        }
      });
    });
    addMissingOrExtraBucketIds(builder, extraBucketIdsPerRedundancyZone, "extra");
  }
    
  private void addMissingOrExtraBucketIds(StringBuilder builder, Map<String,List<Integer>> bucketIds, String type) {
    builder
      .append("\nThe ")
      .append(bucketIds.size())
      .append(" ")
      .append("redundancy zones contain the following ")
      .append(type)
      .append(" bucket ids:");
    bucketIds.forEach((key, value) -> builder
      .append("\n\tZone ")
      .append(key)
      .append(" has ")
      .append(value.size())
      .append(" ")
      .append(type)
      .append(" bucket ids: ")
      .append(value));
  }

  public String toString() {
    return new StringBuilder()
      .append(getClass().getSimpleName())
      .append("[")
      .append("; configuredNumberOfBuckets=")
      .append(this.configuredNumberOfBuckets)
      .append("; allBucketIdsPerServer=")
      .append(this.allBucketIdsPerServer)
      .append("; primaryBucketIdsPerServer=")
      .append(this.primaryBucketIdsPerServer)
      .append("; allBucketIdsPerRedundancyZone=")
      .append(this.allBucketIdsPerRedundancyZone)
      .append("; primaryBucketIdsPerRedundancyZone=")
      .append(this.primaryBucketIdsPerRedundancyZone)
      .append("]")
      .toString();
  }
}