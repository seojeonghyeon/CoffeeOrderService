package com.justin.teaorderservice.infra.config;

import com.justin.teaorderservice.infra.argumentresolver.LoginMemberArgumentResolver;
import com.justin.teaorderservice.infra.interceptor.LogInterceptor;
import com.justin.teaorderservice.infra.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**",
                        "/*.ico",
                        "/error"
                );
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/view/order/**")
                .excludePathPatterns(
                        "/",
                        "/css/**",
                        "/*.ico",
                        "/error",
                        "/api/**",
                        "/view/order/v1/login",
                        "/view/order/v1/login/logout",
                        "/view/order/v1/members/add",
                        "/view/order/v1/members/{userId}/detail",
                        "/view/order/v1/home"
                );
    }
}
