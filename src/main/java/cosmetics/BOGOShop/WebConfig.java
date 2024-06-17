package cosmetics.BOGOShop;



import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Bean
//    public FilterRegistrationBean logFilter(){
//        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
//        filterRegistrationBean.setFilter(new LoginCheckFilter());
//        filterRegistrationBean.setOrder(1); // 순서 등록
//        filterRegistrationBean.addUrlPatterns("/api/*"); //필터를 적용할 URl 패턴
//        return filterRegistrationBean;
//    }

//        @Override
//        public void addInterceptors(InterceptorRegistry registry) {
//            registry.addInterceptor(new LoginCheckInterceptor())
//            .order(1) //순위를 나중으로 미루는 방법
//            .addPathPatterns("/**")
//            .excludePathPatterns("/css/**", "/*.ico", "/error","/login/*");
//}

}
