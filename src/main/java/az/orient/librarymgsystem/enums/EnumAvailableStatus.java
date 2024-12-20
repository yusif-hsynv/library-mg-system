package az.orient.librarymgsystem.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum EnumAvailableStatus {
    ACTIVE(1), DEACTIVE(0);

    public int value;
}
