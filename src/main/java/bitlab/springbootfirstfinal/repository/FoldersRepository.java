package bitlab.springbootfirstfinal.repository;

import bitlab.springbootfirstfinal.models.Folders;
import bitlab.springbootfirstfinal.models.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface FoldersRepository extends JpaRepository<Folders, Long> {
    List<Folders> findAllByOrderByFolderIdAsc();
}
