package cosmetics.BOGOShop.domain.item.pattern;

import cosmetics.BOGOShop.domain.item.HairItem;
import cosmetics.BOGOShop.domain.item.Item;
import cosmetics.BOGOShop.domain.item.Makeup;
import cosmetics.BOGOShop.domain.item.SkinCare;
import org.springframework.stereotype.Component;

@Component
public class itemsCreation {

    @Component
    public class MakeupItemCreationStrategy extends AbstractItemCreation {
        @Override
        public Long getCategoryId() {
            return 1L;
        }
        @Override
        protected Item createSpecificItem() {
            return new Makeup();
        }
    }

    @Component
    public class SkinCareItemCreationStrategy extends AbstractItemCreation{
        @Override
        public Long getCategoryId() {
            return 2L;
        }
        @Override
        protected Item createSpecificItem() {
            return new SkinCare();
        }
    }

    @Component
    public class HairItemCreationStrategy extends AbstractItemCreation {
        @Override
        public Long getCategoryId() {
            return 3L;
        }
        @Override
        protected Item createSpecificItem() {
            return new HairItem();
        }


    }

}
