#!/bin/sh

# Gradle Wrapper initialization script for Unix-like systems
# This script downloads and executes Gradle

APP_NAME="gradlew"
APP_BASE_NAME=$(basename "$0")
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

cd "$DIR"
exec "$JAVA_HOME/bin/java" -jar gradle/wrapper/gradle-wrapper.jar "$@"
