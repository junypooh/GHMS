package com.kt.giga.home.infra.server.proxy;

import java.nio.channels.SocketChannel;

import com.kt.giga.home.infra.server.server.TcpServerHandler;
import com.kt.giga.home.infra.server.server.TcpServerHandlerFactory;

class TcpProxyConnectorFactory implements TcpServerHandlerFactory {

    private final TcpProxyConfig config;

    public TcpProxyConnectorFactory(TcpProxyConfig config) {
        this.config = config;
    }

    @Override
    public TcpServerHandler create(final SocketChannel clientChannel) {
        return new TcpProxyConnector(clientChannel, config);
    }

}
