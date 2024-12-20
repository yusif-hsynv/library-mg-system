package az.orient.librarymgsystem.service;

import az.orient.librarymgsystem.dto.request.ReqAuthor;
import az.orient.librarymgsystem.dto.response.RespAuthor;
import az.orient.librarymgsystem.dto.response.Response;

import java.util.List;

public interface AuthorService {
    Response<List<RespAuthor>> authorList();

    Response<RespAuthor> authorById(Long id);

    Response createAuthor(ReqAuthor reqAuthor);

    Response updateAuthor(ReqAuthor reqAuthor);

    Response deleteAuthor(Long id);
}
