package org.noear.solon.cloud.service;


import org.noear.solon.logging.event.LogEvent;

/**
 * @author noear 2021/2/23 created
 */
public interface CloudLogService {
    void append(LogEvent logEvent);
}
