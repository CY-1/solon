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
package org.noear.solon.test.annotation;

import org.noear.solon.annotation.Note;

import java.lang.annotation.*;

/**
 * 配置测试属性源
 *
 * @author noear
 * @since 1.10
 * @deprecated 2.5
 */
@Note("由 @Import 替代")
@Deprecated
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TestPropertySource {
    /**
     * 例，资源文件：classpath:demo.yml
     * 例，外部文件：./demo.yml
     * */
    String[] value();
}