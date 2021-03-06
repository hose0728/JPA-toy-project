package jpabook.jpashop.repository.order.query;

import java.time.LocalDateTime;
import java.util.List;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderQueryDto {

  private Long OrderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;
  private List<OrderItemQueryDto> orderItems;

  public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate,
      OrderStatus orderStatus, Address address
  ) {
    OrderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
  }
}
