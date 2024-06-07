package cosmetics.BOGOShop.service;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import cosmetics.BOGOShop.domain.item.BodyCare;

import cosmetics.BOGOShop.repository.category.CategoryRepository;
import cosmetics.BOGOShop.repository.order.query.OrderQuerydslRepository;
import cosmetics.BOGOShop.repository.querydsl.BodyItemJPARepository;
import cosmetics.BOGOShop.repository.querydsl.BodyItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 해당 트랜잭션 내에서 조회하는 Entity는 조회용 인식 > 변경감지 스냅샷을 따로 보관하지 않게하여 메모리 절약
@RequiredArgsConstructor //생성자 주입 방법 (final 이나 @NotNull 붙은 필드 생성자를 자동 생성)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BodyItemRepository bodyRepository;
    private final OrderQuerydslRepository orderQuerydslRepository;

    /**
     * 카테고리 등록
     */
    @Transactional
    public Long join(Category category){
        validateDuplicateCategory(category); //중복 카테고리 검증
        categoryRepository.save(category);
        return category.getId();
    }

    private void validateDuplicateCategory(Category category) {
        List<Category> findCategorys = categoryRepository.findByName(category.getName());
        if(!findCategorys.isEmpty()){
            throw new IllegalStateException("이미 존재하는 카테고리 입니다.");
        }
    }

    //서브 카테고리 등록
    @Transactional
    public Long addSubCategory(SubCategory subCategory){
        categoryRepository.saveSub(subCategory);
        return subCategory.getId();
    }

    //바디케어카테고리 등록
    @Transactional
    public Long addBodyCareCategory(BodyCare bodyCare){
        categoryRepository.bodyCareSave(bodyCare);
       return bodyCare.getId();
    }


//    public void addChildCategory(Category child, Category parentCategory) {
//        //parentCategory.addChildCategory(child); // 부모 카테고리에 자식 카테고리 추가
//        parentCategory.setChild(child.getChild());
//        child.setParent(parentCategory); // 부모 카테고리 설정
//    }


    /**
     * 카테고리 전체 조회
     */
    public List<Category> findCategorys(){
        return categoryRepository.findAll();
    }

    //ID로 카테고리 조회
    public Category findByID(Long categoryId){
        return categoryRepository.findOne(categoryId);
    }

    /**
     * 카테고리 수정
     */

    @Transactional
    public void update(Long id,String name){
        Category category = categoryRepository.findOne(id);
        category.setName(name);
    }

}
