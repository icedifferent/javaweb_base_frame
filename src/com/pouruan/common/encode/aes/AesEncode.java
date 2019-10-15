package com.pouruan.common.encode.aes;

	import javax.crypto.Cipher;  
	import javax.crypto.spec.IvParameterSpec;  
	import javax.crypto.spec.SecretKeySpec;  
	  
	import sun.misc.BASE64Decoder;  
	import sun.misc.BASE64Encoder;  
	  
	/** 
	 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化； 
	 */  
	public class AesEncode {  
	    /* 
	     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。 
	     */  
	//  a0b891c2d563e4f7  
	    private String sKey = "ef0123abcd456789";  
	    private String ivParameter = "456789abcd0123ef";  
	    private static AesEncode instance = null;  
	  
	    private AesEncode() {  
	  
	    }  
	  
	    public static AesEncode getInstance() {  
	        if (instance == null)  
	            instance = new AesEncode();  
	        return instance;  
	    }  
	  
	    // 加密  
	    public String encrypt(String sSrc){  
	        String result = "";  
	        try {  
	            Cipher cipher;  
	            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	            byte[] raw = sKey.getBytes();  
	            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
	            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度  
	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
	            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));  
	            result = new BASE64Encoder().encode(encrypted);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }   
	        // 此处使用BASE64做转码。  
	        return result;  
	                  
	    }  
	  
	    // 解密  
	    public String decrypt(String sSrc){  
	        try {  
	            byte[] raw = sKey.getBytes("ASCII");  
	            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());  
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
	            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密  
	            byte[] original = cipher.doFinal(encrypted1);  
	            String originalString = new String(original, "utf-8");  
	            return originalString;  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	            return null;  
	        }  
	    }  
	  
		public static void main(String[] args){  
		    // 需要加密的字串  
		    String cSrc = "测试";  
		    System.out.println(cSrc + "  长度为" + cSrc.length());  
		    // 加密  
		    long lStart = System.currentTimeMillis();  
		    String enString = AesEncode.getInstance().encrypt(cSrc);  
		    System.out.println("加密后的字串是：" + enString + "长度为" + enString.length());  
		      
		    long lUseTime = System.currentTimeMillis() - lStart;  
		    System.out.println("加密耗时：" + lUseTime + "毫秒");  
		    // 解密  
		    lStart = System.currentTimeMillis();  
		    String DeString = AesEncode.getInstance().decrypt(enString);  
		    System.out.println("解密后的字串是：" + DeString);  
		    lUseTime = System.currentTimeMillis() - lStart;  
		    System.out.println("解密耗时：" + lUseTime + "毫秒");  
	   }  
	}	  

