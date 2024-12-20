package az.orient.librarymgsystem.controller;

import az.orient.librarymgsystem.dto.request.ReqBook;
import az.orient.librarymgsystem.dto.response.RespBook;
import az.orient.librarymgsystem.dto.response.Response;
import az.orient.librarymgsystem.service.BookService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/list")
    public Response<List<RespBook>> getBookList(){
        return bookService.getBookList();
    }
    @GetMapping("byId/{id}")
    public Response<RespBook> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/create")
    public Response createBook(@RequestBody ReqBook reqBook) {
        return bookService.createBook(reqBook);
    }

    @PutMapping("/update")
    public Response updateBook(@RequestBody ReqBook reqBook) {
        return bookService.updateBook(reqBook);
    }
    @DeleteMapping("/delete")
    public Response deleteBook(@RequestParam Long id) {
        return bookService.deleteBook(id);
    }
}
