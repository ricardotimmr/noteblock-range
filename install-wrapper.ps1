# Install Gradle Wrapper
$wrapperDir = ".\gradle\wrapper"
$wrapperJarUrl = "https://services.gradle.org/distributions/gradle-8.8-bin.zip"
$wrapperZip = "$env:TEMP\gradle-wrapper.zip"
$gradleDir = "$env:TEMP\gradle-8.8"

# Create wrapper directory
if (-not (Test-Path $wrapperDir)) {
    New-Item -ItemType Directory -Path $wrapperDir -Force | Out-Null
}

# Download Gradle distribution
Write-Host "Downloading Gradle wrapper from $wrapperJarUrl ..."
Invoke-WebRequest -Uri $wrapperJarUrl -OutFile $wrapperZip

# Extract to temp directory
Write-Host "Extracting Gradle..."
Expand-Archive -Path $wrapperZip -DestinationPath $env:TEMP -Force

# Copy wrapper JAR
Write-Host "Installing wrapper JAR..."
Copy-Item "$gradleDir\lib\gradle-wrapper.jar" -Destination "$wrapperDir\gradle-wrapper.jar" -Force

# Cleanup
Remove-Item $wrapperZip -Force

Write-Host "Gradle wrapper installed successfully!"
