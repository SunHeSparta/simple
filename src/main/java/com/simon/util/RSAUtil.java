package com.simon.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

@Slf4j
public class RSAUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    protected static final Provider PROVIDER_BC;

    static {
        if (Security.getProperty(BouncyCastleProvider.PROVIDER_NAME) == null) {
            PROVIDER_BC = new BouncyCastleProvider();
            Security.addProvider(PROVIDER_BC);
        } else {
            PROVIDER_BC = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        }
    }


    /**
     * RSA最大加密密文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117; // 117

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128; // 128

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";           //SHA1withRSA MD5withRSA

    /**
     * 功能:RSA公钥加密
     *
     * @param rawData
     * @return
     */
    public static String encryptByRSA(String rawData,PublicKey publicKey) {
        try {
            Cipher encodeCipher = Cipher.getInstance(publicKey.getAlgorithm(),PROVIDER_BC);
            encodeCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int blockSize = encodeCipher.getBlockSize();
//            int blockSize = MAX_ENCRYPT_BLOCK;// 117
            byte[] data = rawData.getBytes(DEFAULT_CHARSET);
            int data_length = data.length;// 明文数据大小
            int outputSize = encodeCipher.getOutputSize(data_length);// 输出缓冲区大小
            // 计算出块的数量
            int blocksSize = (data_length + blockSize - 1) / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data_length - i * blockSize > 0) {
                if (data_length - i * blockSize > blockSize) {
                    encodeCipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    encodeCipher.doFinal(data, i * blockSize, data_length - i * blockSize, raw, i * outputSize);
                }
                i++;
            }
            return Base64.encodeBase64String(raw);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException
                | ShortBufferException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("RSA.encryptByRSA 公钥对数据{}使用字符集{}加密失败", rawData, DEFAULT_CHARSET, e);
            throw new RuntimeException(String.format("公钥对数据[%s]使用字符集[%s]加密失败", rawData, DEFAULT_CHARSET), e);
        }
    }

    /**
     * 功能:私钥RSA解密
     *
     * @param encodeData
     * @return
     */
    public static String decryptByRSA(String encodeData, PrivateKey privateKey) {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream(MAX_DECRYPT_BLOCK)) {
            Cipher decodeCipher = Cipher.getInstance(privateKey.getAlgorithm(),PROVIDER_BC);
            decodeCipher.init(Cipher.DECRYPT_MODE, privateKey);
            int blockSize = decodeCipher.getBlockSize();
//            int blockSize = MAX_DECRYPT_BLOCK;
            int j = 0;
            byte[] raw = Base64.decodeBase64(encodeData);
            while (raw.length - j * blockSize > 0) {
                bout.write(decodeCipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            byte[] decryptedData = bout.toByteArray();
            return new String(decryptedData, DEFAULT_CHARSET);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException | IOException e) {
            log.error("RSA.decryptByRSA 私钥对数据{}使用字符集{}加密失败", encodeData, DEFAULT_CHARSET, e);
            throw new RuntimeException(String.format("私钥对数据[%s]使用字符集[%s]解密失败", encodeData, DEFAULT_CHARSET), e);
        }
    }

    /**
     * 功能:使用私钥签名
     *
     * @param rawData
     * @return
     */
    public static String sign(String rawData,PrivateKey privateKey) {
        try {
            Signature signSignature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signSignature.initSign(privateKey);
            signSignature.update(rawData.getBytes(DEFAULT_CHARSET));
            return Base64.encodeBase64String(signSignature.sign());
        } catch (Exception e) {
            log.error("RSA.sign 使用私钥对数据{}进行{}签名失败", rawData, DEFAULT_CHARSET, e);
            throw new RuntimeException(String.format("使用私钥对数据[%s]进行[%s]签名失败", rawData, DEFAULT_CHARSET), e);
        }
    }

    /**
     * 功能:公钥验签
     *
     * @param rawData
     * @param sign
     * @return
     */
    public static boolean verify(String rawData, String sign, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(rawData.getBytes(DEFAULT_CHARSET));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | UnsupportedEncodingException e) {
            log.error("RSA.verify 公钥使用签名串{}对数据{}进行{}验签失败", sign, rawData, DEFAULT_CHARSET, e);
            throw new RuntimeException(String.format("公钥使用签名串[%s]对数据[%s]进行[%s]验签失败", sign, rawData, DEFAULT_CHARSET), e);
        }
    }

    /**
     * 加密文件内容
     * @param file
     * @param publicKey
     * @return
     */
    public static String encryptFile(File file,PublicKey publicKey) {
        try {
            String plainText = FileUtils.readFileToString(file, DEFAULT_CHARSET);
            return encryptByRSA(plainText,publicKey);
        } catch (IOException e) {
            log.error("读取文件(内容明文)异常:",e);
        }
        return null;
    }

    /**
     * 输入明文文件，原路径输出密文文件
     * @param file
     * @param publicKey
     * @return
     */
    public static File getEncryptFile(File file,PublicKey publicKey) {
        try {
            String plainText = FileUtils.readFileToString(file, DEFAULT_CHARSET);
            return writeStringToFile(encryptByRSA(plainText,publicKey),file.getAbsolutePath(),false);
        } catch (IOException e) {
            log.error("获取密文文件异常:",e);
        }
        return null;
    }

    /**
     * 解密文件内容
     * @param file
     * @param privateKey
     * @return
     */
    public static String decryptFile(File file,PrivateKey privateKey) {

        try {
            String encryptText = FileUtils.readFileToString(file, DEFAULT_CHARSET);
            return decryptByRSA(encryptText,privateKey);
        } catch (IOException e) {
            log.error("读取文件(内容密文)异常:",e);
        }
        return null;
    }

    /**
     * 输入密文文件，原路径输出明文文件
     * @param file
     * @param privateKey
     * @return
     */
    public static File getDecryptFile(File file,PrivateKey privateKey) {

        try {
            String encryptText = FileUtils.readFileToString(file, DEFAULT_CHARSET);
            return writeStringToFile(decryptByRSA(encryptText,privateKey),file.getAbsolutePath(),false);
        } catch (IOException e) {
            log.error("获取明文文件异常:",e);
        }
        return null;
    }

    /**
     * 写入文件
     * @param content String
     * @param filePath
     * @param append
     * @return
     * @throws IOException
     */
    public static File writeStringToFile(String content, String filePath,boolean append) throws IOException {
        File file = new File(filePath);
        FileUtils.writeStringToFile(file,content,DEFAULT_CHARSET,append);
        return file;
    }

    /**
     * 写入文件
     * @param content byte[]
     * @param filePath
     * @param append
     * @return
     * @throws IOException
     */
    public static File writeByteToFile(byte[] content, String filePath,boolean append) throws IOException {
        File file = new File(filePath);
        FileUtils.writeByteArrayToFile(file,content,append);
        return file;
    }


