package ch.zuehlke.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    private String name;

    public RegisterRequest(String name) {
        this.name = name;
    }
}
