package com.base.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class RsaUtils {
	private static final String CHARSET = "UTF-8";
	private static final String ALG = "RSA";
	private static final int SIZE = 2048;
	private static final String ALG_MODEL = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
	private static final String PUBLIC_KEY = "UTF-8";
	private static final String PRIVATE_KEY = "UTF-8";
	/** 最佳非對稱加密填補設定 */
	private static final OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1",
			new MGF1ParameterSpec("SHA-256"), PSource.PSpecified.DEFAULT);

	/**
	 * 加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		byte[] dataBytes = data.getBytes(CHARSET);

		Cipher cipher = Cipher.getInstance(ALG_MODEL);
		cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance(ALG)
				.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY))), oaepParams);

		return byteToBase64Str(cipher.doFinal(dataBytes));
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data) throws Exception {
		byte[] dataBytes = base64StrToBytes(data);

		Cipher cipher = Cipher.getInstance(ALG_MODEL);
		cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance(ALG)
				.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY))), oaepParams);

		return new String(cipher.doFinal(dataBytes), CHARSET);
	}

	/**
	 * 產生金鑰 (公鑰前後自行加上BEGIN END段即為PEM格式)
	 * 
	 * @throws Exception
	 */
	public static void createRsaKey() throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALG);
		kpg.initialize(SIZE, new SecureRandom());
		KeyPair kp = kpg.generateKeyPair();

		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();

		String publicKeyStr = byteToBase64Str(publicKey.getEncoded());
		String privateKeyStr = byteToBase64Str(privateKey.getEncoded());

		System.out.println("Public Key in Base64:" + publicKeyStr);
		System.out.println("Private Key in Base64:" + privateKeyStr);

	}

	/**
	 * bytes轉Base64
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteToBase64Str(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	/**
	 * Base64轉bytes
	 * 
	 * @param base64Str
	 * @return
	 */
	public static byte[] base64StrToBytes(String base64Str) {
		return Base64.getDecoder().decode(base64Str);
	}
}
