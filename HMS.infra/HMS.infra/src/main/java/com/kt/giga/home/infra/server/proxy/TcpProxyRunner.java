package com.kt.giga.home.infra.server.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TcpProxyRunner {
	
	@Value("#{system['proxy.silk.yn']}")
	private String proxyYn;

    private final static Logger LOGGER = Logger.getAnonymousLogger();

    @PostConstruct
    public void init() {
    	if("N".equals(proxyYn)) return;
    			
        final Properties properties = new Properties();
        
        try {
        	InputStream is = this.getClass().getResourceAsStream("/config/" + System.getProperty("hiot.run.env") + "/proxy.properties");
            properties.load(is);
        } catch (IOException exception) {
            if (LOGGER.isLoggable(Level.SEVERE))
                LOGGER.log(Level.SEVERE, "Can't load properties");
        }

        final List<TcpProxyConfig> configs = TcpProxyConfigParser.parse(properties);
        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("Starting TcpProxy with " + configs.size() + " connectors");

        final int cores = Runtime.getRuntime().availableProcessors();
        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("TcpProxy detected " + cores + " core" + (cores > 1 ? "s" : ""));

        final int workerCount = Math.max(cores / configs.size(), 1);
        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("TcpProxy will use " + workerCount + " workers per connector");

        for (final TcpProxyConfig config : configs) {
            config.setWorkerCount(workerCount);

            new TcpProxy(config).start();
        }

        if (LOGGER.isLoggable(Level.INFO))
            LOGGER.info("TcpProxy started");
    }
}
