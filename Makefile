## Makefile for common development tasks

.PHONY: help install-maven run-dev build run

help:
	@echo "make install-maven   # Install Maven via Homebrew (macOS)"
	@echo "make run-dev         # Run the app in dev mode (spring-boot:run)"
	@echo "make build           # Clean and package the app"
	@echo "make run             # Run the packaged jar"

install-maven:
	@./scripts/install-maven.sh

run-dev:
	@mvn spring-boot:run

build:
	@mvn -DskipTests clean package

run:
	@java -jar target/admin-portal-0.0.1-SNAPSHOT.jar
