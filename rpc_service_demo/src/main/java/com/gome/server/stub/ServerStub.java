package com.gome.server.stub;

/**
 * @author zzy
 * @create 2021-08-09-7:36
 */

/**
 * 0.啟動ServerSocket,監聽端口
 * 1.處理序列化和反序列化
 * 2.根據ServiceName 反射相應的類並且完成method調用
 * 3.要去註冊中心拿到serviceName的相關類別
 */
public interface ServerStub {
    // 1. 啟動服務方法startService
    // 2. 線程子類方法，每一個request要生成相應的RunnableTask
    // 3. 關閉服務方法stopService
    // 4. 註冊方法的調用，維護一個註冊中心類

    public void startService(int port);

    public void stopService();

    public String getStatus();


}
