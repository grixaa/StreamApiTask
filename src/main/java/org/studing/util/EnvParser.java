package org.studing.util;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;

import static io.github.cdimascio.dotenv.Dotenv.load;

public class EnvParser {
    private final static Dotenv ENV = load();

    public static String get(@NonNull final String key) {
        return ENV.get(key);
    }
}
