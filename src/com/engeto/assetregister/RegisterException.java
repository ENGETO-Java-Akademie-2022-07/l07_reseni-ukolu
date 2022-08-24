package com.engeto.assetregister;

import java.io.FileNotFoundException;

public class RegisterException extends Throwable {
    public RegisterException(Exception e) {
        super(e.getLocalizedMessage());
    }

    public RegisterException(String s) {
        super(s);
    }
}
