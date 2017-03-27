package com.wumugulu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wumugulu.entity.Book;

@Controller
// url接口地址的前缀
@RequestMapping("/books")
public class BookController {

	// 我们用这个来保存Book的信息，并且利用id值就可以找到对应的Book，相当于模拟了一个数据库;
	// 有兴趣的话，可以百度一下关于HashMap的内容，网上很多很多
	private Map<Integer, Book> mapDB = new HashMap<>();

	// get-查询全部Book
	// value表示被访问的url的路径（会跟class上面一行中定义的"/books"合起来,结果是"/books"[或者"/books/"也可以访问到]）
	// method表示访问这个url接口的时候method必须是GET
	@RequestMapping(value="", method=RequestMethod.GET)
	@ResponseBody
	public List<Book> getAll(){
		return new ArrayList<Book>(mapDB.values());
	}
	
	// get-查询单个Book
	// value表示被访问的url的路径（会跟class上面一行中定义的"/books"合起来就是"/books/{id}",其中{id}是一个Integer的整数数值）
	// method表示访问这个url接口的时候method必须是GET
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Book getOne(@PathVariable Integer id){
		System.out.println("got parameter bookId = " + id);
		return mapDB.get(id);
	}

	// post-新增Book
	// value表示被访问的url的路径（会跟class上面一行中定义的"/books"合起来,结果是"/books"[或者"/books/"也可以访问到]）
	// method表示访问这个url接口的时候method必须是POST（注意：这是和前面getAll方法的区别之处）
	@RequestMapping(value="", method=RequestMethod.POST)
	@ResponseBody
	public String create(Book book){
		System.out.println("got parameter Book = " + book.toString());
		if(book.getId()==null){
			return "id为空，不可添加";
		}
		if(mapDB.containsKey(book.getId())){
			return "id重复，添加失败";
		}
		mapDB.put(book.getId(), book);
		return "success add Book: " + book.getId();
	}

	// put-修改Book
	// value表示被访问的url的路径（会跟class上面一行中定义的"/books"合起来就是"/books/{bookId}",其中{bookId}是一个Integer的整数数值）
	// method表示访问这个url接口的时候method必须是PUT（这个有点儿特殊，要留意下）
	@RequestMapping(value={"/{bookId}"}, method=RequestMethod.PUT)
	@ResponseBody
	public String update(@PathVariable Integer bookId, Book book){
		System.out.println("got parameter bookId = " + bookId);
		System.out.println("got parameter Book = " + book.toString());
		if(bookId.intValue()!=book.getId().intValue()){
			return "请求参数的id值不匹配";
		}
		if(!mapDB.containsKey(bookId)){
			return "未找到数据项: " + bookId;
		}
		mapDB.put(bookId, book);
		return "success update Book: " + book.getId();
	}

	// delete-删除Book
	// value表示被访问的url的路径（会跟class上面一行中定义的"/books"合起来就是"/books/{id}",其中{id}是一个Integer的整数数值）
	// method表示访问这个url接口的时候method必须是DELETE（这个也比较特殊，同样要留意）
	@RequestMapping(value={"/{id}"}, method=RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable Integer id){
		System.out.println("got parameter id=" + id);
		if(!mapDB.containsKey(id)){
			return "未找到数据项: " + id;
		}
		mapDB.remove(id);
		return "success delete Book: " + id;
	}

}
