package org.studing.util;

import lombok.NonNull;

import static io.github.cdimascio.dotenv.Dotenv.load;

public class EnvParser {
    public static String get(@NonNull final String key) {
        return load().get(key);
    }
}
