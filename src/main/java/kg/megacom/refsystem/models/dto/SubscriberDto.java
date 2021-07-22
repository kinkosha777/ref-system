package kg.megacom.refsystem.models.dto;

import lombok.Data;

import java.util.Date;
@Data
public class SubscriberDto {
    private Long id;
    private String phone;
    private Boolean active;
    private Date editDate;
    private Date addDate;
}
