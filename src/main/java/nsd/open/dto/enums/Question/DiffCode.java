package nsd.open.dto.enums.Question;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiffCode {

    최하("01","ED001"),
    하("02","ED002"),
    중("03","ED003"),
    상("04","ED004"),
    최상("05","ED005");

    private final String code;
    private final String diffCode;

}
