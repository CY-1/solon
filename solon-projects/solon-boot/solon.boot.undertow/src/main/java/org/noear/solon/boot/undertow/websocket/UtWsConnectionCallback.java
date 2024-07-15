/*
 * Copyright 2017-2024 noear.org and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noear.solon.boot.undertow.websocket;

import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.*;
import io.undertow.websockets.spi.WebSocketHttpExchange;
import org.noear.solon.net.websocket.SubProtocolCapable;
import org.noear.solon.net.websocket.WebSocketRouter;

import java.net.URI;

public class UtWsConnectionCallback implements WebSocketConnectionCallback {
    private final UtWsChannelListener listener = new UtWsChannelListener();
    private final WebSocketRouter webSocketRouter = WebSocketRouter.getInstance();

    public void onHandshake(WebSocketHttpExchange exchange){
        //添加子协议支持
        String path = URI.create(exchange.getRequestURI()).getPath();
        SubProtocolCapable subProtocolCapable = webSocketRouter.getSubProtocol(path);
        if (subProtocolCapable != null) {
            exchange.setResponseHeader("Sec-WebSocket-Protocol", subProtocolCapable.getSubProtocols());
        }
    }

    @Override
    public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
        listener.onOpen(exchange, channel);

        channel.getReceiveSetter().set(listener);
        channel.resumeReceives();
    }
}
