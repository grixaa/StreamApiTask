package org.studing.util;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import static io.github.cdimascio.dotenv.Dotenv.load;

@UtilityClass
public class EnvParser {
    private final Dotenv ENV = load();

    public String get(@NonNull final String key) {
        return ENV.get(key);
    }
}