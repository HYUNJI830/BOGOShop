package cosmetics.BOGOShop.utils;


import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class SessionUtil {
    public static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    public static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    /**
     * Member
     * @param session
     * @return
     */
    public static Long getLoginMemberId(HttpSession session) {
        return (Long) session.getAttribute(LOGIN_MEMBER_ID);
    }
    public static void setLoginMemberId(HttpSession session, Long id) {
        session.setAttribute(LOGIN_MEMBER_ID, id);
    }

    /**
     * Admin
     * @param session
     * @return
     */
    public static Long getLoginAdminId(HttpSession session) {
        return (Long) session.getAttribute(LOGIN_ADMIN_ID);
    }
    public static void setLoginAdminId(HttpSession session, Long id) {
        session.setAttribute(LOGIN_ADMIN_ID, id);
    }
    //삭제
    public static void clear(HttpSession session) {
        session.invalidate();
    }
}
