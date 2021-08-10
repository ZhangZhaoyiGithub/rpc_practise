package com.gome.test;

import com.gome.client.stub.ClientStub;
import com.gome.client.stub.ClientStub1;
import com.gome.server.HelloService;
import com.gome.server.NumberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author zzy
 * @create 2021-08-09-9:04
 */
public class RpcClient {
    public static final Log LOG = LogFactory.getLog (RpcClient.class);
    public static void main(String[] args) throws IOException {
        HelloService service = ClientStub1.getRemoteProxyObj (HelloService.class, new InetSocketAddress ("localhost", 8088));
        System.out.println (service.sayHello ("test"));

        NumberService numberService = ClientStub1.getRemoteProxyObj (NumberService.class, new InetSocketAddress ("localhost", 8088));
        LOG.info (numberService.getNumber (2));

    }
}
