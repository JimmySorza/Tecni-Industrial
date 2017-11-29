package com.tecnindustrial.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Categoria.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Producto.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.OrdenReparacion.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.OrdenReparacion.class.getName() + ".lineas", jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.OrdenLinea.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Tecnico.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Estado.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Venta.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Venta.class.getName() + ".lineas", jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.VentaLinea.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Proveedor.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Compra.class.getName(), jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.Compra.class.getName() + ".lineas", jcacheConfiguration);
            cm.createCache(com.tecnindustrial.domain.CompraLinea.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
