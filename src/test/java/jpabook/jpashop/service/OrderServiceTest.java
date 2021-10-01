package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired
  EntityManager em;
  @Autowired
  OrderService orderService;
  @Autowired
  OrderRepository orderRepository;

  @Test
  @DisplayName("주문이 정상적으로 된다.")
  public void 상품주문() throws Exception{

    Member member = createMember();
    Book book = createBook("증폭의 고서", 1000, 10);
    //when
    int ordercount = 2;
    Long orderId = orderService.order(member.getId(), book.getId(), ordercount);
    //then
    Order getOrder = orderRepository.findOne(orderId);

    assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
    assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
    assertThat(getOrder.getTotalPrice()).isEqualTo(book.getPrice()*ordercount);
    assertThat(book.getStockQuantity()).isEqualTo(10-ordercount);


  }

  private Book createBook(String name, int price, int stockQuantity) {
    Book book = new Book();
    book.setName(name);
    book.setPrice(price);
    book.setStockQuantity(stockQuantity);
    em.persist(book);
    return book;
  }

  private Member createMember() {
    //given
    Member member = new Member();
    member.setName("회원1");
    member.setAddress(new Address("소환사의 협곡","탑","1111" ));
    em.persist(member);
    return member;
  }

  @Test
  public void 상품주문_재고수량초과() throws Exception{
    //given
    Member member = createMember();
    Book book = createBook("수학의 정석", 1000, 10);

    int orderCount = 12;


    //when
    Assertions.assertThrows(NotEnoughStockException.class,()-> orderService.order(member.getId(), book.getId(), orderCount),"재고 수량 부족 예외가 발생");

    //then

  }


  @Test
  public void 주문취소() throws Exception{

    //given
    Member member = createMember();
    Book book = createBook("토익", 1000, 100);

    int orderCount = 3;
    Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

    //when
    orderService.cancelOrder(orderId);

    //then

    Order getOrder = orderRepository.findOne(orderId);
    assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
    assertThat(book.getStockQuantity()).isEqualTo(100);
  }




}