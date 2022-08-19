package bitlab.springbootfirstfinal.repository;

import bitlab.springbootfirstfinal.models.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TaskCategoriesRepository extends JpaRepository<TaskCategories, Long> {
    List<TaskCategories> findAllByOrderByCategoriesIdAsc();
}
