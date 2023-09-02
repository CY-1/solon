package org.noear.solon.sessionstate.jedis;

import org.noear.solon.Solon;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.util.LogUtil;

public class XPluginImp implements Plugin {
    @Override
    public void start(AppContext context) {
        if (Solon.app().enableSessionState() == false) {
            return;
        }

        if (Solon.app().chainManager().getSessionStateFactory().priority()
                >= JedisSessionStateFactory.SESSION_STATE_PRIORITY) {
            return;
        }

        /*
         *
         * server.session.state.redis:
         * server:
         * password:
         * db: 31
         * maxTotal: 200
         *
         * */

        if (JedisSessionStateFactory.getInstance().redisClient() == null) {
            return;
        }

        Solon.app().chainManager().setSessionStateFactory(JedisSessionStateFactory.getInstance());

        LogUtil.global().info("Session: Redis session state plugin is loaded");
    }
}
