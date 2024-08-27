package nsd.open.payload.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RequestQType {
    대교(0),
    천재교육(1);
    private final int qTypeId;

}
