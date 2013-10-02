package org.funcode.htmlbeans.wrappers;

public enum ElementType {

    BOOLEAN,
    STRING,
    FLOAT,
    INTEGER,
    LONG,
    COMPLEX,
    LIST,
    DOUBLE,
    BYTE;


    public static boolean isPrimitive(ElementType type) {
         return type == BOOLEAN
                 || type == STRING
                 || type == FLOAT
                 || type == INTEGER
                 || type == LONG
                 || type == DOUBLE
                 || type == BYTE;
    }

}
