package az.orient.librarymgsystem.repository;

import az.orient.librarymgsystem.entity.Author;
import az.orient.librarymgsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAllByActive(Integer active);

    Author findAuthorByIdAndActive(Long id, Integer active);
}
