
package com.cutout.server.utils;

/**
 * @project JJHServices
 * @package com.jjhgame.security
 * @filename Aes.java
 * @createtime 2017年7月27日上午10:41:52  
 * @author  fcj2593@163.com
 * @todo   TODO
 */
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/*
 * AES对称加密和解密
 */
@Component
public class Aes {
	private String key;
	private String iv;
	//加密
	private Cipher encipher;
	//解码
	private Cipher decipher;
	
	public void init() {
		try {
			key = "h1f2ae11k4e5y000";
			iv = "320211123333dsed";
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
			// 6.根据指定算法AES自成密码器
			decipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			decipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes()));
			
			
			encipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			encipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes()));
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * AES加密 成功版
	 * 
	 * @param key
	 * @param iv
	 * @param data
	 * @return
	 */
	public String encoder(String data) {
		try {
			if (encipher == null)
				init();
			byte[] encrypted = encipher.doFinal(data.getBytes());
			return Base64.encodeBase64String(BinaryToHexString(encrypted).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String hexStr = "0123456789ABCDEF";

	private String BinaryToHexString(byte[] bytes) {
		String result = "";
		String hex = "";
		for (int i = 0; i < bytes.length; i++) {
			// 字节高4位
			hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
			// 字节低4位
			hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
			result += hex;
		}
		return result;
	}

	private byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
 		for (int i = 0; i < length; i++) {
			int pos = i * 2;
 			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/*
	 * 解密 解密过程： 1.同加密1-4步 2.将加密后的字符串反纺成byte[]数组 3.将加密内容解密
	 */
	public String dncode(String content) {
		try {
			if (decipher == null)
				init();
			String tmp=content.substring(content.length()-2);
			if("==".equals(tmp))
				content=content.substring(0,content.length()-2);
			
			// 8.将加密并编码后的内容解码成字节数组
			byte[] byte_content = hexStringToBytes(new String(Base64.decodeBase64(content)));
			/*
			 * 解密
			 */
			byte[] byte_decode = decipher.doFinal(byte_content);
			String AES_decode = new String(byte_decode, "utf-8");
			return AES_decode;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		// 如果有错就返加null
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

}