//    /**
//     * 功能:密钥转成字符串
//     *
//     * @param key
//     * @return
//     */
//    public static String keyToString(Key key) {
//        try {
//            return Base64.encode(key.getEncoded());
//        } catch (Exception e) {
//            log.error("RSA.keyToString 输出密钥{}字符串失败", key.getFormat(), e);
//            throw new RuntimeException(String.format("输出密钥[%s]字符串失败", key.getFormat()), e);
//        }
//    }

//    /**
//     * 功能:公钥字符串转公钥
//     *
//     * @param publicKeyStr
//     * @return
//     */
//    public static PublicKey string2PublicKey(String publicKeyStr) {
//        PublicKey publicKey = null;
//        X509EncodedKeySpec bobPubKeySpec = null;
//        try {
//            bobPubKeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyStr));
//            // RSA对称加密算法
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            // 取公钥匙对象
//            publicKey = keyFactory.generatePublic(bobPubKeySpec);
//        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
//            log.error("RSA.string2PublicKey 公钥{}加载失败", publicKeyStr, e);
//            throw new RuntimeException(String.format("公钥[%s]加载失败", publicKeyStr), e);
//        }
//        return publicKey;
//    }

//    /**
//     * 功能: 私钥字符串转私钥
//     *
//     * @param privateKyeStr
//     * @return
//     */
//    public static PrivateKey string2PrivateKey(String privateKyeStr) {
//        PrivateKey privateKey = null;
//        PKCS8EncodedKeySpec priPKCS8 = null;
//        try {
//            priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKyeStr));
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            privateKey = keyFactory.generatePrivate(priPKCS8);
//        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
//            log.error("RSA.string2PrivateKey 私钥{}加载失败", privateKyeStr, e);
//            throw new RuntimeException(String.format("私钥[%s]加载失败", privateKyeStr), e);
//        }
//        return privateKey;
//    }

    /**
     * 功能: 根据路径加载公钥

     * @return
     */
    private static PublicKey getPublicKeyFromX509(File publicKeyFile) {
        try (InputStream fin = getInputStream(publicKeyFile)) {
            CertificateFactory f = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) f.generateCertificate(fin);
            return certificate.getPublicKey();
        } catch (IOException | CertificateException e) {
            log.error("RSA.getPublicKeyFromX509 加载公钥证书路径{}失败", publicKeyFile.getAbsolutePath(), e);
            throw new RuntimeException(String.format("加载公钥证书路径[%s]失败", publicKeyFile.getAbsoluteFile()), e);
        }
    }

    private static InputStream getInputStream(File keyFile) {
        try {
            return new FileInputStream(keyFile);
        } catch (FileNotFoundException e) {
            log.error("RSA.getInputStream 文件{}加载失败", keyFile.getAbsolutePath(), e);
            throw new RuntimeException(String.format("文件[%s]加载失败", keyFile.getAbsolutePath()), e);
        }
    }

    /**
     * 功能: 加载私钥证书
     *
     * @param password
     * @return
     */
    private static PrivateKey getPrivateKey(File privateKeyFile, String password) {
        try (InputStream is = getInputStream(privateKeyFile)) {
            KeyStore store = KeyStore.getInstance("pkcs12");
            char[] passwordChars = password.toCharArray();
            store.load(is, passwordChars);
            Enumeration<String> e = store.aliases();
            if (e.hasMoreElements()) {
                String alias = e.nextElement();
                return (PrivateKey) store.getKey(alias, passwordChars);
            }
            throw new RuntimeException(String.format("无法加载私钥证书路径[%s]及密码[%s],请核对证书文件及密码", privateKeyFile.getAbsoluteFile(), password));
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException
                | UnrecoverableKeyException e) {
            log.error("RSA.getInputStream 加载私钥证书路径{}及密码{}失败", privateKeyFile.getAbsolutePath(), password, e);
            throw new RuntimeException(String.format("加载私钥证书路径[%s]及密码[%s]失败", privateKeyFile.getAbsoluteFile(), password), e);
        }
    }
}
