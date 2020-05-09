import com.simon.util.PfxUtil;
import com.simon.util.RSAUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Create by SunHe on 2020/4/1
 */
public class LjbcParseTest {

//    private static final String path = "/Users/edz/ljb_sftp/repay/";
//    private static final String path = "/Users/edz/ljb_sftp/claims/";
    private static final String path = "/Users/edz/ljb_sftp/compensatory/";
    private static final String parsePath = "/Users/edz/ljb_sftp/compensatory/parse/";
    private static final String privateKeyFilePath = "/Users/edz/workspace/simple/etc/private_key.p12";
    private static final String privateKeyPwd = "xinliu123";
    private static final String bak = ".bak";
    private static final String txt = ".txt";

    public static void main(String[] args) throws Exception {
        File file = new File(path);
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            if (!f.getName().endsWith(bak)) {
                continue;
            }
//            decodeFile(f);
//            boolean b = f.renameTo(new File(f.getAbsolutePath() + ".bak"));
            boolean b = f.renameTo(new File(path + f.getName().replace(bak, "")));
        }
    }

    private static void stringToFile(String s, String fileName) throws IOException {
        FileOutputStream fos = null;
        try {
            File txt = new File(fileName);
            if (!txt.exists()) {
                boolean newFile = txt.createNewFile();
            }
            byte[] bytes = s.getBytes();
            int b = bytes.length;
            fos = new FileOutputStream(txt);
            fos.write(bytes, 0, b);
            fos.write(bytes);
        } finally {
            assert fos != null;
            fos.close();
        }

    }

    private static void decodeFile(File file) throws Exception {
        File privateKeyFile = new File(privateKeyFilePath);
        String encryptText = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        String decryptText = RSAUtil.decryptByRSA(encryptText, PfxUtil.getPrivateKey(privateKeyFile, privateKeyPwd));
        stringToFile(decryptText, parsePath + file.getName());
    }

//    private List<String> decodeFile(File file) throws Exception {
//        File privateKeyFile = new File(privateKeyFilePath);
//        String encryptText = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
//        String decryptText = RSAUtil.decryptByRSA(encryptText, PfxUtil.getPrivateKey(privateKeyFile, privateKeyPwd));
//        if (StringUtils.isBlank(decryptText)) {
//            return new ArrayList<>();
//        }
//        String[] decArray = decryptText.split("\r\n");
//        if (ArrayUtils.isEmpty(decArray)) {
//            return new ArrayList<>();
//        }
//        return Stream.of(decArray).collect(Collectors.toList());
//    }

}
