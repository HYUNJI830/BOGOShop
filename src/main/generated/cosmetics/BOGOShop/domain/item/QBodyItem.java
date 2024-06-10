package cosmetics.BOGOShop.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBodyItem is a Querydsl query type for BodyItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBodyItem extends EntityPathBase<BodyItem> {

    private static final long serialVersionUID = 142208863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBodyItem bodyItem = new QBodyItem("bodyItem");

    public final QBodyCare bodyCare;

    public final StringPath brandName = createString("brandName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QBodyItem(String variable) {
        this(BodyItem.class, forVariable(variable), INITS);
    }

    public QBodyItem(Path<? extends BodyItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBodyItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBodyItem(PathMetadata metadata, PathInits inits) {
        this(BodyItem.class, metadata, inits);
    }

    public QBodyItem(Class<? extends BodyItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bodyCare = inits.isInitialized("bodyCare") ? new QBodyCare(forProperty("bodyCare")) : null;
    }

}

