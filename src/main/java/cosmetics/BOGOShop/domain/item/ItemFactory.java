package cosmetics.BOGOShop.domain.item;

import cosmetics.BOGOShop.domain.Category;
import cosmetics.BOGOShop.domain.SubCategory;
import cosmetics.BOGOShop.dto.item.ItemDto;
import cosmetics.BOGOShop.repository.category.CategoryRepository;
import cosmetics.BOGOShop.repository.category.SubCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class ItemFactory {
    private final  CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    private final Map<String, Function<ItemDto, Item>> itemMap = new HashMap<>();

    public ItemFactory(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        // 여기에 새로운 Item 타입을 추가합니다.
        itemMap.put("헤어케어", this::createHairItem);
        itemMap.put("메이크업", this::createMakeupItem);
        itemMap.put("스킨케어",this::createSkinCareItem);
        // 추가적인 타입을 여기에 추가할 수 있습니다.
    }



    //카테고리별 상품 등록
    public Item createItem(ItemDto itemDto) {
        Function<ItemDto, Item> itemFunction = itemMap.get(itemDto.getCategoryName());
        if (itemFunction == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리 입니다.: " + itemDto.getCategoryName());
        }
        return itemFunction.apply(itemDto);
    }

    private Item createHairItem(ItemDto itemDto) {
        HairItem hairItem = new HairItem();

        Category category = (Category) categoryRepository.findByName(itemDto.getCategoryName());
        SubCategory subCategory = subCategoryRepository.findByName(itemDto.getSubCategoryName());
        hairItem.setCategory(category);
        hairItem.setSubCategory(subCategory);

        hairItem.setName(itemDto.getItemName());
        hairItem.setPrice(itemDto.getPrice());
        hairItem.setStockQuantity(itemDto.getStockQuantity());

        return hairItem;
    }


    private Item createMakeupItem(ItemDto itemDto) {
        Makeup makeup = new Makeup();
        Category category = categoryRepository.findByID(itemDto.getCategoryId());
        SubCategory subCategory = subCategoryRepository.findByName(itemDto.getSubCategoryName());
        makeup.setCategory(category);
        makeup.setSubCategory(subCategory);

        makeup.setName(itemDto.getItemName());
        makeup.setPrice(itemDto.getPrice());
        makeup.setStockQuantity(itemDto.getStockQuantity());

        return makeup;
    }

    private Item createSkinCareItem(ItemDto itemDto) {
        SkinCare skinCare = new SkinCare();
        Category category = categoryRepository.findByID(itemDto.getCategoryId());
        SubCategory subCategory = subCategoryRepository.findByName(itemDto.getSubCategoryName());
        skinCare.setCategory(category);
        skinCare.setSubCategory(subCategory);

        skinCare.setName(itemDto.getItemName());
        skinCare.setPrice(itemDto.getPrice());
        skinCare.setStockQuantity(itemDto.getStockQuantity());

        return skinCare;
    }

}
