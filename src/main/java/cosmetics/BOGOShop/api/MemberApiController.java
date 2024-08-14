package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.document.MemberControllerDocs;
import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.dto.*;
import cosmetics.BOGOShop.jwt.dto.JwtToken;
import cosmetics.BOGOShop.login.dto.Login.SignInDto;
import cosmetics.BOGOShop.login.dto.Login.SignUpDto;
import cosmetics.BOGOShop.dto.member.*;
import cosmetics.BOGOShop.service.MemberService;
import cosmetics.BOGOShop.utils.SecurityUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController//(Controller + ResponseBody : 데이터 자체를 Json으로 바로 보냄)
@RequiredArgsConstructor
public class MemberApiController implements MemberControllerDocs {

    private final MemberService memberService;


    @Operation(summary = "전체 회원 조회",description = "전체 회원 목록을 조회 합니다.")
    @GetMapping("/api/members")
    public Result  members(){
        List<Member> findMembers = memberService.findMembers();
        //엔티티 > DTO 반환
        List<MemberDto> collect = findMembers.stream()
                .map(m-> new MemberDto(m)) //각 요소를 변환 (Member 엔티티 -> Member DTO)
                .collect(Collectors.toList()); //스트림을 컬렉션(+리스트)으로 변환

        return new Result(collect.size(),collect);
    }
//    @Operation(summary = "회원 등록",description = "회원을 등록 합니다.")
//    @PostMapping("/api/members")
//    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request)
//    {
//        Member member = new Member();
//        member.setName(request.getName());
//        member.setAge(request.getAge());
//        member.setPhone(request.getPhone());
//        member.setAddress(request.getAddress());
//
//        Long id = memberService.join(member);
//        return new CreateMemberResponse(id);
//    }

    @Operation(summary = "회원 정보 수정",description = "파라미터로 받은 회원ID로 회원 정보를 수정하고 수정된 정보를 확인 합니다.")
    @PutMapping("/api/members/{id}") //멱등성
    public UpdateMemberResponse updateMember(@PathVariable("id") long id, @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id,request);
        UpdateMemberResponse findMember = memberService.findOne(id);
        return findMember;
    }

    //MemberControllerDocs 구현
    @PostMapping("/members/sign-up")
    public ResponseEntity<MemberDto> signUp(@RequestBody @Valid SignUpDto signUpDto){
        MemberDto savedMemberDto = memberService.signUp(signUpDto);
        return ResponseEntity.ok(savedMemberDto);
    }

    @Operation(summary = "로그인",description = "JWT 토큰을 이용해서 로그인 합니다.")
    @PostMapping("/members/sign-in")
    public JwtToken signIn(@RequestBody @Valid SignInDto signInDto) {
        String userId = signInDto.getUserId();
        String password = signInDto.getPassword();
        JwtToken jwtToken = memberService.signIn(userId, password);
        return jwtToken;
    }

    @Operation(summary = "로그인 여부 확인",description = "JWT 토큰을 이용해서 로그인 여부를 확인 합니다.")
    @PostMapping("/members/login")
    public String loginUser() {
        return SecurityUtil.getCurrentLoginUserId();
    }

    @Operation(summary = "로그인 아웃",description = "로그아웃과 함께 JWT 토큰도 삭제합니다.")
    @PostMapping("/members/logout")
    public String logout(HttpServletRequest request, Principal principal){
        memberService.logout(request,principal.getName());
        return "success";
    }

}
