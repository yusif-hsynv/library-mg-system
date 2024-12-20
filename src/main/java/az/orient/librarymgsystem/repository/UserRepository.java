package az.orient.librarymgsystem.repository;

import az.orient.librarymgsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByActive(Integer active);

    User findUserByIdAndActive(Long id, Integer active);

}
