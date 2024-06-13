package cosmetics.BOGOShop.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getCurrentLoginUserId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String LoginUserId = authentication.getName();

        if(authentication == null || authentication.getName() == null){
          throw new RuntimeException("인증 정보가 없습니다.");
        }

        return authentication.getName();
    }
}
