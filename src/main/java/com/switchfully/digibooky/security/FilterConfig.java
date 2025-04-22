package com.switchfully.digibooky.security;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<BasicAuthFilter> basicAuthFilter() {
        FilterRegistrationBean<BasicAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new BasicAuthFilter());
        registrationBean.addUrlPatterns("/digibooky/*");  // Ensure the URL pattern matches your needs
        registrationBean.setOrder(1);  // Order is set to run early
        return registrationBean;
    }

    @Bean
    public RoleBasedAuthorizationInterceptor roleBasedAuthorizationInterceptor() {
        return new RoleBasedAuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleBasedAuthorizationInterceptor())
                .addPathPatterns("/digibooky/**");  // Apply this to all the endpoints
    }
}
