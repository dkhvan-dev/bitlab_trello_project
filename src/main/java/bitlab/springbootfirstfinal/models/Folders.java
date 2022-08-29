package bitlab.springbootfirstfinal.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "folders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Folders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long folderId;

    @Column(name = "name")
    private String folderName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<TaskCategories> categories;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
