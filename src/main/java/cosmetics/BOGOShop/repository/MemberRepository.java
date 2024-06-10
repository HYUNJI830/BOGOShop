package cosmetics.BOGOShop.repository;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.dto.member.LoginMemberDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long>,MemberRepositoryCustom {
    List<Member> findByName(String name);

}
