package com.gome.server.register;

/**
 * @author zzy
 * @create 2021-08-09-7:51
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 註冊服務
 */
public class ServerRegisterCenter {
    public static final Log LOG = LogFactory.getLog (ServerRegisterCenter.class);
    private static Map<String, Class> serviceRegistryMap = new HashMap<String, Class> ();

    // registerService
    public boolean registerService(Class clazz, Class clazzImpl) {
        if (clazz == null) {
            return false;
        }
        serviceRegistryMap.put (clazz.getName (), clazzImpl);
        return true;
    }

    // 獲取註冊服務名稱
    public Class getService(String name) {
        if (name == null) {
            return null;
        }
        Class clazz = serviceRegistryMap.get (name);
        return clazz;
    }
}
