# Log Buckets by Redundancy Zone
## Description

This example provides a function that logs buckets by redundancy zone.

For each server, the **GetBucketIdsFunction** returns a **ServerBucketIds** object that contains:

- region name
- redundancy zone
- configured number of buckets
- all bucket ids
- primary bucket ids

The **GetBucketIdsResultCollector** on the client combines all the **ServerBucketIds** into an **AllBucketIds** object containing:

- all bucket ids per server
- primary bucket ids per server
- all bucket ids per redundancy zone
- primary bucket ids per redundancy zone
- missing bucket ids per redundancy zone
- extra bucket ids per redundancy zone
- total number of bucket ids
- total number of primary buckets

## Initialization
Modify the **GEODE** environment variable in the *setenv.sh* script to point to a Geode installation directory.
## Build
Build the Spring Boot Client Application and Geode Server Function and logger classes using gradle like:

```
./gradlew clean jar bootJar
```
## Run Example
### Start and Configure Locator and Servers
Start and configure the locator and 6 servers in 2 redundancy zones using the *startandconfigure.sh* script like:

```
./startandconfigure.sh
```
### Load Entries
Load N Customer instances into the Customer region and 5N Order instances into the co-located Order Region using the *runclient.sh* script like below.

The parameters are:

- operation (load-customers-and-orders)
- number of entries (e.g. 10000)

```
./runclient.sh load-customers-and-orders 10000
```
### Log Buckets
Log the buckets using the *runclient.sh* script like below.

The parameters are:

- operation (log-buckets)
- region name (e.g. Customer)

```
./runclient.sh log-buckets Customer
```

### Shutdown Locator and Servers
Execute the *shutdownall.sh* script to shutdown the servers and locators like:

```
./shutdownall.sh
```
### Remove Locator and Server Files
Execute the *cleanupfiles.sh* script to remove the server and locator files like:

```
./cleanupfiles.sh
```
## Example Output
### Start and Configure Locator and Servers
Sample output from the *startandconfigure.sh* script is:

