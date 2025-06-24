package klj.project.domain.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNuinfo is a Querydsl query type for Nuinfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNuinfo extends EntityPathBase<Nuinfo> {

    private static final long serialVersionUID = 1963979885L;

    public static final QNuinfo nuinfo = new QNuinfo("nuinfo");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final StringPath codeId = createString("codeId");

    public final NumberPath<Long> sn = createNumber("sn", Long.class);

    public final StringPath value = createString("value");

    public QNuinfo(String variable) {
        super(Nuinfo.class, forVariable(variable));
    }

    public QNuinfo(Path<? extends Nuinfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNuinfo(PathMetadata metadata) {
        super(Nuinfo.class, metadata);
    }

}

