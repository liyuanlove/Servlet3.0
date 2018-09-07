package com.atguigu.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * asyncSupported=true：支持异步处理
 */
@WebServlet(value = "/async", asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("主线程开始..." + Thread.currentThread() + "==>" + System.currentTimeMillis());
        //开启异步模式
        AsyncContext startAsync = req.startAsync();

        //开始异步处理业务逻辑
        startAsync.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("副线程开始..." + Thread.currentThread() + "==>" + System.currentTimeMillis());
                    sayHello();
                    startAsync.complete();
                    //获取到异步上下文
                    AsyncContext asyncContext = req.getAsyncContext();
                    //获取响应
                    ServletResponse response = asyncContext.getResponse();
                    response.getWriter().write("hello async...");
                    System.out.println("副线程结束..." + Thread.currentThread() + "==>" + System.currentTimeMillis());
                } catch (Exception e) {
                }
            }
        });

        System.out.println("主线程结束..." + Thread.currentThread() + "==>" + System.currentTimeMillis());
    }

    public void sayHello() throws Exception {
        System.out.println(Thread.currentThread() + " processing...");
        Thread.sleep(3000);
    }

}
