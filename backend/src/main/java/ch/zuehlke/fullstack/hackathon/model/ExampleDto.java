package ch.zuehlke.fullstack.hackathon.model;


public class ExampleDto {

    private final String name;
    private final int value;

    public ExampleDto(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
