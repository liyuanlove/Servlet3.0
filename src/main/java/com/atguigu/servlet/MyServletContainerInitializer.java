package com.atguigu.servlet;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;

import com.atguigu.service.HelloService;

/**
 * 容器启动的时候会将@HandlesTypes指定的这个类型下面的子类、实现类、子接口等传递过来
 * 传入感兴趣的类型
 */
@HandlesTypes(value = {HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    /**
     * 应用启动的时候，会运行这个方法
     * 使用ServletContext注册Web组件（Servlet/Filter/Listener）
     * 使用代码的方式，在项目启动的时候给ServletContext里面添加组件（必须在项目启动的时候添加）
     * 1).ServletContainerInitializer得到的ServletContext
     * 2).ServletContextListener得到的ServletContext
     *
     * @param c   感兴趣的类型的所有子类型
     * @param ctx 代表当前Web应用的ServletContext，一个Web应用只有一个ServletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("感兴趣的类型：");
        for (Class<?> clz : c) {
            System.out.println(clz);
        }

        //注册Servlet
        ServletRegistration.Dynamic servlet = ctx.addServlet("userServlet", new UserServlet());
        //配置Servlet的映射信息
        servlet.addMapping("/user");

        //注册Listener
        ctx.addListener(UserListener.class);

        //注册Filter
        FilterRegistration.Dynamic filter = ctx.addFilter("userFilter", UserFilter.class);
        //配置Filter的映射信息
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }

}
