package com.gome.client.stub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author zzy
 * @create 2021-08-09-10:05
 */
public class ClientStub1 {
    private static final Log LOG = LogFactory.getLog (ClientStub1.class);

    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress addr) {

        return (T) Proxy.newProxyInstance (serviceInterface.getClassLoader (), new Class[]{serviceInterface}, new InvocationHandler () {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = null;
                ObjectOutputStream output = null;
                ObjectInputStream input = null;

                try {
                    socket = new Socket ();
                    socket.connect (addr);

                    output = new ObjectOutputStream (socket.getOutputStream ());
                    output.writeUTF (serviceInterface.getName ());
                    output.writeUTF (method.getName ());
                    output.writeObject (method.getParameterTypes ());
                    output.writeObject (args);

                    input = new ObjectInputStream (socket.getInputStream ());
                    return input.readObject ();
                } finally {
                    if (socket != null) {
                        try {
                            socket.close ();
                        } catch (IOException e) {
                            e.printStackTrace ();
                        }
                    }
                    if (output != null) {
                        try {
                            output.close ();
                        } finally {

                        }
                    }
                    if (input != null) {
                        try {
                            input.close ();
                        } catch (IOException e) {
                            e.printStackTrace ();
                        }
                    }
                }

            }
        });
    }
}
