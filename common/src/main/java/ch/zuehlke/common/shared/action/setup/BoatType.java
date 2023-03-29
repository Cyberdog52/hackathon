package ch.zuehlke.common.shared.action.setup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoatType {

    LARGE(3),
    MEDIUM(2),
    SMALL(1);

    private final int boatSize;
}
