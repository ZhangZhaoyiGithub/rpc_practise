package com.gome.client;

import com.gome.client.stub.ClientStub1;
import com.gome.server.HelloService;
import com.gome.server.NumberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;

/**
 * @author zzy
 * @create 2021-08-09-23:39
 */
public class RpcTools {
    public static final Log LOG = LogFactory.getLog (RpcTools.class);

    public static void main(String[] args) {
        HelloService service = ClientStub1.getRemoteProxyObj (HelloService.class, new InetSocketAddress ("localhost", 8088));
        System.out.println (service.sayHello (args[0]));

        NumberService numberService = ClientStub1.getRemoteProxyObj (NumberService.class, new InetSocketAddress ("localhost", 8088));
        LOG.info (String.format ("the 2 args is %s", args[1]));
        Integer integer = new Integer (args[1]);
        int number = Integer.valueOf (args[1]);
        LOG.info (numberService.getNumber (number));

    }
}
