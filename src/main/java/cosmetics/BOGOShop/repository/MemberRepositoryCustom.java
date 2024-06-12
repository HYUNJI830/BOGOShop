package cosmetics.BOGOShop.repository;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.dto.Login.LoginMemberDto;

public interface MemberRepositoryCustom {
    LoginMemberDto findMemberByIdAndPassword(String userId, String password);

    Member register(LoginMemberDto loginMemberDto);

    void updatePassword(LoginMemberDto loginMemberDto);
}
