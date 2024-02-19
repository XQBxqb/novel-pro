package com.novel.core.thread;

/**
 * @author 昴星
 * @date 2023-09-03 17:36
 * @explain
 */


public class ThreadInfo {

    public static ThreadLocal<String> userSession = new ThreadLocal<>() ;

    public static ThreadLocal<String> ipSession = new ThreadLocal<>() ;

    public static void  clear(){
        userSession.remove();;
        ipSession.remove();
    }

    public static ThreadLocal<String> getUserSession() {
        return userSession;
    }

    public static ThreadLocal<String> getIpSession() {
        return ipSession;
    }

}
