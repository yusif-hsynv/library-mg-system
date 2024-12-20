package az.orient.librarymgsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespUser {
    private Long id;
    private String name;
    private String surname;
    private String contactPhone;
    private String address;
}
