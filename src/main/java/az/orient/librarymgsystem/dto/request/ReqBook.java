package az.orient.librarymgsystem.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ReqBook {
    private Long id;
    private String title;
    private boolean borrowed;
    private List<Long> authorId;
    private Long userId;
}
