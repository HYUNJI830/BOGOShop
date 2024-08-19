package cosmetics.BOGOShop.repository.category;

import cosmetics.BOGOShop.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, String> {
    SubCategory findById(Long id);
    SubCategory findByName(String name);
}
