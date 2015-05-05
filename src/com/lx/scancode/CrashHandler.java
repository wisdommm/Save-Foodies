package com.lx.scancode;


import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.util.Log;


public class CrashHandler implements UncaughtExceptionHandler {   
    /** 是否开启日志输出,在Debug状态下开启,  
     * 在Release状态下关闭以提示程序性能  
     * */  
    public static final boolean DEBUG = true;   
    /** 系统默认的UncaughtException处理类 */  
    private Thread.UncaughtExceptionHandler mDefaultHandler;   
    /** CrashHandler实例 */  
    private static CrashHandler INSTANCE;   
    /** 程序的Context对象 */  
   private Context mContext;   
    /** 保证只有一个CrashHandler实例 */  
    private CrashHandler() {}   
    /** 获取CrashHandler实例 ,单例模式*/  
    public static synchronized CrashHandler getInstance() {   
        if (INSTANCE == null) {   
            INSTANCE = new CrashHandler();   
        }   
        return INSTANCE;   
    }   
    
    /**  
     * 初始化,注册Context对象,  
     * 获取系统默认的UncaughtException处理器,  
     * 设置该CrashHandler为程序的默认处理器  
     *   
     * @param ctx  
     */  
    public void init(Context ctx) {   
//        mContext = ctx;   
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();   
        Thread.setDefaultUncaughtExceptionHandler(this);   
    }   
    
    /**  
     * 当UncaughtException发生时会转入该函数来处理  
     */  
    @Override  
    public void uncaughtException(Thread thread, Throwable ex) {   
        if (!handleException(ex) && mDefaultHandler != null) {   
            //如果用户没有处理则让系统默认的异常处理器来处理   
            mDefaultHandler.uncaughtException(thread, ex);   
        } else {  //如果自己处理了异常，则不会弹出系统自带错误对话框，则需要手动退出app 
			//别忘了手动退出,自已杀死自已的线程
            android.os.Process.killProcess(android.os.Process.myPid());   
            System.exit(10); 
           
        }   
    }   
    
    /**  
     * 自定义错误处理,收集错误信息  
     * 发送错误报告等操作均在此完成.  
     * 开发者可以根据自己的情况来自定义异常处理逻辑  
     * @return  
     * true代表处理该异常，不再向上抛异常， 
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理， 
     * 简单来说就是true不会弹出那个错误提示框，false就会弹出 
     */  
    private boolean handleException(final Throwable ex) {   
    	if (ex == null) {   
            return false;   
        }   
//        final String msg = ex.getLocalizedMessage();   
        final StackTraceElement[] stack = ex.getStackTrace(); 
        final String message = ex.getMessage();
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < stack.length; i++) { 
        	sb.append(stack[i].toString());
        } 
        Log.w("FADA:", "CrashHandler stack:"+sb.toString()+"-----message:"+message);
          //使用Toast来显示异常信息  ,并把异常保存在SD卡中,再上传至服务器. 
//          new Thread() {   
//              @Override  
//              public void run() {   
//  		//Looper用于封装了android线程中的消息循环，默认情况下一个线程是不存在消息循环（message loop）的，
//  		//需要调用Looper.prepare()来给线程创建一个消息循环，调用Looper.loop()来使消息循环起作用，从消息队列里取消息，处理消息。
//  		
//                  Looper.prepare();   
//  //   当然也可以弹一个Toast提示:Toast.makeText(mContext, "程序出错啦:" + message, Toast.LENGTH_LONG).show();  
//   
////         保存异常到SD卡中:可以只创建一个文件，以后全部往里面append然后发送，这样就会有重复的信息，个人不推荐 
//                  String fileName = "crash-" + System.currentTimeMillis()  + ".log";   
//                  File file = new File(Environment.getExternalStorageDirectory(), fileName); 
//                  try { 
//                      FileOutputStream fos = new FileOutputStream(file,true); 
//                      fos.write(message.getBytes()); 
//                      for (int i = 0; i < stack.length; i++) { 
//                          fos.write(stack.toString().getBytes()); 
//                      } 
//                      fos.flush(); 
//                      fos.close(); 
//                  } catch (Exception e) { 
//                  } 
//  				//注：写在Looper.loop()之后的代码不会被立即执行，当调用后mHandler.getLooper().quit()后，loop才会中止，
//  				//其后的代码才能得以运行。Looper对象通过MessageQueue来存放消息和事件。一个线程只能有一个Looper，对应一个MessageQueue
//                  Looper.loop();   
//              }   
//      
//          }.start();   
          
//          MobclickAgent.reportError(mContext,"stack=:"+sb.toString()+"-----message:"+message);
          return false;   
			}
          
}