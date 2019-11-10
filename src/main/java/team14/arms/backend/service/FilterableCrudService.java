package team14.arms.backend.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import team14.arms.backend.data.entity.AbstractEntity;

public interface FilterableCrudService<T extends AbstractEntity> extends CrudService<T> {

    Page<T> findAnyMatching(Optional<?> filter, Pageable pageable);

    long countAnyMatching(Optional<?> filter);
}
