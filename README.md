# Playtodoo Modulith

Este proyecto es una API basada en Spring Boot que gestiona complejos deportivos, usuarios y reservas.

## Documentación Swagger

La documentación interactiva se genera automáticamente con **SpringDoc OpenAPI**.

Para visualizarla, ejecuta la aplicación y visita:

```
http://localhost:8080/swagger-ui.html
```

o bien

```
http://localhost:8080/swagger-ui/index.html
```

En estas páginas encontrarás todas las rutas organizadas por categoría y descritas en español.

### Ejecución

1. Compila el proyecto (se requiere Java 21 y Maven):
   ```bash
   ./mvnw spring-boot:run
   ```
2. Abre tu navegador en la URL indicada arriba.

La configuración base de Swagger se encuentra en `OpenApiConfig.java`.

