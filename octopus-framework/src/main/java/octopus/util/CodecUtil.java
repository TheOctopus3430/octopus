package octopus.util;

import org.omg.IOP.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具类
 *
 * @author zhangyu
 */
public final class CodecUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(Codec.class);


    /**
     * URL编码
     */
    public static String encodeURL(String source) {
        String target;

        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }


    /**
     * URL解码
     */
    public static String decodeURL(String source) {
        String target;

        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("decode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

}
