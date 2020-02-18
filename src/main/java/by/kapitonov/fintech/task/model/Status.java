package by.kapitonov.fintech.task.model;

public enum Status {

    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private String name;

    Status(String name) {
        this.name = name;
    }
}
