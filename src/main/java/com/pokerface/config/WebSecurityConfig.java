package com.pokerface.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

	public final static String LOGIN_ID = "loginId";
    public final static String LOGIN_USER = "loginUser";
    public final static String VERIFY_CODE = "verifyCode";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
	public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/register");
        addInterceptor.excludePathPatterns("/registerSubmit");
        addInterceptor.excludePathPatterns("/checkUser");
        addInterceptor.excludePathPatterns("/loginSubmit");
        addInterceptor.excludePathPatterns("/login");
        addInterceptor.excludePathPatterns("/getVerifyCode");
        addInterceptor.excludePathPatterns("/getSmsCode");
        addInterceptor.excludePathPatterns("/");
        addInterceptor.excludePathPatterns("/intef/*");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            HttpSession session = request.getSession();
            if (session.getAttribute(LOGIN_USER) != null)
                return true;

            // 跳转登录
            String url = "login";
            response.sendRedirect(url);
            return false;
        }
    }
}