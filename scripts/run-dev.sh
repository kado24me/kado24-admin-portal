#!/usr/bin/env zsh
# Run Spring Boot app in development mode. Prefers ./mvnw if present.
set -euo pipefail

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
cd "$ROOT_DIR"

: ${MAVEN_OPTS:="-Xmx1g -Xms256m"}

if [ -x "./mvnw" ]; then
  echo "Using project Maven wrapper ./mvnw"
  ./mvnw spring-boot:run
else
  if ! command -v mvn >/dev/null 2>&1; then
    echo "mvn not found. Run ./scripts/install-maven.sh to install Maven or install it manually."
    exit 2
  fi
  echo "Using system mvn"
  MAVEN_OPTS="$MAVEN_OPTS" mvn spring-boot:run
fi
