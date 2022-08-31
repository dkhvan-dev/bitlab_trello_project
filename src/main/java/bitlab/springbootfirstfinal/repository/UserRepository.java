package bitlab.springbootfirstfinal.repository;

import bitlab.springbootfirstfinal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findAllByEmail(String email);
    User findByEmail(String email);
}
