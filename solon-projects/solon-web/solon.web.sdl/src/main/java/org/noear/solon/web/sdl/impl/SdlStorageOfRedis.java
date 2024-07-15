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
package org.noear.solon.web.sdl.impl;

import org.noear.redisx.RedisClient;
import org.noear.solon.Utils;
import org.noear.solon.web.sdl.SdlStorage;

import java.io.Serializable;

/**
 * 单设备登录数据服务 Redis 实现
 *
 * @author noear
 * @since 2.2
 */
public class SdlStorageOfRedis implements SdlStorage {
    RedisClient storage;

    public SdlStorageOfRedis(RedisClient redisClient) {
        this.storage = redisClient;
    }

    /**
     * 更新单点登录标识
     */
    @Override
    public String updateUserSdlKey(Serializable userId) {
        String storageKey = SdlStorage.SESSION_SDL_KEY + ":" + userId;
        String userSdlKey = Utils.guid();

        //设置用户的单点登录标识（当前会话 与 用户的'单点登录标识'，说明当前会话不是最新的登录）
        storage.open(ru -> {
            ru.key(storageKey).persist().set(userSdlKey);
        });

        return userSdlKey;
    }

    /**
     * 移除单点登录标识
     * */
    @Override
    public void removeUserSdlKey(Serializable userId) {
        String storageKey = SdlStorage.SESSION_SDL_KEY + ":" + userId;

        storage.open(ru -> {
            ru.key(storageKey).delete();
        });
    }

    /**
     * 获取单点登录标识
     */
    @Override
    public String getUserSdlKey(Serializable userId) {
        String storageKey = SdlStorage.SESSION_SDL_KEY + ":" + userId;

        return storage.openAndGet(ru -> ru.key(storageKey).get());
    }
}