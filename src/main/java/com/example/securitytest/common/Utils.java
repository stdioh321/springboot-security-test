package com.example.securitytest.common;

import java.time.Instant;
import java.util.Date;

public class Utils {
    public static Date getCurrentDate() {
        return Date.from(Instant.now());
    }
}
