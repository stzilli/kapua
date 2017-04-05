package org.eclipse.kapua.service.datastore.model.query;

import org.eclipse.kapua.locator.KapuaLocator;

public class StorablePredicateXmlRegistry {
    
    private static final StorablePredicateFactory factory = KapuaLocator.getInstance().getFactory(StorablePredicateFactory.class);
    
    public TermPredicate<?> newTermPredicate() {
        return factory.newTermPredicate(null, null);
    }
    
    public AndPredicate newAndPredicate() {
        return factory.newAndPredicate();
    }
    
    public ChannelPredicate newChannelPredicate() {
        return factory.newChannelPredicate(null);
    }
    
    public ExistsPredicate newExistsPredicate() {
        return factory.newExistsPredicate(null);
    }
}
