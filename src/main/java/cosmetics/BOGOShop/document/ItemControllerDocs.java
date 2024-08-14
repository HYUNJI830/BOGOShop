package cosmetics.BOGOShop.document;

import cosmetics.BOGOShop.dto.item.ItemDto;
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
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name="아이템 API",description = "아이템 관련 컨트롤러에 대한 설명입니다.")
public interface ItemControllerDocs {

    @Parameters(value = {
            @Parameter(name = "itemId", description = "아이템 아이디"),
            @Parameter(name = "itemName", description = "아이템 이름"),
            @Parameter(name = "brandName", description = "브랜드 이름"),
            @Parameter(name = "price", description = "아이템 가격"),
            @Parameter(name = "stockQuantity", description = "아이템 수량"),
            @Parameter(name = "categoryId", description = "카데고리 아이디"),
            @Parameter(name = "categoryName", description = "카테고리 이름"),
            @Parameter(name = "subCategoryId", description = "서브카데고리 아이디"),
            @Parameter(name = "subCategoryName", description = "서브카데고리 이름")
    })
    @Operation(summary = "상품 등록",description = "상품을 등록 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 등록 완료", content = @Content(schema = @Schema(implementation = ItemDto.class)))
    })
    ResponseEntity<String> saveItem(ItemDto itemDto);

    @Parameters(value = {
            @Parameter(name = "itemId", description = "아이템 아이디"),
            @Parameter(name = "itemName", description = "아이템 이름"),
            @Parameter(name = "brandName", description = "브랜드 이름"),
            @Parameter(name = "price", description = "아이템 가격"),
            @Parameter(name = "stockQuantity", description = "아이템 수량"),
            @Parameter(name = "categoryId", description = "카데고리 아이디"),
            @Parameter(name = "categoryName", description = "카테고리 이름"),
            @Parameter(name = "subCategoryId", description = "서브카데고리 아이디"),
            @Parameter(name = "subCategoryName", description = "서브카데고리 이름")
    })
    @Operation(summary = "상품 수정",description = "상품을 수정 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "상품 수정 완료", content = @Content(schema = @Schema(implementation = ItemDto.class)))
    })
    ResponseEntity<String> updateItem(Long itemId,ItemDto itemDto);
}
