/*
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import team14.arms.backend.data.entity.Order;
import team14.arms.backend.data.entity.User;
import team14.arms.backend.repositories.OrderRepository;

@Service
public class OrderService implements FilterableCrudService<Order> {

    private static final String ORDER_ITEM_NAME_ALREADY_TAKEN = "There is already an order item with that name. Please select a unique name for the item.";

    private final OrderRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository OrderItemRepository) {
        this.orderItemRepository = OrderItemRepository;
    }

    @Override
    public Page<Order> findAnyMatching(Optional<?> filter, Pageable pageable) {
        if (filter.isPresent()) {
            return OrderRepository.findByOrderLikeIgnoreCase("%" + filter.get().toString() + "%", pageable);
        } else {
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional<?> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return orderItemRepository.countByNameOrderIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    public Page<Order> find(Pageable pageable) {
        return orderItemRepository.findBy(pageable);
    }

    @Override
    public JpaRepository<Order, Long> getRepository() {
        return orderItemRepository;
    }

    @Override
    public Order createNew(User currentUser) {
        return new Order();
    }

    @Override
    public Order save(User currentUser, Order entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(ORDER_ITEM_NAME_ALREADY_TAKEN);
        }
    }

}*/

