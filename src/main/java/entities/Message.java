package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Date date;

    @OneToOne()
    User user;
}
