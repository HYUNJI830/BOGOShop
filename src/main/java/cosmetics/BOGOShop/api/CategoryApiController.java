package cosmetics.BOGOShop.api;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import cosmetics.BOGOShop.domain.item.BodyCare;
import cosmetics.BOGOShop.domain.item.BodyItem;

import cosmetics.BOGOShop.dto.Result;

import cosmetics.BOGOShop.dto.category.CategoryDto;
import cosmetics.BOGOShop.dto.category.CategoryResponse;
import cosmetics.BOGOShop.dto.category.SubCategoryDto;
import cosmetics.BOGOShop.dto.item.*;
import cosmetics.BOGOShop.repository.querydsl.BodyItemJPARepository;
import cosmetics.BOGOShop.repository.querydsl.BodyItemRepository;
import cosmetics.BOGOShop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;
    private final BodyItemRepository bodyItemRepository;
    private final BodyItemJPARepository bodyItemJPARepository;

    //카테고리 및 서브카테고리 조회
    @GetMapping("/api/category")
    public Result getCategory(){
        List<Category> findCategory = categoryService.findCategorys();
        List<CategoryDto> collect = findCategory.stream()
            .map(this::mapToCategoryDto)
            .collect(Collectors.toList());
        return new Result(collect.size(),collect);
    }

    private CategoryDto mapToCategoryDto(Category category) {
        List<BodyCareDto> bodyCareDtos = category.getBodyCares().stream()
                .map(bodyCare -> new BodyCareDto(bodyCare.getId(),bodyCare.getName(),category.getId()))
                .collect(Collectors.toList());
        List<SubCategoryDto> subCategoryDtos = category.getSubCategories().stream()
                .map(subCategory -> new SubCategoryDto(subCategory.getId(),subCategory.getName(),category.getId()))
                .collect(Collectors.toList());


        return new CategoryDto(category.getId(),category.getName(), getNullableList(bodyCareDtos),getNullableList(subCategoryDtos));
    }
    private <T> List<T> getNullableList(List<T> list) {
        return list.isEmpty() ? null : list;
    }

    @PostMapping("/api/category")
    public CategoryResponse saveCategory(@RequestBody @Valid CategoryDto categoryDto){

        Category category = new Category();
        category.setName(categoryDto.getCategoryName());

        Long id = categoryService.join(category);
        return new CategoryResponse(id);
    }
    //서브 카데고리 등록
    @PostMapping("/api/category/subcategory")
    public Long saveSubCategory(@RequestBody @Valid SubCategoryDto subCategoryDto){
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryDto.getSubCategoryName());
        subCategory.setCategoryID(subCategoryDto.getCategoryId());
        Long subCategoryId = categoryService.addSubCategory(subCategory);
        return subCategoryId;
    }
    //카테고리별 아이템 찾기
    @GetMapping("/api/items")
    public List<ItemDto> searchItems(@RequestParam(required = false) Long categoryId){
        return bodyItemRepository.searchItems(categoryId);
    }
    //http://localhost:8080/api/items?categoryId=2


    //bodyCare 등록
    @PostMapping("/api/category/bodyCare")
    public Long saveBodyCareCategory(@RequestBody @Valid BodyCareDto bodyCareDto){
        BodyCare bodyCare = new BodyCare();
        bodyCare.setName(bodyCareDto.getBodyCareName());
        bodyCare.setCategory(bodyCareDto.getCategoryId());
        Long bodyCareId = categoryService.addBodyCareCategory(bodyCare);
        return bodyCareId;
    }

    //조건에 만족한 바디아이템 찾기
    @GetMapping("/api/bodyItems")
    public List<BodyCareItemDto> searchBodyItems(BodyItemSearchCondition searchCondition){
        return bodyItemRepository.search(searchCondition);
    }

    //페이징
    @GetMapping("/api/page/bodyItems")
    public Page<BodyCareItemDto> pageBodyItems(BodyItemSearchCondition searchCondition, Pageable pageable){
        return  bodyItemJPARepository.searchPageSimple(searchCondition,pageable);
    }

    //    @GetMapping("/api/category")
//    public Result category(){
//        List<Category> findCategory = categoryService.findCategorys();
//
//        List<CategoryDto> collect = findCategory.stream()
//                .map(this::mapToCategoryDto)
//                .collect(Collectors.toList());
//
//        return new Result(collect.size(),collect);
//    }

    //대분류 카테고리 생성 API
//    @PostMapping("/api/parent")
//    public CreateCategoryResponse createParentCategory(@RequestBody @Valid CategoryDto categoryDto){
//        Category parentCategory = new Category();
//        parentCategory.setName(categoryDto.getName());
//
//        Long parentId = categoryService.join(parentCategory);
//        return new CreateCategoryResponse(parentId);
//    }

    //자식 카테고리 생성 API
//    @PostMapping("/api/{parentId}/child")
//    public CreateCategoryResponse createChildCategory(@PathVariable Long parentId, @RequestBody @Valid CategoryChildDto categoryChildDto){
//        Category parentCategory = categoryService.findByID(parentId);
//
//        //부모 카테고리를 가지고 자식 카테고리 생성
//        Category childCategory = new Category();
//        childCategory.setName(categoryChildDto.getName());
//
//        //부모카테고리에 자식 카테고리 추가
//        //parentCategory.addChildCategory(childCategory);
//        categoryService.addChildCategory(childCategory,parentCategory);
//
//        Long childId = categoryService.join(childCategory);
//        return new CreateCategoryResponse(childId);
//    }


//    @GetMapping("/api/category")
//    public Result category(){
//        List<Category> findCategory = categoryService.findCategorys();
//
//        List<CategoryDto> collect = findCategory.stream()
//                .map(this::mapToCategoryDto)
//                .collect(Collectors.toList());
//
//        return new Result(collect.size(),collect);
//    }

//    private CategoryDto mapToCategoryDto(Category category) {
//        List<CategoryChildDto> childDtos = category.getChild().stream()
//                .map(child -> new CategoryChildDto(child.getId(),child.getName()))
//                .collect(Collectors.toList());
//
//        return new CategoryDto(category.getId(),category.getName(), childDtos);
//    }




}
