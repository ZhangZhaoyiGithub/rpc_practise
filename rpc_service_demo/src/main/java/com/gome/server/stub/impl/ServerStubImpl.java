package com.gome.server.stub.impl;

import com.gome.server.HelloService;
import com.gome.server.NumberService;
import com.gome.server.impl.HelloServiceImpl;
import com.gome.server.impl.NumberServiceImpl;
import com.gome.server.register.ServerRegisterCenter;
import com.gome.server.stub.ServerStub;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zzy
 * @create 2021-08-09-7:57
 */
public class ServerStubImpl implements ServerStub {
    // 1. 啟動服務方法startService
    // 2. 線程子類方法，每一個request要生成相應的RunnableTask
    // 3. 關閉服務方法stopService
    // 4. 註冊方法的調用，維護一個註冊中心類

    public static final Log LOG = LogFactory.getLog (ServerStubImpl.class);

    private ServerRegisterCenter serverRegisterCenter;

    private ExecutorService executorService;

    private ServerSocket ss;

    private static boolean isRunning = false;

    // 計數器
    private static AtomicInteger taskCount = new AtomicInteger (0);

    public ServerStubImpl() {
    }

    public ServerStubImpl(ServerRegisterCenter serverRegisterCenter) {
        this.serverRegisterCenter = serverRegisterCenter;
    }

    /**
     *
     */
    @Override
    public void startService(int port) {
        // 創建線程池
        executorService = createExecutors ();

        // 初始化ServerSocket
        try {
            ss = new ServerSocket ();
            ss.bind (new InetSocketAddress (port));

            boolean flag = true;
            ServerStubImpl.isRunning = true;
            LOG.info ("the service is starting");
            while (flag) {
                executorService.execute (new RpcTask (ss.accept ()));
            }

        } catch (IOException e) {
            LOG.error ("init serverSocket is fail");
            LOG.error (e);
        } finally {
            try {
                ss.close ();
            } catch (IOException e) {
                LOG.error (e);
            }
        }
    }

    /**
     * 創建requestTask對應的執行線程池
     *
     * @return
     */
    private ExecutorService createExecutors() {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder ();
        ThreadFactory tf = threadFactoryBuilder.setNameFormat ("the rpc serverStub #%d").build ();
        return Executors.newFixedThreadPool (Runtime.getRuntime ().availableProcessors (), tf);
    }

    /**
     * 停止服務
     */
    @Override
    public void stopService() {
        ServerStubImpl.isRunning = false;

        try {
            executorService.shutdown ();
            executorService.awaitTermination (15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOG.error (e);
            executorService.shutdownNow ();
        }
    }

    /**
     * 獲取狀態
     *
     * @return
     */
    @Override
    public String getStatus() {
        if (ServerStubImpl.isRunning) {
            return "the service is running";
        }
        return "the service is stop";
    }

    public ServerRegisterCenter getServerRegisterCenter() {
        return serverRegisterCenter;
    }

    public void setServerRegisterCenter(ServerRegisterCenter serverRegisterCenter) {
        this.serverRegisterCenter = serverRegisterCenter;
    }

    private class RpcTask implements Runnable {
        private Socket socket;

        public RpcTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            // 處理socket收到的內容
            ObjectInputStream objInput = null;
            ObjectOutputStream objOut = null;
            try {
                InputStream in = socket.getInputStream ();
                objInput = new ObjectInputStream (in);
                String serviceName = objInput.readUTF ();
                String methodName = objInput.readUTF ();
                // 參數類型
                Class<?>[] parameterTypes = (Class<?>[]) objInput.readObject ();
                // 參數
                Object[] params = (Object[]) objInput.readObject ();
                // 從註冊中心獲取類名稱，反射調用方法
                Class clazz = serverRegisterCenter.getService (serviceName);
                if (clazz == null) {
                    LOG.info (String.format ("the serviceName: %s is not exists", serviceName));
                    throw new ClassNotFoundException (serviceName + " not found");
                }

                try {
                    Method method = clazz.getMethod (methodName, parameterTypes);
                    Object invokeResult = method.invoke (clazz.newInstance (), params);

                    objOut = new ObjectOutputStream (socket.getOutputStream ());
                    objOut.writeObject (invokeResult);

                } catch (NoSuchMethodException e) {
                    LOG.error (e);
                } catch (IllegalAccessException e) {
                    LOG.error (e);
                } catch (InstantiationException e) {
                    e.printStackTrace ();
                } catch (InvocationTargetException e) {
                    e.printStackTrace ();
                }

            } catch (IOException e) {
                LOG.error (e);
            } catch (ClassNotFoundException e) {
                LOG.error (e);
            } finally {
                int count = taskCount.incrementAndGet ();
                LOG.info (String.format ("the task number is %d", count));

                // 關閉流
                if (objOut != null) {
                    try {
                        objOut.close ();
                    } catch (IOException e) {
                        LOG.error (e);
                    }
                }
                if (objInput != null) {
                    try {
                        objInput.close ();
                    } catch (IOException e) {
                        LOG.error (e);
                    }
                }
                if (socket != null) {
                    try {
                        socket.close ();
                    } catch (IOException e) {
                        LOG.error (e);
                    }
                }
            }
        }

    }


    public static void main(String[] args) {
        // 啟動註冊中心
        ServerRegisterCenter serverRegisterCenter = new ServerRegisterCenter ();
        serverRegisterCenter.registerService (HelloService.class, HelloServiceImpl.class);
        serverRegisterCenter.registerService (NumberService.class, NumberServiceImpl.class);

        LOG.info (serverRegisterCenter.getService (HelloService.class.getName ()));
        // 啟動ServiceStub
        ServerStubImpl serverStub = new ServerStubImpl (serverRegisterCenter);
        serverStub.startService (8088);
    }

}
