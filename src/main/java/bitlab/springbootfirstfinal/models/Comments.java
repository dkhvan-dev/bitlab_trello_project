package bitlab.springbootfirstfinal.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long commentsId;

    @Column(name = "author")
    private String commentAuthor;

    @Column(name = "comment")
    @Type(type = "text")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tasks task;
}
