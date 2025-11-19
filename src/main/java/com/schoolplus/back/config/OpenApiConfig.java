package com.schoolplus.back.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.PathItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SchoolPlus API")
                        .description("API de gestão escolar - SchoolPlus")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SchoolPlus Team")
                                .email("contact@schoolplus.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            LinkedHashMap<String, String> temp = new LinkedHashMap<>();
            temp.put("auth-controller", "Autenticação");
            temp.put("city-controller", "Cidades");
            temp.put("user-controller", "Usuários");
            temp.put("class-controller", "Turmas");
            temp.put("member-controller", "Membros");
            temp.put("address-controller", "Endereços");
            temp.put("subject-controller", "Disciplinas");
            temp.put("schedule-controller", "Horários");
            temp.put("department-controller", "Departamentos");

            final LinkedHashMap<String, String> tagRenames = sortTags(temp);

            if (openApi.getTags() != null) {
                openApi.getTags().forEach(tag -> {
                    String newName = tagRenames.get(tag.getName());
                    if (newName != null)
                        tag.setName(newName);
                });
            }

            Paths paths = openApi.getPaths();

            if (paths != null) {
                paths.values().forEach(pathItem -> {
                    if (pathItem.getGet() != null && pathItem.getGet().getTags() != null)
                        pathItem.getGet().getTags().replaceAll(tag -> tagRenames.getOrDefault(tag, tag));
                    if (pathItem.getPost() != null && pathItem.getPost().getTags() != null)
                        pathItem.getPost().getTags().replaceAll(tag -> tagRenames.getOrDefault(tag, tag));
                    if (pathItem.getPut() != null && pathItem.getPut().getTags() != null)
                        pathItem.getPut().getTags().replaceAll(tag -> tagRenames.getOrDefault(tag, tag));
                    if (pathItem.getDelete() != null && pathItem.getDelete().getTags() != null)
                        pathItem.getDelete().getTags().replaceAll(tag -> tagRenames.getOrDefault(tag, tag));
                    if (pathItem.getPatch() != null && pathItem.getPatch().getTags() != null)
                        pathItem.getPatch().getTags().replaceAll(tag -> tagRenames.getOrDefault(tag, tag));
                });

                LinkedHashMap<String, ?> sortedPaths = paths.entrySet()
                        .stream()
                        .sorted((entry1, entry2) -> {
                            PathItem pathItem1 = (PathItem) entry1.getValue();
                            PathItem pathItem2 = (PathItem) entry2.getValue();

                            String tag1 = getFirstTag(entry1.getKey(), pathItem1);
                            String tag2 = getFirstTag(entry2.getKey(), pathItem2);

                            java.util.List<String> tagOrder = new java.util.ArrayList<>(tagRenames.values());
                            int tagIndex1 = tagOrder.indexOf(tag1);
                            int tagIndex2 = tagOrder.indexOf(tag2);

                            if (tagIndex1 == -1)
                                tagIndex1 = Integer.MAX_VALUE;
                            if (tagIndex2 == -1)
                                tagIndex2 = Integer.MAX_VALUE;

                            if (tagIndex1 != tagIndex2)
                                return Integer.compare(tagIndex1, tagIndex2);

                            return entry1.getKey().compareTo(entry2.getKey());
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
            }
        };
    }

    private String getFirstTag(String key, PathItem pathItem) {
        if (pathItem.getGet() != null && pathItem.getGet().getTags() != null
                && !pathItem.getGet().getTags().isEmpty()) {
            return pathItem.getGet().getTags().get(0);
        }
        if (pathItem.getPost() != null && pathItem.getPost().getTags() != null
                && !pathItem.getPost().getTags().isEmpty()) {
            return pathItem.getPost().getTags().get(0);
        }
        if (pathItem.getPut() != null && pathItem.getPut().getTags() != null
                && !pathItem.getPut().getTags().isEmpty()) {
            return pathItem.getPut().getTags().get(0);
        }
        if (pathItem.getDelete() != null && pathItem.getDelete().getTags() != null
                && !pathItem.getDelete().getTags().isEmpty()) {
            return pathItem.getDelete().getTags().get(0);
        }
        if (pathItem.getPatch() != null && pathItem.getPatch().getTags() != null
                && !pathItem.getPatch().getTags().isEmpty()) {
            return pathItem.getPatch().getTags().get(0);
        }
        return key;
    }

    private LinkedHashMap<String, String> sortTags(LinkedHashMap<String, String> tags) {
        LinkedHashMap<String, String> sorted = new LinkedHashMap<>();

        // Adiciona Autenticação em primeiro
        String autenticacao = tags.get("auth-controller");
        if (autenticacao != null) {
            sorted.put("auth-controller", autenticacao);
        }

        // Ordena o resto alfabeticamente pelo valor (nome amigável)
        tags.entrySet().stream()
                .filter(e -> !e.getKey().equals("auth-controller"))
                .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
                .forEach(e -> sorted.put(e.getKey(), e.getValue()));

        return sorted;
    }
}
