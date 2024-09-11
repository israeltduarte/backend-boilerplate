package br.isertech.com.easyfinancesback.util;

import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.spi.Configurable;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class IserUUIDGenerator implements IdentifierGenerator, Configurable {

    private String prefix;
    private String originPrefix;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return String.format("%s_%s_%s", originPrefix, prefix, UUID.randomUUID());
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
        prefix = String.join("_", params.getProperty("prefix"));
        originPrefix = getProperty(serviceRegistry, "originPrefix");
    }

    private String getProperty(ServiceRegistry serviceRegistry, String property) {
        return serviceRegistry.getService(ConfigurationService.class).getSetting(property, String.class, "");
    }

    @Override
    public void configure(Map<String, Object> configurationValues) {
        throw new UnsupportedOperationException("Unimplemented method 'configure'");
    }

}