```
./startandconfigure.sh
1. Executing - start locator --name=locator

............................
Locator in <working-dir>/locator on localhost[10334] as locator is currently online.
Process ID: 45364
Uptime: 45 seconds
Geode Version: 1.15.0-build.0
Java Version: 1.8.0_151
Log File: <working-dir>/locator/locator.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

Successfully connected to: JMX Manager [host=localhost, port=1099]

Cluster configuration service is up and running.

2. Executing - set variable --name=APP_RESULT_VIEWER --value=any

Value for variable APP_RESULT_VIEWER is now: any.

3. Executing - configure pdx --read-serialized=true --disk-store=DEFAULT --auto-serializable-classes=.*

read-serialized = true
ignore-unread-fields = false
persistent = true
disk-store = DEFAULT
PDX Serializer = org.apache.geode.pdx.ReflectionBasedAutoSerializer
Non Portable Classes = [.*]
Cluster configuration for group 'cluster' is updated.

4. Executing - start server --name=server-1-a --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneA

......
Server in <working-dir>/server-1-a on localhost[56372] as server-1-a is currently online.
Process ID: 45381
Uptime: 7 seconds
Geode Version: 1.15.0-build.0
Java Version: 1.8.0_151
Log File: <working-dir>/server-1-a/cacheserver.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

5. Executing - start server --name=server-2-a --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneA

.......
Server in <working-dir>/server-2-a on localhost[56404] as server-2-a is currently online.
Process ID: 45386
Uptime: 8 seconds
Geode Version: 1.15.0-build.0
Java Version: 1.8.0_151
Log File: <working-dir>/server-2-a/cacheserver.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

6. Executing - start server --name=server-3-a --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneA

.........
Server in <working-dir>/server-3-a on localhost[56444] as server-3-a is currently online.
Process ID: 45410
Uptime: 14 seconds
Geode Version: 1.15.0-build.0
Java Version: 1.8.0_151
Log File: <working-dir>/server-3-a/cacheserver.log
JVM Arguments: <jvm-arguments>
Class-Path: <classpath>

7. Executing - start server --name=server-1-b --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneB

..........
Server in <working-dir>/server-1-b on localhost[56492] as server-1-b is currently online.
Process ID: 45414
Uptime: 11 seconds
Geode Version: 1.15.0-build.0
Java Version: 1.8.0_151
Log File: <working-dir>/server-1-b/cacheserver.log
JVM Arguments: -Dgemfire.default.locators=localhost[10334] -Dgemfire.start-dev-rest-api=false -Dgemfire.use-cluster-configuration=true -Dgemfire.statistic-archive-file=cacheserver.gfs -Dgemfire.log-file=cacheserver.log -Dgemfire.conserve-sockets=false -Dgemfire.redundancy-zone=zoneB -XX:OnOutOfMemoryError=kill -KILL %p -Dgemfire.launcher.registerSignalHandlers=true -Djava.awt.headless=true -Dsun.rmi.dgc.server.gcInterval=9223372036854775806
Class-Path: <classpath>

8. Executing - start server --name=server-2-b --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneB

......
Server in <working-dir>/server-2-b on localhost[56539] as server-2-b is currently online.
Process ID: 45420
Uptime: 7 seconds
Geode Version: 1.15.0-build.0
Java Version: 1.8.0_151
Log File: <working-dir>/server-2-b/cacheserver.log
JVM Arguments: -Dgemfire.default.locators=localhost[10334] -Dgemfire.start-dev-rest-api=false -Dgemfire.use-cluster-configuration=true -Dgemfire.statistic-archive-file=cacheserver.gfs -Dgemfire.log-file=cacheserver.log -Dgemfire.conserve-sockets=false -Dgemfire.redundancy-zone=zoneB -XX:OnOutOfMemoryError=kill -KILL %p -Dgemfire.launcher.registerSignalHandlers=true -Djava.awt.headless=true -Dsun.rmi.dgc.server.gcInterval=9223372036854775806
Class-Path: <classpath>

9. Executing - start server --name=server-3-b --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneB

.....
Server in <working-dir>/server-3-b on localhost[56590] as server-3-b is currently online.
Process ID: 45421
Uptime: 6 seconds
Geode Version: 1.15.0-build.0
Java Version: 1.8.0_151
Log File: <working-dir>/server-3-b/cacheserver.log
JVM Arguments: -Dgemfire.default.locators=localhost[10334] -Dgemfire.start-dev-rest-api=false -Dgemfire.use-cluster-configuration=true -Dgemfire.statistic-archive-file=cacheserver.gfs -Dgemfire.log-file=cacheserver.log -Dgemfire.conserve-sockets=false -Dgemfire.redundancy-zone=zoneB -XX:OnOutOfMemoryError=kill -KILL %p -Dgemfire.launcher.registerSignalHandlers=true -Djava.awt.headless=true -Dsun.rmi.dgc.server.gcInterval=9223372036854775806
Class-Path: <classpath>

10. Executing - deploy --jar=server/build/libs/server-0.0.1-SNAPSHOT.jar

  Member   | Deployment Name |            JAR            | JAR Location
---------- | --------------- | ------------------------- | -----------------------------------------------------
server-1-a | server          | server-0.0.1-SNAPSHOT.jar | <working-dir>/server-1-a/server-0.0.1-SNAPSHOT.v1.jar
server-1-b | server          | server-0.0.1-SNAPSHOT.jar | <working-dir>/server-1-b/server-0.0.1-SNAPSHOT.v1.jar
server-2-a | server          | server-0.0.1-SNAPSHOT.jar | <working-dir>/server-2-a/server-0.0.1-SNAPSHOT.v1.jar
server-2-b | server          | server-0.0.1-SNAPSHOT.jar | <working-dir>/server-2-b/server-0.0.1-SNAPSHOT.v1.jar
server-3-a | server          | server-0.0.1-SNAPSHOT.jar | <working-dir>/server-3-a/server-0.0.1-SNAPSHOT.v1.jar
server-3-b | server          | server-0.0.1-SNAPSHOT.jar | <working-dir>/server-3-b/server-0.0.1-SNAPSHOT.v1.jar

11. Executing - list members

Member Count : 7

   Name    | Id
---------- | ------------------------------------------------------------
locator    | localhost(locator:45364:locator)<ec><v0>:41000 [Coordinator]
server-1-a | localhost(server-1-a:45381)<v1>:41001
server-2-a | localhost(server-2-a:45386)<v2>:41002
server-3-a | localhost(server-3-a:45410)<v3>:41003
server-1-b | localhost(server-1-b:45414)<v4>:41004
server-2-b | localhost(server-2-b:45420)<v5>:41005
server-3-b | localhost(server-3-b:45421)<v6>:41006

12. Executing - create region --name=Customer --type=PARTITION_REDUNDANT_PERSISTENT

  Member   | Status | Message
---------- | ------ | ------------------------------------------
server-1-a | OK     | Region "/Customer" created on "server-1-a"
server-1-b | OK     | Region "/Customer" created on "server-1-b"
server-2-a | OK     | Region "/Customer" created on "server-2-a"
server-2-b | OK     | Region "/Customer" created on "server-2-b"
server-3-a | OK     | Region "/Customer" created on "server-3-a"
server-3-b | OK     | Region "/Customer" created on "server-3-b"

Cluster configuration for group 'cluster' is updated.

13. Executing - create region --name=Order --type=PARTITION_REDUNDANT_PERSISTENT --colocated-with=Customer --partition-resolver=example.resolver.CustomerPartitionResolver

  Member   | Status | Message
---------- | ------ | ---------------------------------------
server-1-a | OK     | Region "/Order" created on "server-1-a"
server-1-b | OK     | Region "/Order" created on "server-1-b"
server-2-a | OK     | Region "/Order" created on "server-2-a"
server-2-b | OK     | Region "/Order" created on "server-2-b"
server-3-a | OK     | Region "/Order" created on "server-3-a"
server-3-b | OK     | Region "/Order" created on "server-3-b"

Cluster configuration for group 'cluster' is updated.

14. Executing - list regions

List of regions
---------------
Customer
Order

15. Executing - list functions

  Member   | Function
---------- | --------------------
server-1-a | GetBucketIdsFunction
server-1-b | GetBucketIdsFunction
server-2-a | GetBucketIdsFunction
server-2-b | GetBucketIdsFunction
server-3-a | GetBucketIdsFunction
server-3-b | GetBucketIdsFunction

************************* Execution Summary ***********************
Script file: startandconfigure.gfsh

Command-1 : start locator --name=locator
Status    : PASSED

Command-2 : set variable --name=APP_RESULT_VIEWER --value=any
Status    : PASSED

Command-3 : configure pdx --read-serialized=true --disk-store=DEFAULT --auto-serializable-classes=.*
Status    : PASSED

Command-4 : start server --name=server-1-a --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneA
Status    : PASSED

Command-5 : start server --name=server-2-a --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneA
Status    : PASSED

Command-6 : start server --name=server-3-a --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneA
Status    : PASSED

Command-7 : start server --name=server-1-b --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneB
Status    : PASSED

Command-8 : start server --name=server-2-b --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneB
Status    : PASSED

Command-9 : start server --name=server-3-b --server-port=0 --statistic-archive-file=cacheserver.gfs --J=-Dgemfire.log-file=cacheserver.log --J=-Dgemfire.conserve-sockets=false --J=-Dgemfire.redundancy-zone=zoneB
Status    : PASSED

Command-10 : deploy --jar=server/build/libs/server-0.0.1-SNAPSHOT.jar
Status     : PASSED

Command-11 : list members
Status     : PASSED

Command-12 : create region --name=Customer --type=PARTITION_REDUNDANT_PERSISTENT
Status     : PASSED

Command-13 : create region --name=Order --type=PARTITION_REDUNDANT_PERSISTENT --colocated-with=Customer --partition-resolver=example.resolver.CustomerPartitionResolver
Status     : PASSED

Command-14 : list regions
Status     : PASSED

Command-15 : list functions
Status     : PASSED
```
### Load Entries
Sample output from the *runclient.sh* script is:

