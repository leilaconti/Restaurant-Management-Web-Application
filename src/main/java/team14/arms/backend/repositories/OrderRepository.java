package team14.arms.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import team14.arms.backend.data.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
/*
  Page<Order> findBy(Pageable page);

  static Page<Order> findByOrderLikeIgnoreCase(String string, Pageable pageable) {
    return null;
  }

  long countByNameOrderIgnoreCase(String repositoryFilter);*/
}
