package jpabook.jpashop.controller;

import java.util.List;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.form.BookForm;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/new")
  public String create(BookForm form) {

    Book book = new Book();
    book.setPrice(form.getPrice());
    book.setStockQuantity(form.getStockQuantity());
    book.setName(form.getName());
    book.setAuthor(form.getAuthor());
    book.setIsbn(form.getIsbn());

    itemService.saveItem(book);
    return "redirect:/items";

  }

  @GetMapping
  public String list(Model model) {
    List<Item> items = itemService.finditems();
    model.addAttribute("items", items);
    return "items/itemList";
  }

  @GetMapping("/{itemId}/edit")
  public String updateItemForm(@PathVariable Long itemId, Model model) {
    Book item = (Book) itemService.findOne(itemId);
    BookForm form = new BookForm();
    form.setId(item.getId());
    form.setName(item.getName());
    form.setAuthor(item.getAuthor());
    form.setIsbn(item.getIsbn());
    form.setPrice(item.getPrice());
    form.setStockQuantity(item.getStockQuantity());
    model.addAttribute("form", form);
    return "items/updateItemForm";
  }

  @PostMapping("/{itemId}/edit")
  public String updateItem(BookForm form, @PathVariable Long itemId){

    Book book = new Book();
    book.setIsbn(form.getIsbn());
    book.setName(form.getName());
    book.setId(form.getId());
    book.setStockQuantity(form.getStockQuantity());
    book.setPrice(form.getPrice());
    book.setAuthor(form.getAuthor());

    itemService.saveItem(book);
    return "redirect:/items";
  }

}