```
./runclient.sh load-customers-and-orders 1000

2021-08-29 16:09:29.130  INFO 45589 --- [           main] example.client.Client                    : Starting Client ...
...
2021-08-29 16:09:32.021  INFO 45589 --- [           main] example.client.Client                    : Started Client in 3.257 seconds (JVM running for 3.893)
2021-08-29 16:09:32.023  INFO 45589 --- [           main] example.client.service.CustomerService   : Putting 1000 customers
2021-08-29 16:09:32.062  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=0)
2021-08-29 16:09:32.079  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=0-0)
2021-08-29 16:09:32.084  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=0-1)
2021-08-29 16:09:32.089  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=0-2)
2021-08-29 16:09:32.097  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=0-3)
2021-08-29 16:09:32.099  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=0-4)
2021-08-29 16:09:32.103  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=1)
2021-08-29 16:09:32.105  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=1-0)
2021-08-29 16:09:32.107  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=1-1)
2021-08-29 16:09:32.109  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=1-2)
2021-08-29 16:09:32.111  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=1-3)
2021-08-29 16:09:32.113  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=1-4)
2021-08-29 16:09:32.124  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=2)
2021-08-29 16:09:32.126  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=2-0)
2021-08-29 16:09:32.128  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=2-1)
2021-08-29 16:09:32.130  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=2-2)
2021-08-29 16:09:32.132  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=2-3)
2021-08-29 16:09:32.134  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=2-4)
2021-08-29 16:09:32.150  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=3)
2021-08-29 16:09:32.153  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=3-0)
2021-08-29 16:09:32.155  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=3-1)
2021-08-29 16:09:32.157  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=3-2)
2021-08-29 16:09:32.159  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=3-3)
2021-08-29 16:09:32.161  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=3-4)
2021-08-29 16:09:32.164  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=4)
2021-08-29 16:09:32.166  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=4-0)
2021-08-29 16:09:32.168  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=4-1)
2021-08-29 16:09:32.171  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=4-2)
2021-08-29 16:09:32.173  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=4-3)
2021-08-29 16:09:32.175  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=4-4)
...
2021-08-29 16:09:41.419  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=995)
2021-08-29 16:09:41.421  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=995-0)
2021-08-29 16:09:41.423  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=995-1)
2021-08-29 16:09:41.425  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=995-2)
2021-08-29 16:09:41.426  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=995-3)
2021-08-29 16:09:41.429  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=995-4)
2021-08-29 16:09:41.430  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=996)
2021-08-29 16:09:41.431  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=996-0)
2021-08-29 16:09:41.433  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=996-1)
2021-08-29 16:09:41.434  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=996-2)
2021-08-29 16:09:41.435  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=996-3)
2021-08-29 16:09:41.436  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=996-4)
2021-08-29 16:09:41.438  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=997)
2021-08-29 16:09:41.441  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=997-0)
2021-08-29 16:09:41.442  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=997-1)
2021-08-29 16:09:41.445  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=997-2)
2021-08-29 16:09:41.448  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=997-3)
2021-08-29 16:09:41.450  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=997-4)
2021-08-29 16:09:41.451  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=998)
2021-08-29 16:09:41.453  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=998-0)
2021-08-29 16:09:41.455  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=998-1)
2021-08-29 16:09:41.456  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=998-2)
2021-08-29 16:09:41.458  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=998-3)
2021-08-29 16:09:41.460  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=998-4)
2021-08-29 16:09:41.462  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Customer(id=999)
2021-08-29 16:09:41.464  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=999-0)
2021-08-29 16:09:41.466  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=999-1)
2021-08-29 16:09:41.468  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=999-2)
2021-08-29 16:09:41.470  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=999-3)
2021-08-29 16:09:41.471  INFO 45589 --- [           main] example.client.service.CustomerService   : Saved Order(id=999-4)
```
### Log Buckets
Sample output from the *runclient.sh* script is:

