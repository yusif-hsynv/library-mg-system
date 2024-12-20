package az.orient.librarymgsystem.controller;

import az.orient.librarymgsystem.dto.request.ReqAuthor;
import az.orient.librarymgsystem.dto.request.ReqUser;
import az.orient.librarymgsystem.dto.response.RespAuthor;
import az.orient.librarymgsystem.dto.response.RespUser;
import az.orient.librarymgsystem.dto.response.Response;
import az.orient.librarymgsystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    @GetMapping("/list")
    public Response<List<RespAuthor>> authorList() {
        return authorService.authorList();
    }

    @GetMapping("byId/{id}")
    public Response<RespAuthor> authorById(@PathVariable Long id) {
        return authorService.authorById(id);

    }

    @PostMapping("/create")
    public Response createAuthor(@RequestBody ReqAuthor reqAuthor) {
        return authorService.createAuthor(reqAuthor);
    }

    @PutMapping("/update")
    public Response updateAuthor(@RequestBody ReqAuthor reqAuthor) {
        return authorService.updateAuthor(reqAuthor);
    }

    @PutMapping("/delete")
    public Response deleteAuthor(@RequestParam Long id) {
        return authorService.deleteAuthor(id);
    }
}
