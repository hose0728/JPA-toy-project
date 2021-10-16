package jpabook.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.dto.OrderSimpleQueryDto;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * xToOne(ManyToOne,OneToOne) Order -> Member Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

  private final OrderRepository orderRepository;

  @GetMapping("/api/v1/simple-orders")
  public List<Order> ordersV1() {
    List<Order> result = orderRepository.findAllByString(new OrderSearch());
    return result;
  }

  @GetMapping("/api/v2/simple-orders")
  public List<SimpleOrderDto> ordersV2() {
    List<Order> result = orderRepository.findAllByString(new OrderSearch());
    return result.stream()
        .map(SimpleOrderDto::new)
        .collect(Collectors.toList());
  }

  @GetMapping("/api/v3/simple-orders")
  public List<SimpleOrderDto> ordersV3() {
    List<Order> result = orderRepository.findAllWithMemberDelivery();
    return result.stream()
        .map(SimpleOrderDto::new)
        .collect(Collectors.toList());
  }

  @GetMapping("/api/v4/simple-orders")
  public List<OrderSimpleQueryDto> ordersV4() {
    return orderRepository.findOrderDtos();
  }

  @Data
  static class SimpleOrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDto(Order order) {
      this.orderId = order.getId();
      this.name = order.getMember().getName();
      this.orderDate = order.getOrderDate();
      this.orderStatus = order.getStatus();
      this.address = order.getDelivery().getAddress();

    }
  }

}
