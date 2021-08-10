package com.gome.server.impl;

import com.gome.server.HelloService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zzy
 * @create 2021-08-09-7:32
 */
public class HelloServiceImpl implements HelloService {
    public static final Log LOG = LogFactory.getLog (HelloServiceImpl.class);
    public String sayHello(String name) {
        if (name == null) {
            return "";
        }
        LOG.info (String.format ("the param is %s for HelloService", name));
        return String.format ("hi, %s", name);
    }
}
