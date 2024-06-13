package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Address;
import cosmetics.BOGOShop.dto.Login.SignUpDto;
import cosmetics.BOGOShop.dto.member.MemberDto;
import cosmetics.BOGOShop.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Transactional
public class MemberControllerTest {

    @Autowired
    MemberService memberService;
    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    int randomServerPort;

    private SignUpDto signUpDto;
    @BeforeEach
    void beforeEach(){
        //Member 회원가입
        signUpDto = SignUpDto.builder()
                .userId("userE")
                .name("이이네")
                .age(28L)
                .phone("010-123-4567")
                .address(new Address("서울","동대문구","123길"))
                .build();
    }

    @Test
    public void signUp() {
        //api 요청 설정
        String url = "http://localhost" + randomServerPort + "/members/sign-up";
        ResponseEntity<MemberDto> responseEntity = testRestTemplate.postForEntity(url,signUpDto, MemberDto.class);

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        MemberDto savedMemberDto = responseEntity.getBody();
        Assertions.assertThat(savedMemberDto.getUserId()).isEqualTo(signUpDto.getUserId());
    }
}