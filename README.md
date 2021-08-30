# Log Buckets by Redundancy Zone
## Description

This example provides a function that logs buckets by redundancy zone.

For each server, the **GetBucketIdsFunction** returns a **ServerBucketIds** object that contains:

- region name
- redundancy zone
- configured number of buckets
- bucket ids

The **GetBucketIdsResultCollector** on the client combines all the **ServerBucketIds** into an **AllBucketIds** object containing:

- bucket ids per server
- bucket ids per redundancy zone
- missing bucket ids per redundancy zone
- extra bucket ids per redundancy zone

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
./runclient.sh load-regions 10000
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

2021-08-29 16:10:02.329  INFO 45609 --- [           main] example.client.Client                    : Starting Client ...
...
2021-08-29 16:10:06.427  INFO 45609 --- [           main] example.client.Client                    : Started Client in 4.574 seconds (JVM running for 5.132)
2021-08-29 16:10:06.717  INFO 45609 --- [           main] example.client.service.CustomerService   : 
Region: Customer
Configured Number of Buckets: 113
The 6 servers contain the following bucket ids:
         Server server-1-a in zone zoneA contains 37 bucket ids: [0, 4, 10, 12, 14, 16, 22, 24, 27, 32, 33, 36, 38, 40, 50, 53, 55, 56, 62, 64, 69, 71, 73, 77, 78, 82, 85, 86, 88, 89, 90, 95, 96, 100, 104, 107, 111]
         Server server-1-b in zone zoneB contains 38 bucket ids: [0, 3, 5, 7, 8, 12, 14, 16, 17, 23, 24, 25, 32, 35, 39, 41, 44, 45, 48, 52, 55, 58, 60, 65, 72, 74, 76, 82, 85, 86, 91, 92, 94, 99, 104, 105, 108, 110]
         Server server-2-a in zone zoneA contains 38 bucket ids: [1, 5, 9, 13, 17, 19, 21, 23, 26, 28, 30, 37, 39, 43, 44, 45, 51, 52, 57, 59, 60, 61, 63, 67, 72, 74, 75, 79, 80, 83, 92, 94, 97, 98, 101, 106, 109, 112]
         Server server-2-b in zone zoneB contains 37 bucket ids: [2, 9, 13, 15, 18, 21, 27, 29, 30, 34, 36, 40, 42, 43, 47, 49, 51, 54, 61, 64, 66, 69, 73, 77, 78, 79, 83, 87, 88, 89, 93, 97, 98, 100, 103, 107, 112]
         Server server-3-a in zone zoneA contains 38 bucket ids: [2, 3, 6, 7, 8, 11, 15, 18, 20, 25, 29, 31, 34, 35, 41, 42, 46, 47, 48, 49, 54, 58, 65, 66, 68, 70, 76, 81, 84, 87, 91, 93, 99, 102, 103, 105, 108, 110]
         Server server-3-b in zone zoneB contains 38 bucket ids: [1, 4, 6, 10, 11, 19, 20, 22, 26, 28, 31, 33, 37, 38, 46, 50, 53, 56, 57, 59, 62, 63, 67, 68, 70, 71, 75, 80, 81, 84, 90, 95, 96, 101, 102, 106, 109, 111]
The 2 redundancy zones contain the following bucket ids:
        Zone zoneB contains 113 bucket ids: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112]
        Zone zoneA contains 113 bucket ids: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112]
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
