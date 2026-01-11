@echo off
REM ============================================
REM Stop All Microservices Script
REM ============================================

echo.
echo ============================================
echo   STOPPING ALL MICROSERVICES
echo ============================================
echo.

REM Kill all Java processes (Spring Boot services)
echo Stopping all Spring Boot services...
taskkill /F /IM java.exe /T 2>nul
if %ERRORLEVEL% EQU 0 (
    echo Java processes stopped.
) else (
    echo No Java processes found or already stopped.
)

REM Stop Kafka Docker containers
echo.
echo Stopping Kafka Docker containers...
cd /d "%~dp0kafka-spring-cloud"
docker-compose down
if %ERRORLEVEL% EQU 0 (
    echo Kafka containers stopped.
) else (
    echo Kafka containers not running or Docker not available.
)

echo.
echo ============================================
echo   ALL SERVICES STOPPED!
echo ============================================
pause
