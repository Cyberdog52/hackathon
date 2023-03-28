package ch.zuehlke.common;

import lombok.Builder;

@Builder
public record Coordinate(
        int x,
        int y
) {

}
