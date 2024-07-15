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
package org.noear.solon.docs.exception;

import org.noear.solon.exception.SolonException;

/**
 * 文档异常
 *
 * @author noear
 * @since 2.3
 */
public class DocException extends SolonException {
    public DocException(String message) {
        super(message);
    }

    public DocException(String message, Throwable cause) {
        super(message, cause);
    }
}
