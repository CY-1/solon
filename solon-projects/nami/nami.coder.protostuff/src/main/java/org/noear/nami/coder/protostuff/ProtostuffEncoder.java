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
package org.noear.nami.coder.protostuff;

import org.noear.nami.Context;
import org.noear.nami.Encoder;
import org.noear.nami.common.ContentTypes;

/**
 * @author noear
 * @since 1.2
 */
public class ProtostuffEncoder implements Encoder {
    public static final ProtostuffEncoder instance = new ProtostuffEncoder();

    @Override
    public String enctype() {
        return ContentTypes.PROTOBUF_VALUE;
    }

    @Override
    public byte[] encode(Object obj) {
        //
        //两种可能：map 或者 entity
        //
        return ProtostuffUtil.serialize(obj);
    }

    @Override
    public void pretreatment(Context ctx) {

    }
}
