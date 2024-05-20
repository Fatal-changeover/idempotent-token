package com.itheima.Interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * ClassName: FeignInterceptor
 * Package: com.itheima.Interceptor
 * Description:
 *
 * @Author R
 * @Create 2024/5/20 17:05
 * @Version 1.0
 */
@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //传递令牌
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            if(request!=null) {
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String hearName = headerNames.nextElement();
                    if("token".equals(hearName)) {
                        String headerValue = request.getHeader(hearName);
                        //传递token
                        requestTemplate.header(hearName,headerValue);
                    }
                }
            }
        }
    }
}
