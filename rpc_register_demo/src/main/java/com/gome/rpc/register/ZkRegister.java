package com.gome.rpc.register;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author zzy
 * @create 2021-08-10-16:10
 */
public class ZkRegister {
    private static final Logger LOG = LoggerFactory.getLogger (ZkRegister.class);
    // ZK地址
    private static final String zkURL = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private static int SESSION_TIMEOUT = 2000;

    private ZooKeeper zooKeeper = null;

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void initZK() {
        try {
            this.zooKeeper = new ZooKeeper (zkURL, SESSION_TIMEOUT, new Watcher () {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    Event.EventType type = watchedEvent.getType ();
                    if (type != null && type != Event.EventType.None) {

                    LOG.info ("the current watchEvent is: {}", watchedEvent);
                    LOG.info ("the current eventType is: {}", watchedEvent.getType ());
                    LOG.info ("the watch path is: {}", watchedEvent.getPath ());
                    }
                    // 再次启动监听
                    try {
                        zooKeeper.getChildren ("/", true);
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }

                }
            });
        } catch (IOException e) {
            LOG.error (e.toString ());
        }
    }

    public static void main(String[] args) throws KeeperException {
        ZkRegister zkRegister = new ZkRegister ();
        zkRegister.initZK ();
        ZooKeeper zooKeeper = zkRegister.getZooKeeper ();
//        try {
//            String result = zooKeeper.create ("/rpc29", "rpc_register".getBytes (), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            LOG.info ("the zookeeper create result is {}", result);
//        } catch (KeeperException e) {
//            e.printStackTrace ();
//        } catch (InterruptedException e) {
//            e.printStackTrace ();
//        }

        try {

            List<String> children = zooKeeper.getChildren ("/", true);

            String result = zooKeeper.create ("/rpc38", "rpc_register".getBytes (), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            for (String child : children) {
                System.out.println (child);
            }

            String result2 = zooKeeper.create ("/rpc39", "rpc_register".getBytes (), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            for (String child : children) {
                System.out.println (child);
            }
            // 延时阻塞
//            Thread.sleep (Long.MAX_VALUE);
            Thread.sleep (2000);
// git reset HEAD -- readme.txt
            // 他理論上不應該改變
            // soft
            // soft stage
            // soft not add
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

    }

}
