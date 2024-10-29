package com.kopw.spring.config;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class GenerateSecretKey {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256); // 256 bits
        SecretKey secretKey = keyGen.generateKey();
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Key: " + base64Key);
    }
}
