package net.yundingwei.dszw.app.common.constants;

import net.yundingwei.dszw.app.common.utils.FileUtils;

/**
 * Created by admin on 2016/4/28.
 */
public class Config {
    public static final String CACHE_SHARED_NAME = "KTX_SHARED_CACHE";
    public static String DEVICE_PATH = null;

    static {
        DEVICE_PATH = FileUtils.getSDRootDir() + "/dszw/device";
    }
}
