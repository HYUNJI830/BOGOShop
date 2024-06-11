package cosmetics.BOGOShop.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.LogRecord;

@Log4j2
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/","/login","/css/*"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //ServletRequest : HTTP 요청이 아닌 경우까지 고려해서
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse =(HttpServletResponse) servletResponse;

        try {
            log.info("인증 체크 필터 시작 {}",requestURI);
            if(isLoginCheckPath(requestURI)){
                log.info("인증 체크 로직 실행 {}",requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session == null){
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }
            filterChain.doFilter(servletRequest,servletResponse); //다음필터가 있으면 필터를 호출하고, 필터가 없으면 서블릿 호출
        }catch (Exception e){
            throw e;
        }finally {
            log.info("인증 체크 필터 종료 {}",requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크 X
     */
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whiteList,requestURI);
    }
}
