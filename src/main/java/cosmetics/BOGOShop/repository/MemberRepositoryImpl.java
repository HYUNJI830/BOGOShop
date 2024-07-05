package cosmetics.BOGOShop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.domain.QMember;
import cosmetics.BOGOShop.login.dto.Login.LoginMemberDto;
import cosmetics.BOGOShop.login.dto.Login.QLoginMemberDto;
import jakarta.persistence.EntityManager;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    public Member register(LoginMemberDto loginMemberDto){
        Member member = new Member();
        member.setUserId(loginMemberDto.getUserId());
        member.setPassword(loginMemberDto.getPassword());
        member.setStatus(loginMemberDto.getStatus());

        em.persist(member);
        return member;
    }

    @Override
    public void updatePassword(LoginMemberDto loginMemberDto) {
        long count =  queryFactory
                .update(QMember.member)
                .set(QMember.member.password,loginMemberDto.getPassword())
                .where(QMember.member.userId.eq(loginMemberDto.getUserId()))
                .execute();

        em.flush();
        em.clear();
    }


    @Override
    public LoginMemberDto findMemberByIdAndPassword(String userId, String password) {
        return queryFactory
                .select(new QLoginMemberDto(
                        QMember.member.id,
                        QMember.member.userId,
                        QMember.member.password,
                        QMember.member.isAdmin,
                        QMember.member.status
                ))
                .from(QMember.member)
                .where(QMember.member.userId.eq(userId),QMember.member.password.eq(password))
                .fetchOne();
    }



}
