package cosmetics.BOGOShop.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHairCare is a Querydsl query type for HairCare
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHairCare extends EntityPathBase<HairCare> {

    private static final long serialVersionUID = -1689496163L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHairCare hairCare = new QHairCare("hairCare");

    public final QItem _super;

    public final StringPath brandName = createString("brandName");

    public final cosmetics.BOGOShop.domain.QCategory category;

    public final StringPath hairCategory = createString("hairCategory");

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final StringPath name;

    //inherited
    public final NumberPath<Integer> price;

    //inherited
    public final NumberPath<Integer> stockQuantity;

    public QHairCare(String variable) {
        this(HairCare.class, forVariable(variable), INITS);
    }

    public QHairCare(Path<? extends HairCare> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHairCare(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHairCare(PathMetadata metadata, PathInits inits) {
        this(HairCare.class, metadata, inits);
    }

    public QHairCare(Class<? extends HairCare> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QItem(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new cosmetics.BOGOShop.domain.QCategory(forProperty("category"), inits.get("category")) : null;
        this.id = _super.id;
        this.name = _super.name;
        this.price = _super.price;
        this.stockQuantity = _super.stockQuantity;
    }

}

