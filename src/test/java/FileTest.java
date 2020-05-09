import org.apache.commons.lang3.ArrayUtils;

import java.io.*;

/**
 * Create by SunHe on 2020/4/1
 */
public class FileTest {

    private static final String fromPath = "/Users/edz/ljb_sftp/10001";
//    private static final String toPath = "/Users/edz/ljb_sftp/repay/";
    private static final String toPath = "/Users/edz/ljb_sftp/claims/";
//    public static final String fileNameContains = "10_xinliu_repaydetail_202003";
    public static final String fileNameContains = "10_xinliu_compensatorydetail";

    public static void main(String[] args) throws IOException {
        File file = new File(fromPath);
        copyFiles(file);
    }

    private static void copyFiles(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || ArrayUtils.isEmpty(files)) {
                return;
            }
            for (File f : files) {
                copyFiles(f);
            }
        } else if (file.getName().contains(fileNameContains)) {
            File fi = new File(toPath + file.getName());
            copyFileUsingFileStreams(file, fi);
        }
    }

    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        try (InputStream input = new FileInputStream(source);
             OutputStream output = new FileOutputStream(dest)) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
    }

}
