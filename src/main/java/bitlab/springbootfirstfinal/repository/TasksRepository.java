package bitlab.springbootfirstfinal.repository;

import bitlab.springbootfirstfinal.models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> searchAllByFolder_FolderIdOrderByTaskId(Long id);
}
