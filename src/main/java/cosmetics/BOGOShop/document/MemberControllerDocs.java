package cosmetics.BOGOShop.document;

import cosmetics.BOGOShop.dto.member.MemberDto;
import cosmetics.BOGOShop.login.dto.Login.SignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name="회원 API",description = "회원 관련 컨트롤러에 대한 설명입니다.")
public interface MemberControllerDocs {

    @Parameters(value = {
            @Parameter(name = "userId", description = "회원 아이디"),
            @Parameter(name = "password", description = "회원 비밀번호"),
            @Parameter(name = "name", description = "회원 이름"),
            @Parameter(name = "age", description = "회원 나이"),
            @Parameter(name = "phone", description = "회원 연락망"),
            @Parameter(name = "address", description = "회원 주소")
    })
    @Operation(summary = "회원 가입",description = "JWT 토큰을 이용해서 회원 가입 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입완료", content = @Content(schema = @Schema(implementation = SignUpDto.class)))
    })
    public ResponseEntity<MemberDto> signUp(SignUpDto signUpDto);
}
