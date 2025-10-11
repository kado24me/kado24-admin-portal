# gifthub-admin-portal

This is the Admin Portal web application (Spring Boot + Thymeleaf).

Quick run instructions (macOS / zsh)

1) Install dependencies (Maven)

If you don't have Maven installed, use the helper script:

```bash
./scripts/install-maven.sh
```

2) Run in development mode (hot restart via spring-boot:run)

```bash
./scripts/run-dev.sh
# or, if you have mvn available
mvn spring-boot:run
```

3) Build & run packaged jar

```bash
mvn -DskipTests clean package
java -jar target/admin-portal-0.0.1-SNAPSHOT.jar
```

Convenience Make targets (macOS):

```bash
make install-maven   # install maven via Homebrew
make run-dev         # run development server
make build           # package the app (skips tests)
make run             # run packaged jar
```

Notes
- Project requires Java 17. Verify with `java -version`.
- Consider adding the Maven wrapper (`mvnw`) to the repository if you'd like consistent builds on machines without Maven. You can create it with `mvn -N io.takari:maven:wrapper` on a machine that has Maven.
