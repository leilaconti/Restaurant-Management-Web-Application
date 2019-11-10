package team14.arms.backend.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import team14.arms.backend.data.entity.MenuItem;
import team14.arms.backend.data.entity.User;
import team14.arms.backend.repositories.MenuItemRepository;

@Service
public class MenuItemService implements FilterableCrudService<MenuItem> {

    private static final String MENU_ITEM_NAME_ALREADY_TAKEN = "There is already a menu item with that name. Please select a unique name for the item.";

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public Page<MenuItem> findAnyMatching(Optional<?> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return menuItemRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<?> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return menuItemRepository.countByNameLikeIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    public Page<MenuItem> find(Pageable pageable) {
        return menuItemRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<MenuItem, Long> getRepository() {
        return menuItemRepository;
    }

    @Override
    public MenuItem createNew(User currentUser) {
        return new MenuItem();
    }

    @Override
    public MenuItem save(User currentUser, MenuItem entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(MENU_ITEM_NAME_ALREADY_TAKEN);
        }
    }

}
