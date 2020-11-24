package octopus.util;

import org.apache.commons.lang3.ArrayUtils;

public class ArrayUtil {


    /**
     * 判断数组是否非空
     */
    public static boolean isNotEmpty(Object[] array) {
        return ArrayUtils.isNotEmpty(array);
    }

    /**
     * 判断数组为空
     */
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }


}
