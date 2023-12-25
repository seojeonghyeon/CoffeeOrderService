package me.justin.coffeeorderservice.infra.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static me.justin.coffeeorderservice.infra.config.DataSourceConfig.MASTER_DATASOURCE;
import static me.justin.coffeeorderservice.infra.config.DataSourceConfig.SLAVE_DATASOURCE;


@Profile("prd")
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) ? "slave" : "master";
    }

    @Bean
    @Primary
    @DependsOn({MASTER_DATASOURCE, SLAVE_DATASOURCE})
    public DataSource routingDataSource(
            @Qualifier(MASTER_DATASOURCE) DataSource masterDataSource,
            @Qualifier(SLAVE_DATASOURCE) DataSource slaveDataSource) {

        RoutingDataSource routingDataSource = new RoutingDataSource();
        Map<Object, Object> datasourceMap = new HashMap<>() {
            {
                put("master", masterDataSource);
                put("slave", slaveDataSource);
            }
        };
        routingDataSource.setTargetDataSources(datasourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
