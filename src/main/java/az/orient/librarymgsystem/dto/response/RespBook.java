package az.orient.librarymgsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespBook {
    private Long id;
    private String title;
    private boolean borrowed;
    private List<RespAuthor> respAuthors;
    private RespUser borrowedBy;
}
