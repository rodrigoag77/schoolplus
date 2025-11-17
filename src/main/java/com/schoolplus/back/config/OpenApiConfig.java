package com.schoolplus.back.config;

import io.swagger.v3.oas.models.Paths;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            Paths paths = openApi.getPaths();

            LinkedHashMap<String, ?> sortedPaths = paths.entrySet()
                    .stream()
                    .sorted((entry1, entry2) -> {
                        String path1 = entry1.getKey();
                        String path2 = entry2.getKey();

                        boolean isAuth1 = path1.contains("login");
                        boolean isAuth2 = path2.contains("login");

                        if (isAuth1 && !isAuth2)
                            return -1;
                        if (!isAuth1 && isAuth2)
                            return 1;

                        return path1.compareTo(path2);
                    })
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (a, b) -> a,
                            LinkedHashMap::new));

            openApi.setPaths(new Paths());
            sortedPaths.forEach((path, item) -> {
                openApi.getPaths().put(path, (io.swagger.v3.oas.models.PathItem) item);
            });
        };
    }
}
