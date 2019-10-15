package com.pouruan.common.encode.aes;

	import javax.crypto.Cipher;  
	import javax.crypto.spec.IvParameterSpec;  
	import javax.crypto.spec.SecretKeySpec;  
	  
	import sun.misc.BASE64Decoder;  
	import sun.misc.BASE64Encoder;  
	  
	/** 
	 * AES ��һ�ֿ�������㷨�����û���������Ϣ���ܴ��� ��ԭʼ���ݽ���AES���ܺ��ڽ���Base64����ת���� 
	 */  
	public class AesEncode {  
	    /* 
	     * �����õ�Key ������26����ĸ��������� �˴�ʹ��AES-128-CBC����ģʽ��key��ҪΪ16λ�� 
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
	  
	    // ����  
	    public String encrypt(String sSrc){  
	        String result = "";  
	        try {  
	            Cipher cipher;  
	            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	            byte[] raw = sKey.getBytes();  
	            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
	            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��  
	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
	            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));  
	            result = new BASE64Encoder().encode(encrypted);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }   
	        // �˴�ʹ��BASE64��ת�롣  
	        return result;  
	                  
	    }  
	  
	    // ����  
	    public String decrypt(String sSrc){  
	        try {  
	            byte[] raw = sKey.getBytes("ASCII");  
	            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());  
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
	            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// ����base64����  
	            byte[] original = cipher.doFinal(encrypted1);  
	            String originalString = new String(original, "utf-8");  
	            return originalString;  
	        } catch (Exception ex) {  
	            ex.printStackTrace();  
	            return null;  
	        }  
	    }  
	  
		public static void main(String[] args){  
		    // ��Ҫ���ܵ��ִ�  
		    String cSrc = "����";  
		    System.out.println(cSrc + "  ����Ϊ" + cSrc.length());  
		    // ����  
		    long lStart = System.currentTimeMillis();  
		    String enString = AesEncode.getInstance().encrypt(cSrc);  
		    System.out.println("���ܺ���ִ��ǣ�" + enString + "����Ϊ" + enString.length());  
		      
		    long lUseTime = System.currentTimeMillis() - lStart;  
		    System.out.println("���ܺ�ʱ��" + lUseTime + "����");  
		    // ����  
		    lStart = System.currentTimeMillis();  
		    String DeString = AesEncode.getInstance().decrypt(enString);  
		    System.out.println("���ܺ���ִ��ǣ�" + DeString);  
		    lUseTime = System.currentTimeMillis() - lStart;  
		    System.out.println("���ܺ�ʱ��" + lUseTime + "����");  
	   }  
	}	  

