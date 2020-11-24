package octopus.util;

import java.io.*;

/**
 * 流工具类
 *
 * @author zhangyu
 */
public final class StreamUtil {

    /**
     * 从输入流中获取字符串
     */
    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            if (((line = reader.readLine()) != null)) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
