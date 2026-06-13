# Kubernetes Native Distributed Event Streaming Platform

## What are we trying to solve here?

Managing kafka broker clusters is difficult as it not just used for storing messages. It must also handle:
- Broker failures
- Leader election
- Replication
- Metadata synchronization
- Consumer coordiantion

## Requirements

- The main focus shifts when we try to implement this into a production using kubernetes pods.<br>
- The consumer who subscribed will be assigned to pod and if we have multiple pods, it has to rebalace based on the topic - partitions. <br>
- It should be able to deliver atleast once. <br>
- Hashing algorithm to land the same key to particular partition everytime <br>
- Producer can send messages with same kafka key multiple times <br>
- Consumer will consume from the topic he has subscribed to. <br>
- Design a topic with multiple partitions which supports for multiple consumers. <br>
- It is also based on the capacity you can handle, a metric you can use is the number of transactions per second. <br>
- Since kafka doesn't maintain internal tables to map consumer -> partitions, we have to make sure that one unique key received to a particular partition has to always and manage to fall on the same partition everytime (during growth or shrink even). <br>
- To improve the overall performance, idempotency, consistency. <br>

## Production Grade
Replication across multiple data centeres is biggest hurdle in the industry. The real pain points we need to consider:
- Latency
- Network partitions
- Conflict resolution
- Failover
<br>
So there is a need to maintain eventual consistency over different servers around the world.