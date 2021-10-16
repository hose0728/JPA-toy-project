package jpabook.jpashop;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

  private final InitService initService;

  @PostConstruct
  public void init(){
    initService.dbInit1();
    initService.dbInit2();
  }

  @Component
  @RequiredArgsConstructor
  @Transactional
  static class InitService{

    private final EntityManager em;
    public void dbInit1(){
      Member member = new Member();
      member.setName("userA");
      member.setAddress(new Address("한양","1","111"));
      em.persist(member);

      Book book1 = createBook("JPA1 BOOk", 10000);
      em.persist(book1);

      Book book2 = createBook("JPA2 BOOk", 20000);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());
      Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
      em.persist(order);

    }


    public void dbInit2(){
      Member member = new Member();
      member.setName("userB");
      member.setAddress(new Address("평양","2","222"));
      em.persist(member);

      Book book1 = createBook("SPRING BOOk1", 10000);
      em.persist(book1);

      Book book2 = createBook("SPRING BOOk2", 20000);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());
      Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
      em.persist(order);

    }


  }

  private static Book createBook(String bookName, int bookPrice) {
    Book book1 = new Book();
    book1.setName(bookName);
    book1.setPrice(bookPrice);
    book1.setStockQuantity(100);
    return book1;
  }
}
