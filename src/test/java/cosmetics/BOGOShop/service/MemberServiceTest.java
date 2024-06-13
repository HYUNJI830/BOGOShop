package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.repository.MemberRepositoryOLD;
import jakarta.persistence.EntityManager;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepositoryOLD memberRepository;
    @Autowired
    EntityManager em;

    @Test
    //@Rollback(value = false)
    public void 회원가입() throws Exception{
        //given(요건)
        Member member = new Member();
        member.setName("Lee");

        //when(조건)
        Long saveId = memberService.join(member);

        //then(결과)
        //em.flush();
        Assert.assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    //@Rollback(value = false)
    public void 중복_회원_예외() throws Exception{
        //given(요건)
        Member member1 = new Member();
        member1.setName("Lee");

        Member member2 = new Member();
        member2.setName("Lee");

        //when(조건)
        memberService.join(member1);
        memberService.join(member2); //예외가 발생
        //then(결과)
        fail("예외가 발생해야 한다.");
    }
}