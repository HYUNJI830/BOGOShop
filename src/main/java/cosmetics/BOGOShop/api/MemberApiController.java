package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.dto.*;
import cosmetics.BOGOShop.dto.Login.JwtToken;
import cosmetics.BOGOShop.dto.Login.SignInDto;
import cosmetics.BOGOShop.dto.Login.SignUpDto;
import cosmetics.BOGOShop.dto.member.*;
import cosmetics.BOGOShop.service.MemberService;
import cosmetics.BOGOShop.utils.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController//(Controller + ResponseBody : 데이터 자체를 Json으로 바로 보냄)
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/members")
    public Result  members(){
        List<Member> findMembers = memberService.findMembers();
        //엔티티 > DTO 반환
        List<MemberDto> collect = findMembers.stream()
                .map(m-> new MemberDto(m)) //각 요소를 변환 (Member 엔티티 -> Member DTO)
                .collect(Collectors.toList()); //스트림을 컬렉션(+리스트)으로 변환

        return new Result(collect.size(),collect);
    }
    @PostMapping("/api/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request)
    {
        Member member = new Member();
        member.setName(request.getName());
        member.setAge(request.getAge());
        member.setPhone(request.getPhone());
        member.setAddress(request.getAddress());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/members/{id}") //멱등성
    public UpdateMemberResponse updateMember(@PathVariable("id") long id, @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
    @PostMapping("/members/sign-up")
    public ResponseEntity<MemberDto> signUp(@RequestBody @Valid SignUpDto signUpDto){
        MemberDto savedMemberDto = memberService.signUp(signUpDto);
        return ResponseEntity.ok(savedMemberDto);
    }

    @PostMapping("/members/sign-in")
    public JwtToken signIn(@RequestBody @Valid SignInDto signInDto) {
        String userId = signInDto.getUserId();
        String password = signInDto.getPassword();
        JwtToken jwtToken = memberService.signIn(userId, password);
        return jwtToken;
    }

    @PostMapping("/members/user")
    public String loginUser() {
        return SecurityUtil.getCurrentLoginUserId();
    }

    @PostMapping("/members/logout")
    public ResponseEntity<MemberDto> logout(){
        return null;
    }

}
