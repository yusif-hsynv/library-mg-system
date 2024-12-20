package az.orient.librarymgsystem.service;

import az.orient.librarymgsystem.dto.request.ReqBook;
import az.orient.librarymgsystem.dto.response.RespBook;
import az.orient.librarymgsystem.dto.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    Response<List<RespBook>> getBookList();

    Response<RespBook> getBookById(Long id);

    Response createBook(ReqBook reqBook);

    Response updateBook(ReqBook reqBook);

    Response deleteBook(Long id);
}
