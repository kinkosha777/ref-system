package kg.megacom.refsystem.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "subscribers")
public class Subscriber {
    @Id
    private Long id;
    private String phone;
    private Boolean active;
    private Date editDate;
    private Date addDate;

}
