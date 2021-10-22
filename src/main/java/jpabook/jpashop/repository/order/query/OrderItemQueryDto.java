package jpabook.jpashop.repository.order.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemQueryDto {

  private Long OrderId;
  private String itemName;
  private int orderPrice;
  private int count;

}
