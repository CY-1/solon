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
package org.noear.solon.serialization.jackson.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.noear.solon.Utils;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.core.util.ClassUtil;
import org.noear.solon.serialization.ContextSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Jackson xml序列化
 * @author painter
 * @since 1.5
 * @since 2.8
 */
public class JacksonXmlStringSerializer implements ContextSerializer<String> {
    public static final String label = "/xml";
    private XmlMapper config;

    public XmlMapper getConfig() {
        if (config == null) {
            config = new XmlMapper();
        }

        return config;
    }

    public void setConfig(XmlMapper config) {
        if (config != null) {
            this.config = config;
        }
    }

    @Override
    public String getContentType() {
        return "text/xml";
    }

    @Override
    public boolean matched(Context ctx, String mime) {
        if (mime == null) {
            return false;
        } else {
            return mime.contains(label);
        }
    }

    @Override
    public String name() {
        return "jackson-xml";
    }

    @Override
    public String serialize(Object obj) throws IOException {
        return getConfig().writeValueAsString(obj);
    }

    @Override
    public Object deserialize(String data, Type toType) throws IOException {
        if (toType == null) {
            return getConfig().readTree(data);
        } else {
            Class<?> clz = ClassUtil.getTypeClass(toType);
            return getConfig().readValue(data, clz);
        }
    }

    @Override
    public void serializeToBody(Context ctx, Object data) throws IOException {
        ctx.contentType(getContentType());

        if (data instanceof ModelAndView) {
            ctx.output(serialize(((ModelAndView) data).model()));
        } else {
            ctx.output(serialize(data));
        }
    }

    @Override
    public Object deserializeFromBody(Context ctx) throws IOException {
        String data = ctx.bodyNew();

        if (Utils.isNotEmpty(data)) {
            return getConfig().readTree(data);
        } else {
            return null;
        }
    }
}