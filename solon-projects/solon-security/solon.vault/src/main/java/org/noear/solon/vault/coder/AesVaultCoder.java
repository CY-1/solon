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
package org.noear.solon.vault.coder;

import org.noear.solon.Solon;
import org.noear.solon.vault.VaultCoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author noear
 * @since 1.9
 */
public class AesVaultCoder implements VaultCoder {

    private final String charset = "UTF-8";

    private final String algorithm = "AES/ECB/PKCS5Padding";
    private final String password;

    public AesVaultCoder() {
        this.password = Solon.cfg().get("solon.vault.password");
    }

    /**
     * 加密
     */
    @Override
    public String encrypt(String str) throws Exception {
        byte[] passwordBytes = password.getBytes(charset);
        SecretKeySpec secretKey = new SecretKeySpec(passwordBytes, "AES");

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encrypted = cipher.doFinal(str.getBytes(charset));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 解密
     */
    @Override
    public String decrypt(String str) throws Exception {
        byte[] passwordBytes = password.getBytes(charset);
        SecretKey secretKey = new SecretKeySpec(passwordBytes, "AES");

        //密码
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] encrypted1 = Base64.getDecoder().decode(str);
        byte[] original = cipher.doFinal(encrypted1);

        return new String(original, charset);
    }
}
