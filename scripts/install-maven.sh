#!/usr/bin/env zsh
# Install Maven on macOS using Homebrew if missing
set -euo pipefail

if command -v mvn >/dev/null 2>&1; then
  echo "Maven already installed: $(mvn -v | head -n1)"
  exit 0
fi

if ! command -v brew >/dev/null 2>&1; then
  echo "Homebrew not found. Installing Homebrew..."
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
  echo "Please follow any Homebrew post-install steps the script printed, then re-run this script if necessary."
fi

echo "Installing Maven via Homebrew..."
brew update
brew install maven

echo "Maven installed:";
mvn -v
