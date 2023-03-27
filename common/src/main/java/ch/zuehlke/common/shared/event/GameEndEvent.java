package ch.zuehlke.common.shared.event;

import java.util.UUID;

record GameEndEvent(
        UUID winnerId
) {

}