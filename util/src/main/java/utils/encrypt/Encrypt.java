package utils.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Encrypt {

	public static String aesEncrypt (String content, String key) {
		try {
			Cipher aesECB = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			aesECB.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			// content.getBytes("UTF-8") 编码必须加上，否则会采用系统默认编码导致中文解码有问题
			byte[] result = aesECB.doFinal(content.getBytes("UTF-8"));
			return Base64.encodeBase64String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encrypt(String input, String key) {
		try {
			Cipher aesECB = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
			aesECB.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] result = aesECB.doFinal(input.getBytes("UTF-8"));
			return org.apache.commons.codec.binary.Base64.encodeBase64String(result);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String aesEncrypt (String content, String key, String iv) {
		try {
			Cipher aesECB = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
			aesECB.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			byte[] result = aesECB.doFinal(content.getBytes());
			return Base64.encodeBase64String(result);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String aesDecrypt(String content, String passwd) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
			SecretKeySpec key = new SecretKeySpec(passwd.getBytes(), "AES");
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, key, ivspec);// 初始化
			byte[] result = Base64.decodeBase64(content);
			return new String(cipher.doFinal(result), "UTF-8"); // 解密
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String desEncrypt(String input, String key ){
		try
		{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);// 初始化
			byte[] result = Base64.decodeBase64(input);
			return new String(cipher.doFinal(result), "UTF-8"); // 解密
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
