package org.noear.solon.net.integration;

import org.noear.socketd.transport.core.Listener;
import org.noear.solon.Utils;
import org.noear.solon.core.*;
import org.noear.solon.net.annotation.ServerEndpoint;
import org.noear.solon.net.socketd.SocketdRouter;
import org.noear.solon.net.websocket.WebSocketListener;
import org.noear.solon.net.websocket.WebSocketRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author noear
 * @since 2.6
 */
public class XPluginImpl implements Plugin {
    static final Logger log = LoggerFactory.getLogger(XPluginImpl.class);

    private final SocketdRouter socketdRouter = SocketdRouter.getInstance();
    private final WebSocketRouter webSocketRouter = WebSocketRouter.getInstance();

    @Override
    public void start(AppContext context) throws Throwable {
        //注入容器
        context.wrapAndPut(WebSocketRouter.class, webSocketRouter);
        context.wrapAndPut(SocketdRouter.class, socketdRouter);

        //添加注解处理
        context.beanBuilderAdd(ServerEndpoint.class, this::serverEndpointBuild);
    }

    private void serverEndpointBuild(Class<?> clz, BeanWrap bw, ServerEndpoint anno) {
        String path = anno.value();
        boolean registered = false;

        //socket.d
        if (bw.raw() instanceof Listener) {
            if (Utils.isEmpty(path)) {
                path = "**";
            }

            socketdRouter.of(path, bw.raw());
            registered = true;
        }

        //websocket
        if (bw.raw() instanceof WebSocketListener) {
            if (Utils.isEmpty(path)) {
                path = "**";
            }

            webSocketRouter.of(path, bw.raw());
            registered = true;
        }

        if (registered == false) {
            log.warn("@ServerEndpoint does not support type: {}", clz.getName());
        }
    }
}