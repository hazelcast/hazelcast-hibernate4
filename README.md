!!!This repo is outdated.!!!
======================
Please check https://github.com/hazelcast/hazelcast-hibernate

---------------------------

Beta implementation of *hazelcast-hibernate* module for Hibernate 4.0.

###Usage:

For distributed mode;

`<property name="hibernate.cache.region.factory_class">com.hazelcast.hibernate4.HazelcastCacheRegionFactory</property>`


For invalidation mode;

`<property name="hibernate.cache.region.factory_class">com.hazelcast.hibernate4.HazelcastLocalCacheRegionFactory</property>`


Note: This module will be integrated into Hazelcast distribution in a later release.
