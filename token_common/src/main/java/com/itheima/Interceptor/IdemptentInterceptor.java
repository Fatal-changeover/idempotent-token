package com.itheima.Interceptor;

import com.itheima.annotation.Idemptent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ThemeResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * ClassName: IdemptentInterceptor
 * Package: com.itheima.Interceptor
 * Description:
 *
 * @Author R
 * @Create 2024/5/20 17:58
 * @Version 1.0
 */
public class IdemptentInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 这段代码的作用是在preHandle方法中判断当前请求的处理器是否为HandlerMethod类型的实例。
         * 如果不是，它将直接返回true，表示允许请求继续处理。
         * 这通常是为了过滤掉一些不需要拦截器处理的非控制器方法的请求，
         * 例如静态资源请求等。这样可以提高性能，避免不必要的拦截器逻辑执行。
         */
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Idemptent annotation = method.getAnnotation(Idemptent.class);
        if(annotation != null) {
            checkToken(request);
        }
        return true;
    }

    private void checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            throw  new RuntimeException("非法参数");
        }
        Boolean delete = redisTemplate.delete(token);
        if(!delete) {
            throw new RuntimeException("重复请求");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
