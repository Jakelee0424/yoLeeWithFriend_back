package klj.project.domain.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileGroup is a Querydsl query type for FileGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileGroup extends EntityPathBase<FileGroup> {

    private static final long serialVersionUID = -1796255327L;

    public static final QFileGroup fileGroup = new QFileGroup("fileGroup");

    public final DateTimePath<java.time.LocalDateTime> createDate = createDateTime("createDate", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final EnumPath<FileCategory> groupCategory = createEnum("groupCategory", FileCategory.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QFileGroup(String variable) {
        super(FileGroup.class, forVariable(variable));
    }

    public QFileGroup(Path<? extends FileGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileGroup(PathMetadata metadata) {
        super(FileGroup.class, metadata);
    }

}

