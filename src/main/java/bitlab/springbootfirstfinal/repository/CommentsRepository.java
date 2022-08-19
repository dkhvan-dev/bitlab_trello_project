package bitlab.springbootfirstfinal.repository;

import bitlab.springbootfirstfinal.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> searchAllByTask_TaskIdOrderByCommentsIdDesc(Long taskId);
}
