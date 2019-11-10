package team14.arms.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import team14.arms.backend.data.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Page<MenuItem> findBy(Pageable page);

    Page<MenuItem> findByNameLikeIgnoreCase(String name, Pageable page);

    int countByNameLikeIgnoreCase(String name);
}
