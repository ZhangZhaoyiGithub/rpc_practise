package com.gome.server.impl;

import com.gome.server.NumberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zzy
 * @create 2021-08-09-7:38
 */
public class NumberServiceImpl implements NumberService {
    public static final Log LOG = LogFactory.getLog (NumberServiceImpl.class);
    public int getNumber(int number) {
        LOG.info (String.format ("the number param is %d for NumberServiceImpl", number));
        switch (number) {
            case 0:
                return 10;
            case 1:
                return 11;
            case 2:
                return 12;
            default:
                return 100;
        }
    }
}
