package az.orient.librarymgsystem.service;

import az.orient.librarymgsystem.dto.request.ReqBook;
import az.orient.librarymgsystem.dto.response.*;
import az.orient.librarymgsystem.entity.Author;
import az.orient.librarymgsystem.entity.Book;
import az.orient.librarymgsystem.entity.User;
import az.orient.librarymgsystem.enums.EnumAvailableStatus;
import az.orient.librarymgsystem.exception.ExceptionConstants;
import az.orient.librarymgsystem.exception.LibraryException;
import az.orient.librarymgsystem.repository.AuthorRepository;
import az.orient.librarymgsystem.repository.BookRepository;
import az.orient.librarymgsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    @Override
    public Response<List<RespBook>> getBookList() {
        Response<List<RespBook>> response = new Response<>();
        try {
            List<Book> bookList = bookRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (bookList.isEmpty()) {
                throw new LibraryException(ExceptionConstants.BOOK_NOT_FOUND, "Book not found");
            }
            List<RespBook> respBookList = bookList.stream().map(this::convertToRespBook).toList();
            response.setT(respBookList);
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

    private RespBook convertToRespBook(Book book) {
        List<RespAuthor> respAuthors = book.getAuthors().stream().map(author -> RespAuthor.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build()).toList();
        RespUser respUser = book.getBorrowedBy() != null ? RespUser.builder()
                .id(book.getBorrowedBy().getId())
                .name(book.getBorrowedBy().getName())
                .surname(book.getBorrowedBy().getSurname())
                .contactPhone(book.getBorrowedBy().getContactPhone())
                .address(book.getBorrowedBy().getAddress())
                .build() : null;

        return RespBook.builder()
                .id(book.getId())
                .title(book.getTitle())
                .borrowed(book.isBorrowed())
                .borrowedBy(respUser)
                .respAuthors(respAuthors)
                .build();
    }

    @Override
    public Response<RespBook> getBookById(Long id) {
        Response<RespBook> response = new Response<>();
        try {
            if (id == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Book book = bookRepository.findBookByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (book == null) {
                throw new LibraryException(ExceptionConstants.BOOK_NOT_FOUND, "Book not found");
            }
            RespBook respBook = convertToRespBook(book);
            response.setT(respBook);
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
    public Response createBook(ReqBook reqBook) {
        Response response = new Response();
        try {
            String title = reqBook.getTitle();
            if (title == null || reqBook.getAuthorId().isEmpty()) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            List<Author> authors = reqBook.getAuthorId().stream()
                    .map(authorId -> {
                        Author author = authorRepository.findAuthorByIdAndActive(authorId, EnumAvailableStatus.ACTIVE.value);
                        if (author == null) {
                            throw new LibraryException(ExceptionConstants.AUTHOR_NOT_FOUND, "Author not found");
                        }
                        return author;
                    })
                    .collect(Collectors.toList());
            Book book = Book.builder()
                    .title(title)
                    .borrowed(false)
                    .authors(authors)
                    .borrowedBy(null)
                    .build();
            bookRepository.save(book);
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
    public Response updateBook(ReqBook reqBook) {
        Response response = new Response();
        try {
            Long bookId = reqBook.getId();
            String title = reqBook.getTitle();
            if (bookId == null || title == null || reqBook.getAuthorId().isEmpty()) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Book book = bookRepository.findBookByIdAndActive(bookId, EnumAvailableStatus.ACTIVE.value);
            if (book == null) {
                throw new LibraryException(ExceptionConstants.BOOK_NOT_FOUND, "Book not found");
            }
            List<Author> authors = reqBook.getAuthorId().stream()
                    .map(authorId -> {
                        Author author = authorRepository.findAuthorByIdAndActive(authorId, EnumAvailableStatus.ACTIVE.value);
                        if (author == null) {
                            throw new LibraryException(ExceptionConstants.AUTHOR_NOT_FOUND, "Author not found");
                        }
                        return author;
                    })
                    .collect(Collectors.toList());
            book.setTitle(title);
            book.setAuthors(authors);
            book.setBorrowed(reqBook.isBorrowed());
            User user = reqBook.getUserId() != null ? userRepository.findUserByIdAndActive(reqBook.getUserId(), EnumAvailableStatus.ACTIVE.value) : null;
            book.setBorrowedBy(user);
            bookRepository.save(book);
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
    public Response deleteBook(Long id) {
        Response response = new Response();
        try {
            if (id == null) {
                throw new LibraryException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Book book = bookRepository.findBookByIdAndActive(id, EnumAvailableStatus.ACTIVE.value);
            if (book == null) {
                throw new LibraryException(ExceptionConstants.BOOK_NOT_FOUND, "Book not found");
            }
            book.setActive(EnumAvailableStatus.DEACTIVE.value);
            bookRepository.save(book);
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
