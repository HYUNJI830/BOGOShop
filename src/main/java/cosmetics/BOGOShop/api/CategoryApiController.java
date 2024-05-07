package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.dto.Result;
import cosmetics.BOGOShop.dto.category.CategoryChildDto;
import cosmetics.BOGOShop.dto.category.CategoryDto;
import cosmetics.BOGOShop.dto.category.CreateCategoryResponse;
import cosmetics.BOGOShop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    //대분류 카테고리 생성 API
    @PostMapping("/api/parent")
    public CreateCategoryResponse createParentCategory(@RequestBody @Valid CategoryDto categoryDto){
        Category parentCategory = new Category();
        parentCategory.setName(categoryDto.getName());

        Long parentId = categoryService.join(parentCategory);
        return new CreateCategoryResponse(parentId);
    }

    //자식 카테고리 생성 API
    @PostMapping("/api/{parentId}/child")
    public CreateCategoryResponse createChildCategory(@PathVariable Long parentId, @RequestBody @Valid CategoryChildDto categoryChildDto){
        Category parentCategory = categoryService.findByID(parentId);

        //부모 카테고리를 가지고 자식 카테고리 생성
        Category childCategory = new Category();
        childCategory.setName(categoryChildDto.getName());

        //부모카테고리에 자식 카테고리 추가
        //parentCategory.addChildCategory(childCategory);
        categoryService.addChildCategory(childCategory,parentCategory);

        Long childId = categoryService.join(childCategory);
        return new CreateCategoryResponse(childId);
    }


    @GetMapping("/api/category")
    public Result category(){
        List<Category> findCategory = categoryService.findCategorys();

        List<CategoryDto> collect = findCategory.stream()
                .map(this::mapToCategoryDto)
                .collect(Collectors.toList());

        return new Result(collect.size(),collect);
    }

    private CategoryDto mapToCategoryDto(Category category) {
        List<CategoryChildDto> childDtos = category.getChild().stream()
                .map(child -> new CategoryChildDto(child.getId(),child.getName()))
                .collect(Collectors.toList());

        return new CategoryDto(category.getId(),category.getName(), childDtos);
    }


    @PostMapping("/api/category")
    public CreateCategoryResponse saveCategory(@RequestBody @Valid CategoryDto categoryDto){

        Category category = new Category();
        category.setName(categoryDto.getName());

        Long id = categoryService.join(category);
        return new CreateCategoryResponse(id);
    }

}
