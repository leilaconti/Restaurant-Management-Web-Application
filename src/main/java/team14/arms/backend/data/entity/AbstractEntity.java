package team14.arms.backend.data.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Comparable<AbstractEntity> {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return version == that.version && Objects.equals(id, that.id);
    }
    
    @Override
    public int compareTo(AbstractEntity other) {
        return Long.compare(this.id, other.id);
    }
}
