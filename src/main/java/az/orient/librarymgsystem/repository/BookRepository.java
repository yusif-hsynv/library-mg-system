package az.orient.librarymgsystem.repository;

import az.orient.librarymgsystem.entity.Author;
import az.orient.librarymgsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByActive(Integer active);
    //List<Book> findAllBookByAuthorAndActive(Author author, Integer active);
    Book findBookByIdAndActive(Long id, Integer active);
}
