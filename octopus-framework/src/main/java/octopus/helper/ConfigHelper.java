package octopus.helper;


import octopus.config.ConfigConstant;
import octopus.util.PropsUtil;

import java.util.Properties;

/**
 * 属性文件助手类
 *
 * @author zhangyu
 */
public class ConfigHelper {

    private static final Properties props = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);


    public static String getDriver() {
        return PropsUtil.getString(props, ConfigConstant.JDBC_DRIVER);
    }

    public static String getUrl() {
        return PropsUtil.getString(props, ConfigConstant.JDBC_URL);
    }

    public static String getUsername() {
        return PropsUtil.getString(props, ConfigConstant.JDBC_USERNAME);
    }

    public static String getPassword() {
        return PropsUtil.getString(props, ConfigConstant.JDBC_PASSWORD);
    }

    public static String getBasePackage() {
        return PropsUtil.getString(props, ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getJspPath() {
        return PropsUtil.getString(props, ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
    }

    public static String getAssetPath() {
        return PropsUtil.getString(props, ConfigConstant.APP_ASSET_PATH,"/asset/");
    }
}
