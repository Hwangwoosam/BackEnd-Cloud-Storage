package org.example.enums;

import lombok.Getter;

@Getter
public enum FileType {
    FILE("FOLDER",0),
    FOLDER("FILE",1);

    private final String value;
    private final Integer code;

    FileType(String value, Integer code) {
        this.value = value;
        this.code = code;
    }

    public static FileType fromCode(int code) {
        for (FileType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid FileType code: " + code);
    }
}
