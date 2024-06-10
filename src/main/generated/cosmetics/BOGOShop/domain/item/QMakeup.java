package cosmetics.BOGOShop.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMakeup is a Querydsl query type for Makeup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMakeup extends EntityPathBase<Makeup> {

    private static final long serialVersionUID = 1647579923L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMakeup makeup = new QMakeup("makeup");

    public final QItem _super;

    public final StringPath brandName = createString("brandName");

    public final cosmetics.BOGOShop.domain.QCategory category;

    //inherited
    public final NumberPath<Long> id;

    public final StringPath makeupCategory = createString("makeupCategory");

    //inherited
    public final StringPath name;

    //inherited
    public final NumberPath<Integer> price;

    //inherited
    public final NumberPath<Integer> stockQuantity;

    public QMakeup(String variable) {
        this(Makeup.class, forVariable(variable), INITS);
    }

    public QMakeup(Path<? extends Makeup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMakeup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMakeup(PathMetadata metadata, PathInits inits) {
        this(Makeup.class, metadata, inits);
    }

    public QMakeup(Class<? extends Makeup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QItem(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new cosmetics.BOGOShop.domain.QCategory(forProperty("category"), inits.get("category")) : null;
        this.id = _super.id;
        this.name = _super.name;
        this.price = _super.price;
        this.stockQuantity = _super.stockQuantity;
    }

}

