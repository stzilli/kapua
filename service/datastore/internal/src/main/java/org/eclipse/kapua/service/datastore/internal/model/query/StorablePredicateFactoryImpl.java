package org.eclipse.kapua.service.datastore.internal.model.query;

import org.eclipse.kapua.locator.KapuaProvider;
import org.eclipse.kapua.service.datastore.model.query.AndPredicate;
import org.eclipse.kapua.service.datastore.model.query.ChannelPredicate;
import org.eclipse.kapua.service.datastore.model.query.ExistsPredicate;
import org.eclipse.kapua.service.datastore.model.query.RangePredicate;
import org.eclipse.kapua.service.datastore.model.query.StorableField;
import org.eclipse.kapua.service.datastore.model.query.StorablePredicateFactory;
import org.eclipse.kapua.service.datastore.model.query.TermPredicate;


/**
 * {@link StorablePredicateFactory} implementation
 * 
 * @since 1.0.0
 */
@KapuaProvider
public class StorablePredicateFactoryImpl implements StorablePredicateFactory {

    @Override
    public AndPredicate newAndPredicate() {
        return new AndPredicateImpl();
    }

    @Override
    public <V> TermPredicate<V> newTermPredicate(String field, V value) {
        return new TermPredicateImpl<>(field, value);
    }

    @Override
    public ChannelPredicate newChannelPredicate(String expression) {
        return new ChannelPredicateImpl(expression);
    }
    
    @Override
    public ExistsPredicate newExistsPredicate(String name) {
        return new ExistsPredicateImpl(name);
    }

    @Override
    public <V extends Comparable<V>> RangePredicate<V> newRangePredicate(String field, V minValue, V maxValue) {
        return new RangePredicateImpl<>(field, minValue, maxValue);
    }


}
