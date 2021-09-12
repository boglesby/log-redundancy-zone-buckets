package example.domain;

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

  public int getTotalNumberOfBucketIds() {
    return this.allBucketIds.size();
  }

  public List<Integer> getPrimaryBucketIds() {
    return this.primaryBucketIds;
  }

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