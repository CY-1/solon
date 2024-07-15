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
package org.noear.solon.serialization.hessian;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.ModelAndView;
import org.noear.solon.serialization.ContextSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author noear
 * @since 2.8
 */
public class HessianBytesSerializer implements ContextSerializer<byte[]> {
    private static final String label = "application/hessian";

    @Override
    public String getContentType() {
        return label;
    }

    @Override
    public boolean matched(Context ctx, String mime) {
        if (mime == null) {
            return false;
        } else {
            return mime.startsWith(label);
        }
    }

    @Override
    public String name() {
        return "hessian-bytes";
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Hessian2Output ho = new Hessian2Output(out);
        ho.writeObject(obj);
        ho.close();

        return out.toByteArray();
    }

    @Override
    public Object deserialize(byte[] data, Type toType) throws IOException {
        Hessian2Input hi = new Hessian2Input(new ByteArrayInputStream(data));
        return hi.readObject();
    }

    @Override
    public void serializeToBody(Context ctx, Object data) throws IOException {
        ctx.contentType(getContentType());

        Hessian2Output ho = new Hessian2Output(ctx.outputStream());
        if (data instanceof ModelAndView) {
            ho.writeObject(((ModelAndView) data).model());
        } else {
            ho.writeObject(data);
        }
        ho.flush();
    }

    @Override
    public Object deserializeFromBody(Context ctx) throws IOException {
        Hessian2Input hi = new Hessian2Input(ctx.bodyAsStream());
        return hi.readObject();
    }
}
