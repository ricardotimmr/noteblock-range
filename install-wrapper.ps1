# Restore the official wrapper if it is missing from a source archive.
$ErrorActionPreference = "Stop"
$wrapperDir = Join-Path $PSScriptRoot "gradle/wrapper"
$wrapperJar = Join-Path $wrapperDir "gradle-wrapper.jar"
$wrapperJarUrl = "https://raw.githubusercontent.com/gradle/gradle/v9.7.1/gradle/wrapper/gradle-wrapper.jar"
$expectedSha256 = "7A9CE74CFF467CA1BF60A4FCD9F05185ACCEDA4D0F382434D393E17864262C5D"

New-Item -ItemType Directory -Path $wrapperDir -Force | Out-Null
Invoke-WebRequest -Uri $wrapperJarUrl -OutFile $wrapperJar
if ((Get-FileHash -LiteralPath $wrapperJar -Algorithm SHA256).Hash -ne $expectedSha256) {
    Remove-Item -LiteralPath $wrapperJar
    throw "Gradle wrapper checksum mismatch."
}

Write-Host "Gradle 9.7.1 wrapper installed successfully."
