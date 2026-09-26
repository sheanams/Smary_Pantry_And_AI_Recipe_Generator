@echo off
setlocal enabledelayedexpansion

echo.
echo  ======================================================================
echo              SmartPantry - AI Kitchen Intelligence Platform
echo                     Enterprise Landing Page Launcher
echo  ======================================================================
echo.

REM 1. Check if javac is in PATH
where javac >nul 2>nul
if %ERRORLEVEL% equ 0 (
    set "JAVAC_CMD=javac"
    set "JAVA_CMD=java"
    goto :COMPILE
)

REM 2. Check known Microsoft OpenJDK path
if exist "C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot\bin\javac.exe" (
    set "JAVAC_CMD=C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot\bin\javac.exe"
    set "JAVA_CMD=C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot\bin\java.exe"
    goto :COMPILE
)

REM 3. Search common JDK locations
for /d %%D in ("C:\Program Files\Microsoft\jdk*") do (
    if exist "%%D\bin\javac.exe" (
        set "JAVAC_CMD=%%D\bin\javac.exe"
        set "JAVA_CMD=%%D\bin\java.exe"
        goto :COMPILE
    )
)
for /d %%D in ("C:\Program Files\Java\jdk*") do (
    if exist "%%D\bin\javac.exe" (
        set "JAVAC_CMD=%%D\bin\javac.exe"
        set "JAVA_CMD=%%D\bin\java.exe"
        goto :COMPILE
    )
)
for /d %%D in ("C:\Program Files\Eclipse Adoptium\jdk*") do (
    if exist "%%D\bin\javac.exe" (
        set "JAVAC_CMD=%%D\bin\javac.exe"
        set "JAVA_CMD=%%D\bin\java.exe"
        goto :COMPILE
    )
)

echo [ERROR] Java Development Kit (JDK) not found.
echo Please ensure JDK 17+ is installed and configured in PATH.
pause
exit /b 1

:COMPILE
echo [1/2] Compiling SmartPantry Java sources...
if not exist "out" mkdir out
"%JAVAC_CMD%" -d out -sourcepath src src\com\smartpantry\landing\SmartPantryLanding.java
if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Compilation failed. Please check source errors.
    pause
    exit /b 1
)

echo [2/2] Launching SmartPantry Landing Page...
echo.
"%JAVA_CMD%" -cp out com.smartpantry.landing.SmartPantryLanding

endlocal
