package com.hazelcast.hibernate4.region;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.hibernate4.access.NonStrictReadWriteAccessDelegate;
import com.hazelcast.hibernate4.access.ReadOnlyAccessDelegate;
import com.hazelcast.hibernate4.access.ReadWriteAccessDelegate;
import com.hazelcast.hibernate4.distributed.IMapRegionCache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;

import java.util.Properties;

/**
 * @mdogan 11/16/12
 */
public class HazelcastNaturalIdRegion extends AbstractTransactionalDataRegion<IMapRegionCache>
        implements NaturalIdRegion {

    public HazelcastNaturalIdRegion(final HazelcastInstance instance, final String regionName,
                                    final Properties props, final CacheDataDescription metadata) {
        super(instance, regionName, props, metadata, new IMapRegionCache(regionName, instance, props, metadata));
    }

    public NaturalIdRegionAccessStrategy buildAccessStrategy(final AccessType accessType) throws CacheException {
        if (null == accessType) {
            throw new CacheException(
                    "Got null AccessType while attempting to determine a proper NaturalIdRegionAccessStrategy. This can't happen!");
        }
        if (AccessType.READ_ONLY.equals(accessType)) {
            return new NaturalIdRegionAccessStrategyAdapter(
                    new ReadOnlyAccessDelegate<HazelcastNaturalIdRegion>(this, props));
        }
        if (AccessType.NONSTRICT_READ_WRITE.equals(accessType)) {
            return new NaturalIdRegionAccessStrategyAdapter(
                    new NonStrictReadWriteAccessDelegate<HazelcastNaturalIdRegion>(this, props));
        }
        if (AccessType.READ_WRITE.equals(accessType)) {
            return new NaturalIdRegionAccessStrategyAdapter(
                    new ReadWriteAccessDelegate<HazelcastNaturalIdRegion>(this, props));
        }
        if (AccessType.TRANSACTIONAL.equals(accessType)) {
            throw new CacheException("Transactional access is not currently supported by Hazelcast.");
        }
        throw new CacheException("Got unknown AccessType \"" + accessType
                                 + "\" while attempting to build EntityRegionAccessStrategy.");
    }
}
