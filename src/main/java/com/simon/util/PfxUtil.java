package com.simon.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import java.util.Objects;

public class PfxUtil {
	private static Logger logger = LoggerFactory.getLogger(PfxUtil.class);


	private volatile static PrivateKey privateKey = null;
	private volatile static PublicKey publicKey = null;

	private static PrivateKey readPrivateKey(File file, String pwdStr) throws Exception {
		KeyStore ks = KeyStore.getInstance("PKCS12");
		if (StringUtils.isEmpty(pwdStr)) {
			throw new Exception("私钥密码为空，请检查");
		}
		ks.load(new FileInputStream(file), pwdStr.toCharArray());
		Enumeration<String> enums = ks.aliases();
		String keyAlias = null;
		if (enums.hasMoreElements()) {
			keyAlias = enums.nextElement();
		}
		return  (PrivateKey) ks.getKey(keyAlias, pwdStr.toCharArray());
	}

	public static PrivateKey getPrivateKey(File file, String pwdStr) {
		if (Objects.isNull(privateKey)) {
			synchronized(PfxUtil.class) {
				if (Objects.isNull(privateKey)) {
					try {
						privateKey = readPrivateKey(file, pwdStr);
					} catch (Exception e) {
						logger.error("读取私钥失败:", e);
					}
				}
			}
		}
		return privateKey;
	}
	
	private static PublicKey readPublicKey(File file, String pwdStr) throws Exception {
		KeyStore ks = KeyStore.getInstance("PKCS12");
		char[] password = null;
		if (pwdStr != null && !pwdStr.trim().equals("")) {
			password = pwdStr.toCharArray();
		}
		ks.load(new FileInputStream(file), password);
		Enumeration<String> enums = ks.aliases();
		String keyAlias = null;
		if (enums.hasMoreElements()) {
			keyAlias = enums.nextElement();
		}
		Certificate certificate = ks.getCertificate(keyAlias);
		return certificate.getPublicKey();
	}

	private static PublicKey readPublicKey(File cerFile) throws Exception {
		// 读取证书文件
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(cerFile);

		//生成一个证书对象并使用从输入流 inStream 中读取的数据对它进行初始化。
		Certificate c = cf.generateCertificate(in);
		return c.getPublicKey();

	}

	public static PublicKey getPublicKey(File file) {
		if (Objects.isNull(publicKey)) {
			synchronized(PfxUtil.class) {
				if (Objects.isNull(publicKey)) {
					try {
						publicKey = readPublicKey(file);
					} catch (Exception e) {
						logger.error("读取公钥失败:", e);
					}
				}
			}
		}
		return publicKey;
	}
}
