package cosmetics.BOGOShop.repository.category;

import cosmetics.BOGOShop.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, String> {

    SubCategory findByName(String name);
}
