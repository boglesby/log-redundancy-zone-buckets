package example.domain;

import java.util.List;

public class ServerBucketIds {

  private String regionName;

  private String redundancyZone;
  
  private int configuredNumberOfBuckets;
  
  private List<Integer> bucketIds;

  public ServerBucketIds() {}
  
  public ServerBucketIds(String regionName, String redundancyZone, int configuredNumberOfBuckets, List<Integer> bucketIds) {
    this.regionName = regionName;
    this.redundancyZone = redundancyZone;
    this.configuredNumberOfBuckets = configuredNumberOfBuckets;
    this.bucketIds = bucketIds;
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
      .append("; bucketIds=")
      .append(bucketIds)
      .append("]")
      .toString();
  }
}