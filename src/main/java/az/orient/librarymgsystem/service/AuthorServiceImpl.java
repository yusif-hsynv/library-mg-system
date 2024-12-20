package az.orient.librarymgsystem.service;

import az.orient.librarymgsystem.dto.request.ReqAuthor;
import az.orient.librarymgsystem.dto.response.RespAuthor;
import az.orient.librarymgsystem.dto.response.RespStatus;
import az.orient.librarymgsystem.dto.response.Response;
import az.orient.librarymgsystem.entity.Author;
import az.orient.librarymgsystem.enums.EnumAvailableStatus;
import az.orient.librarymgsystem.exception.ExceptionConstants;
import az.orient.librarymgsystem.exception.LibraryException;
import az.orient.librarymgsystem.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Response<List<RespAuthor>> authorList() {
        Response<List<RespAuthor>> response = new Response<>();
        try {
            List<Author> authorList = authorRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (authorList.isEmpty()) {
                throw new LibraryException(ExceptionConstants.AUTHOR_NOT_FOUND, "Author not found");
            }
            List<RespAuthor> respAuthorList = authorList.stream().map(this::mapping).toList();
            response.setT(respAuthorList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    private RespAuthor mapping(Author author) {
        return RespAuthor.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }

    @Override
    public Response<RespAuthor> authorById(Long id) {
        Response<RespAuthor> response = new Response<>();
        try {
            if (id == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Author author = authorRepository.findAuthorByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (author == null) {
                throw new LibraryException(ExceptionConstants.AUTHOR_NOT_FOUND, "Author not found");
            }
            RespAuthor respAuthor = mapping(author);
            response.setT(respAuthor);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response createAuthor(ReqAuthor reqAuthor) {
        Response response = new Response();
        try {
            String name = reqAuthor.getName();
            String surname = reqAuthor.getSurname();
            if (name == null || surname == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Author author = Author.builder()
                    .name(name)
                    .surname(surname)
                    .build();
            authorRepository.save(author);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response updateAuthor(ReqAuthor reqAuthor) {
        Response response = new Response();
        try {
            Long authorId = reqAuthor.getId();
            String name = reqAuthor.getName();
            String surname = reqAuthor.getSurname();
            if (authorId == null || name == null || surname == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Author author = authorRepository.findAuthorByIdAndActive(authorId, EnumAvailableStatus.ACTIVE.value);
            if (author == null) {
                throw new LibraryException(ExceptionConstants.AUTHOR_NOT_FOUND, "Author not found");
            }
            author.setName(name);
            author.setSurname(surname);
            authorRepository.save(author);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response deleteAuthor(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Author author = authorRepository.findAuthorByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (author == null) {
                throw new LibraryException(ExceptionConstants.AUTHOR_NOT_FOUND, "Author not found");
            }
            author.setActive(EnumAvailableStatus.DEACTIVE.value);
            authorRepository.save(author);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (LibraryException ex) {
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        } catch (Exception ex) {
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception!"));
            ex.printStackTrace();
        }
        return response;
    }
}
