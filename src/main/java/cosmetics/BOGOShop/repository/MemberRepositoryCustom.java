package cosmetics.BOGOShop.repository;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.dto.member.LoginMemberDto;

public interface MemberRepositoryCustom {
    LoginMemberDto findMemberByIdAndPassword(String userId, String password);

    Member register(LoginMemberDto loginMemberDto);

}
