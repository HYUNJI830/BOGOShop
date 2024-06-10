package cosmetics.BOGOShop.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBodyCare is a Querydsl query type for BodyCare
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBodyCare extends EntityPathBase<BodyCare> {

    private static final long serialVersionUID = 142012253L;

    public static final QBodyCare bodyCare = new QBodyCare("bodyCare");

    public final ListPath<BodyItem, QBodyItem> bodyItems = this.<BodyItem, QBodyItem>createList("bodyItems", BodyItem.class, QBodyItem.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QBodyCare(String variable) {
        super(BodyCare.class, forVariable(variable));
    }

    public QBodyCare(Path<? extends BodyCare> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBodyCare(PathMetadata metadata) {
        super(BodyCare.class, metadata);
    }

}

