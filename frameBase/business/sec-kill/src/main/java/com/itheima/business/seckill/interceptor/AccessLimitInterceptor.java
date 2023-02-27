//package com.itheima.business.seckill.interceptor;
//
//import com.itheima.business.seckill.annotation.AccessLimit;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.AsyncHandlerInterceptor;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Objects;
//
///**
// * 限流拦截器
// */
//@Component
//@Slf4j
//public class AccessLimitInterceptor implements HandlerInterceptor {
//    /**
//     * Intercept the execution of a handler. Called after HandlerMapping determined
//     * an appropriate handler object, but before HandlerAdapter invokes the handler.
//     * <p>DispatcherServlet processes a handler in an execution chain, consisting
//     * of any number of interceptors, with the handler itself at the end.
//     * With this method, each interceptor can decide to abort the execution chain,
//     * typically sending an HTTP error or writing a custom response.
//     * <p><strong>Note:</strong> special considerations apply for asynchronous
//     * request processing. For more details see
//     * {@link AsyncHandlerInterceptor}.
//     * <p>The default implementation returns {@code true}.
//     *
//     * @param request  current HTTP request
//     * @param response current HTTP response
//     * @param handler  chosen handler to execute, for type and/or instance evaluation
//     * @return {@code true} if the execution chain should proceed with the
//     * next interceptor or the handler itself. Else, DispatcherServlet assumes
//     * that this interceptor has already dealt with the response itself.
//     * @throws Exception in case of errors
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        //拦截器拦截有该注解的请求，没有的则放行，有的则将该请求地址加入redis，以url作为key，并设置过期时间。每有一次求情则让该key的值增大一次
//        if (handler instanceof HandlerMethod) {
//            //查看该方法上是否有@AccessLimit注解
//            HandlerMethod hm = (HandlerMethod) handler;
//            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
//            //没有@AccessLimit注解 直接放行
//            if (Objects.isNull(accessLimit)) {
//                return true;
//            }
//            //获取注解的参数
//            int seconds = accessLimit.seconds();
//            int maxCount = accessLimit.maxCount();
//        }
//        return true;
//    }
//}
