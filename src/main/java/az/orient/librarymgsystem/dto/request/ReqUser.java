package az.orient.librarymgsystem.dto.request;

import lombok.Data;

@Data
public class ReqUser {
    private Long id;
    private String name;
    private String surname;
    private String contactPhone;
    private String address;
}
