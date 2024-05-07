package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Member;
import cosmetics.BOGOShop.dto.*;
import cosmetics.BOGOShop.dto.member.*;
import cosmetics.BOGOShop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController//(Controller + ResponseBody : 데이터 자체를 Json으로 바로 보냄)
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/members")
    public Result  members(){
        List<Member> findMembers = memberService.findMembers();
        //엔티티 > DTO 반환
        List<MemberDto> collect = findMembers.stream()
                .map(m-> new MemberDto(m))
                .collect(Collectors.toList());

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
}
