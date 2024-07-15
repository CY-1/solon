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
package org.noear.solon.i18n.impl;

import org.noear.solon.Solon;
import org.noear.solon.Utils;
import org.noear.solon.core.handle.Context;
import org.noear.solon.i18n.LocaleResolver;
import org.noear.solon.i18n.LocaleUtil;

import java.util.Locale;

/**
 * 地区解析器，基于 cookie 处理
 *
 * @author noear
 * @since 1.5
 */
public class LocaleResolverCookie implements LocaleResolver {
    private String cookieName = "SOLON.LOCALE";

    /**
     * 设置cookie name
     * */
    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    /**
     * 获取地区
     *
     * @param ctx 上下文
     * */
    @Override
    public Locale getLocale(Context ctx) {
        if (ctx.getLocale() == null) {
            String lang = ctx.cookie(cookieName);

            if (Utils.isEmpty(lang)) {
                ctx.setLocale(Solon.cfg().locale());
            } else {
                ctx.setLocale(LocaleUtil.toLocale(lang));
            }
        }

        return ctx.getLocale();
    }

    /**
     * 设置地区
     *
     * @param ctx 上下文
     * @param locale 地区
     * */
    @Override
    public void setLocale(Context ctx, Locale locale) {
        ctx.cookieSet(cookieName, locale.getLanguage());
        ctx.setLocale(locale);
    }
}
