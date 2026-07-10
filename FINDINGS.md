## Test recordings between optimal hashing strategies for Distributed Systems

### Partition Strategy Test

- Involves evaluation of:
    - Handle Large Partition count.
    - Same key should map to same partition.
    - Partition should always be in the same range.
    - Different Key should return valid partitions.
    - Able to Handle Empty Key.
- Modulo Hashing:
    - Fails to handle large partition count.
    - Fails to have the partition within range.
- Rendezvous Hashing:
    - It was able to correctly handle all tests without any issues.

### Partition Distribution Test

- Involves evaluation of how effectively the keys are being distributed.
- Modulo Hashing:
    - Partition 0 → 10000 keys
    - Partition 1 → 10000 keys
    - Partition 2 → 10000 keys
    - Partition 3 → 10000 keys
    - Partition 4 → 10000 keys
    - Partition 5 → 10000 keys
    - Partition 6 → 10000 keys
    - Partition 7 → 10000 keys
    - Partition 8 → 10000 keys
    - Partition 9 → 10000 keys
- Rendezvous Hashing:
    - Partition 0 → 9972 keys
    - Partition 1 → 9927 keys
    - Partition 2 → 10125 keys
    - Partition 3 → 9929 keys
    - Partition 4 → 9984 keys
    - Partition 5 → 10130 keys
    - Partition 6 → 9954 keys
    - Partition 7 → 9995 keys
    - Partition 8 → 9809 keys
    - Partition 9 → 10175 keys
- Understanding:
    - Both Modulo Hashing and Rendezvous Hashing distribute keys nearly uniformly across the available partitions.
    - Modulo Hashing produced an almost perfectly balanced distribution for the generated dataset. This is largely due
      to the characteristics of Java's String.hashCode() combined with sequentially generated keys (user0 to user99999).
    - Rendezvous Hashing also demonstrated an even distribution, with only minor variations between partitions. Such
      small deviations are expected because the algorithm independently computes a score for each partition and selects
      the highest-scoring one.
    - The results indicate that load balancing is not the primary differentiating factor between Modulo and Rendezvous
      Hashing. Both algorithms are capable of distributing workload effectively.
    - The real advantage of Rendezvous Hashing becomes apparent when the number of partitions changes. Unlike Modulo
      Hashing, Rendezvous Hashing minimizes key movement during partition scaling while still maintaining a balanced
      distribution.

### Performance Test

- Involves evaluation of how performant efficient they are.
    - Modulo : 42.20 ms
    - Rendezvous : 2015.33 ms
- Understanding:
    - Modulo Hashing demonstrated significantly better execution time than Rendezvous Hashing.
    - This performance difference is expected because Modulo Hashing performs a single hash computation followed by a
      modulo operation, resulting in constant time (O(1)) complexity.
    - In contrast, Rendezvous Hashing computes a score for every available partition before selecting the
      highest-scoring partition, resulting in linear time (O(n)) complexity with respect to the number of partitions.
    - Although Rendezvous Hashing is computationally more expensive, the additional processing provides improved
      partition stability and significantly reduces key movement when partitions are added or removed.
    - Therefore, the performance overhead represents a trade-off between computational efficiency and system
      scalability.

### Scaling Test

- Involves how the hashing behaves as the cluster grows overtime.
    - Modulo:
        - 3 -> 4 : 75.00%
        - 4 -> 5 : 79.96%
        - 5 -> 6 : 74.18%
        - 6 -> 8 : 75.00%
        - 8 -> 10 : 80.22%
        - 10 -> 20 : 50.08%
    - Rendezvous:
        - 3 → 4 : 25.00%
        - 4 → 5 : 20.06%
        - 5 → 6 : 16.82%
        - 6 → 8 : 24.87%
        - 8 → 10 : 19.98%
        - 10 → 20 : 50.22%
- Understanding:
    - Modulo Hashing exhibited significant key movement for nearly every scaling operation, with approximately 74–80% of
      keys being reassigned when a small number of partitions were added.
    - This occurs because Modulo Hashing recalculates the partition using hash(key) % partitionCount. Changing the
      partition count changes the modulus, causing many keys to be redistributed across both existing and newly added
      partitions.
    - Rendezvous Hashing demonstrated substantially lower key movement for incremental scaling operations. The observed
      movement closely matched the theoretical expectation of 1 / (new partition count). For example:
    - Scaling from 3 → 4 resulted in approximately 25% key movement.
    - Scaling from 4 → 5 resulted in approximately 20% key movement.
    - Scaling from 5 → 6 resulted in approximately 16.8% key movement.
    - When the number of partitions was doubled (e.g., 10 → 20), approximately 50% of the keys moved. This behavior is
      expected because half of the candidate partitions are newly introduced, giving each key an equal probability of
      selecting a new partition.
    - These results demonstrate that Rendezvous Hashing minimizes key movement during incremental cluster growth, making
      it significantly more suitable than Modulo Hashing for distributed systems that scale gradually over time.