package example.domain;

import org.springframework.data.annotation.Transient;

import java.util.List;

public class ServerBucketIds {

  private String regionName;

  private String redundancyZone;
  
  private int configuredNumberOfBuckets;
  
  private List<Integer> allBucketIds;
  
  private List<Integer> primaryBucketIds;
  
  public ServerBucketIds() {}
  
  public ServerBucketIds(String regionName, String redundancyZone, int configuredNumberOfBuckets, List<Integer> allBucketIds, List<Integer> primaryBucketIds) {
    this.regionName = regionName;
    this.redundancyZone = redundancyZone;
    this.configuredNumberOfBuckets = configuredNumberOfBuckets;
    this.allBucketIds = allBucketIds;
    this.primaryBucketIds = primaryBucketIds;
  }

  public String getRegionName() {
    return this.regionName;
  }

  public String getRedundancyZone() {
    return this.redundancyZone;
  }
  
  public int getConfiguredNumberOfBuckets() {
    return this.configuredNumberOfBuckets;
  }
  
  public List<Integer> getAllBucketIds() {
    return this.allBucketIds;
  }

  public List<Integer> getPrimaryBucketIds() {
    return this.primaryBucketIds;
  }

  // Note: The following methods are Transient to prevent:
  // Caused by: java.lang.IllegalStateException: Cannot set property totalNumberOfPrimaryBucketIds because no setter, no wither and it's not part of the persistence constructor public example.domain.ServerBucketIds()!
  //   at org.springframework.data.mapping.model.InstantiationAwarePropertyAccessor.setProperty(InstantiationAwarePropertyAccessor.java:118) ~[spring-data-commons-2.4.5.jar:2.4.5]
  @Transient
  public int getTotalNumberOfBucketIds() {
    return this.allBucketIds.size();
  }

  @Transient
  public int getTotalNumberOfPrimaryBucketIds() {
    return this.primaryBucketIds.size();
  }

  public String toString() {
    return new StringBuilder()
      .append(getClass().getSimpleName())
      .append("[")
      .append("regionName=")
      .append(this.regionName)
      .append("; redundancyZone=")
      .append(this.redundancyZone)
      .append(" configuredNumberOfBuckets=")
      .append(this.configuredNumberOfBuckets)
      .append("; allBucketIds=")
      .append(allBucketIds)
      .append("; primaryBucketIds=")
      .append(primaryBucketIds)
      .append("]")
      .toString();
  }
}