```
./runclient.sh log-buckets Customer

2021-09-11 14:41:13.443  INFO 94377 --- [           main] example.client.Client                    : Starting Client ...
...
2021-09-11 14:41:16.425  INFO 94377 --- [           main] example.client.Client                    : Started Client in 3.338 seconds (JVM running for 3.792)
2021-09-11 14:41:16.709  INFO 94377 --- [           main] example.client.service.CustomerService   : 
Region: Customer
Configured Number of Buckets: 113
The 6 servers contain the following 226 bucket ids:
         Server server-1-a in zone zoneA contains 38 bucket ids: [1, 3, 9, 10, 18, 19, 22, 23, 29, 32, 34, 35, 37, 40, 43, 50, 51, 55, 61, 63, 68, 72, 73, 76, 79, 81, 85, 88, 89, 91, 93, 96, 99, 103, 106, 108, 109, 112]
         Server server-1-b in zone zoneB contains 38 bucket ids: [1, 4, 6, 10, 12, 14, 16, 17, 19, 24, 31, 33, 34, 39, 43, 45, 47, 49, 53, 58, 62, 66, 67, 68, 71, 72, 75, 81, 82, 83, 92, 95, 96, 98, 99, 100, 105, 111]
         Server server-2-a in zone zoneA contains 38 bucket ids: [0, 5, 6, 7, 8, 12, 14, 17, 20, 25, 30, 31, 36, 41, 42, 45, 47, 49, 52, 53, 59, 62, 64, 66, 69, 71, 74, 77, 82, 83, 86, 87, 90, 94, 98, 101, 102, 107]
         Server server-3-a in zone zoneA contains 37 bucket ids: [2, 4, 11, 13, 15, 16, 21, 24, 26, 27, 28, 33, 38, 39, 44, 46, 48, 54, 56, 57, 58, 60, 65, 67, 70, 75, 78, 80, 84, 92, 95, 97, 100, 104, 105, 110, 111]
         Server server-2-b in zone zoneB contains 37 bucket ids: [2, 5, 9, 11, 20, 22, 23, 26, 27, 30, 35, 37, 40, 41, 44, 48, 51, 52, 54, 60, 61, 65, 73, 74, 78, 84, 86, 87, 88, 89, 90, 93, 94, 101, 103, 107, 112]
         Server server-3-b in zone zoneB contains 38 bucket ids: [0, 3, 7, 8, 13, 15, 18, 21, 25, 28, 29, 32, 36, 38, 42, 46, 50, 55, 56, 57, 59, 63, 64, 69, 70, 76, 77, 79, 80, 85, 91, 97, 102, 104, 106, 108, 109, 110]
The 6 servers contain the following 113 primary bucket ids:
         Server server-1-a in zone zoneA contains 18 primary bucket ids: [1, 3, 10, 18, 22, 35, 37, 40, 51, 55, 61, 63, 68, 73, 81, 93, 103, 109]
         Server server-1-b in zone zoneB contains 19 primary bucket ids: [4, 6, 12, 19, 24, 34, 43, 49, 62, 67, 72, 75, 82, 83, 92, 96, 99, 100, 111]
         Server server-2-a in zone zoneA contains 19 primary bucket ids: [0, 5, 8, 14, 17, 20, 31, 36, 42, 45, 47, 53, 66, 71, 77, 87, 90, 98, 102]
         Server server-3-a in zone zoneA contains 19 primary bucket ids: [2, 13, 16, 21, 26, 28, 33, 39, 46, 48, 56, 58, 65, 70, 80, 84, 95, 97, 105]
         Server server-2-b in zone zoneB contains 19 primary bucket ids: [9, 11, 23, 27, 30, 41, 44, 52, 54, 60, 74, 78, 86, 88, 89, 94, 101, 107, 112]
         Server server-3-b in zone zoneB contains 19 primary bucket ids: [7, 15, 25, 29, 32, 38, 50, 57, 59, 64, 69, 76, 79, 85, 91, 104, 106, 108, 110]
The 2 redundancy zones contain the following bucket ids:
        Zone zoneB contains 113 bucket ids: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112]
        Zone zoneA contains 113 bucket ids: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112]
The 2 redundancy zones contain the following primary bucket ids:
        Zone zoneB contains 57 primary bucket ids: [4, 6, 7, 9, 11, 12, 15, 19, 23, 24, 25, 27, 29, 30, 32, 34, 38, 41, 43, 44, 49, 50, 52, 54, 57, 59, 60, 62, 64, 67, 69, 72, 74, 75, 76, 78, 79, 82, 83, 85, 86, 88, 89, 91, 92, 94, 96, 99, 100, 101, 104, 106, 107, 108, 110, 111, 112]
        Zone zoneA contains 56 primary bucket ids: [0, 1, 2, 3, 5, 8, 10, 13, 14, 16, 17, 18, 20, 21, 22, 26, 28, 31, 33, 35, 36, 37, 39, 40, 42, 45, 46, 47, 48, 51, 53, 55, 56, 58, 61, 63, 65, 66, 68, 70, 71, 73, 77, 80, 81, 84, 87, 90, 93, 95, 97, 98, 102, 103, 105, 109]
The 2 redundancy zones contain the following missing bucket ids:
        Zone zoneB has 0 missing bucket ids: []
        Zone zoneA has 0 missing bucket ids: []
The 2 redundancy zones contain the following extra bucket ids:
        Zone zoneB has 0 extra bucket ids: []
        Zone zoneA has 0 extra bucket ids: []
```
### Shutdown Locator and Servers
Sample output from the *shutdownall.sh* script is:

```
./shutdownall.sh 

(1) Executing - connect

Connecting to Locator at [host=localhost, port=10334] ..
Connecting to Manager at [host=localhost, port=1099] ..
Successfully connected to: [host= localhost, port=1099]

You are connected to a cluster of version: 1.15.0-build.0


(2) Executing - shutdown --include-locators=true

Shutdown is triggered
```
