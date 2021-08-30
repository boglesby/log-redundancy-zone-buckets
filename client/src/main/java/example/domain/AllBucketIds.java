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
  
  private final Map<String,List<Integer>> bucketIdsPerServer;

  private final Map<String,List<Integer>> bucketIdsPerRedundancyZone;
  
  public AllBucketIds() {
    this.redundancyZonesPerServer = new HashMap<>();
    this.bucketIdsPerRedundancyZone = new HashMap<>();
    this.bucketIdsPerServer = new HashMap<>();
  }

  public void process(String server, ServerBucketIds serverBucketIds) {
    this.regionName = serverBucketIds.getRegionName();
    this.configuredNumberOfBuckets = serverBucketIds.getConfiguredNumberOfBuckets();
    this.redundancyZonesPerServer.put(server, serverBucketIds.getRedundancyZone());
    updateBucketsPerServer(server, serverBucketIds);
    updateBucketsPerRedundancyZone(serverBucketIds);
  }
  
  private void updateBucketsPerServer(String server, ServerBucketIds serverBucketIds) {
    Collections.sort(serverBucketIds.getBucketIds());
    this.bucketIdsPerServer.put(server, serverBucketIds.getBucketIds());
  }
  
  private void updateBucketsPerRedundancyZone(ServerBucketIds serverBucketIds) {
    String redundancyZone = serverBucketIds.getRedundancyZone();
    List<Integer> redundancyZoneBucketIds = this.bucketIdsPerRedundancyZone.computeIfAbsent(redundancyZone, k -> new ArrayList<>());
    redundancyZoneBucketIds.addAll(serverBucketIds.getBucketIds());
    Collections.sort(redundancyZoneBucketIds);
    this.bucketIdsPerRedundancyZone.put(redundancyZone, redundancyZoneBucketIds);
  }
  
  public String getDisplayString() {
    StringBuilder builder = new StringBuilder();
    builder
      .append("Region: ")
      .append(this.regionName)
      .append("\nConfigured Number of Buckets: ")
      .append(this.configuredNumberOfBuckets);

    // Add bucket ids per server
    addServerBucketIds(builder);
    
    // Add bucket ids per redundancy zone
    addRedundancyZoneBucketIds(builder);
    
    // Add missing bucket ids in each redundancy zone
    addMissingBucketsInRedundancyZone(builder);
    
    // Add extra bucket ids in each redundancy zone
    addExtraBucketsInRedundancyZone(builder);
    
    builder.append("\n");

    return builder.toString();
  }
  
  private void addServerBucketIds(StringBuilder builder) {
    builder
      .append("\nThe ")
      .append(this.bucketIdsPerServer.size())
      .append(" servers contain the following bucket ids:");
    this.bucketIdsPerServer.forEach((key, value) -> builder
      .append("\n\t Server ")
      .append(key)
      .append(" in zone ")
      .append(this.redundancyZonesPerServer.get(key))
      .append(" contains ")
      .append(value.size())
      .append(" bucket ids: ")
      .append(value));
  }
  
  private void addRedundancyZoneBucketIds(StringBuilder builder) {
    builder
      .append("\nThe ")
      .append(this.bucketIdsPerRedundancyZone.size())
      .append(" redundancy zones contain the following bucket ids:");
    this.bucketIdsPerRedundancyZone.forEach((key, value) -> builder
      .append("\n\tZone ")
      .append(key)
      .append(" contains ")
      .append(value.size())
      .append(" bucket ids: ")
      .append(value));
  }
  
  private void addMissingBucketsInRedundancyZone(StringBuilder builder) {
    Map<String,List<Integer>> missingBucketIdsPerRedundancyZone = new HashMap<>();
    this.bucketIdsPerRedundancyZone.forEach((key, value) -> {
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
    this.bucketIdsPerRedundancyZone.forEach((key, value) -> {
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
}