package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;


import cosmetics.BOGOShop.dto.Result;

import cosmetics.BOGOShop.dto.category.CategoryDto;
import cosmetics.BOGOShop.dto.category.CategoryResponse;
import cosmetics.BOGOShop.dto.category.SubCategoryDto;
import cosmetics.BOGOShop.dto.item.*;

import cosmetics.BOGOShop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "카테고리 API", description = "카테고리에 대한 설명입니다.")
public class CategoryApiController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 조회", description = "카테고리와 서브카테고리를 조회합니다.")
    @GetMapping("/api/category")
    public Result getCategory(){
        List<Category> findCategory = categoryService.findCategorys();
        List<CategoryDto> collect = findCategory.stream()
            .map(this::mapToCategoryDto)
            .collect(Collectors.toList());
        return new Result(collect.size(),collect);
    }

    private CategoryDto mapToCategoryDto(Category category) {
        List<SubCategoryDto> subCategoryDtos = category.getSubCategories().stream()
                .map(subCategory -> new SubCategoryDto(subCategory.getId(),subCategory.getName(),category.getId()))
                .collect(Collectors.toList());


        return new CategoryDto(category.getId(),category.getName(),getNullableList(subCategoryDtos));
    }
    private <T> List<T> getNullableList(List<T> list) {
        return list.isEmpty() ? null : list;
    }

    @Operation(summary = "카테고리 등록", description = "카테고리를 등록합니다.")
    @PostMapping("/api/category")
    public CategoryResponse saveCategory(@RequestBody @Valid CategoryDto categoryDto){
        Category category = new Category();
        category.setName(categoryDto.getCategoryName());
        Long id = categoryService.join(category);
        return new CategoryResponse(id);
    }
    @Operation(summary = "서브카테고리 등록", description = "서브카테고리만 등록합니다.")
    @PostMapping("/api/category/subcategory")
    public CategoryResponse saveSubCategory(@RequestBody @Valid SubCategoryDto subCategoryDto){
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryDto.getSubCategoryName());
        subCategory.setCategoryID(subCategoryDto.getCategoryId()); //카테고리 등록
        Long subCategoryId = categoryService.addSubCategory(subCategory);
        return new CategoryResponse(subCategoryId);
    }



}
