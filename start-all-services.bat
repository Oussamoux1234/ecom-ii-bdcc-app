@echo off
REM ============================================
REM Ecom Microservices Startup Script
REM ============================================
REM Uses global 'mvn' command (requires Maven installed)
REM Run this from the project root directory
REM ============================================

echo.
echo ============================================
echo   ECOM MICROSERVICES STARTUP SCRIPT
echo ============================================
echo.

REM Check if Maven is available
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven is not installed or not in PATH!
    echo Please install Maven and add it to your PATH.
    echo Download: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

REM Set the project root directory
set PROJECT_ROOT=%~dp0

REM ============================================
REM Step 1: Start Kafka (Docker) - Optional
REM ============================================
echo [1/9] Checking Docker for Kafka...
docker info >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Docker is running. Starting Kafka...
    cd /d "%PROJECT_ROOT%kafka-spring-cloud"
    docker-compose up -d
    echo Waiting 10 seconds for Kafka to initialize...
    timeout /t 10 /nobreak > nul
) else (
    echo WARNING: Docker is not running. Skipping Kafka.
    echo Start Docker Desktop manually if you need Kafka.
    timeout /t 3 /nobreak > nul
)

REM ============================================
REM Step 2: Start Config Service FIRST
REM ============================================
echo.
echo [2/9] Starting Config Service (required by other services)...
cd /d "%PROJECT_ROOT%config-service"
start "Config-Service" cmd /k "mvn spring-boot:run"
echo Waiting 30 seconds for Config Service to start...
timeout /t 30 /nobreak > nul

REM ============================================
REM Step 3: Start Discovery Service (Eureka)
REM ============================================
echo.
echo [3/9] Starting Discovery Service (Eureka)...
cd /d "%PROJECT_ROOT%discovery-service"
start "Eureka-Discovery" cmd /k "mvn spring-boot:run"
echo Waiting 30 seconds for Eureka to start...
timeout /t 30 /nobreak > nul

REM ============================================
REM Step 4: Start Customer Service
REM ============================================
echo.
echo [4/9] Starting Customer Service...
cd /d "%PROJECT_ROOT%customer-service"
start "Customer-Service" cmd /k "mvn spring-boot:run"
timeout /t 30 /nobreak > nul

REM ============================================
REM Step 5: Start Inventory Service
REM ============================================
echo.
echo [5/9] Starting Inventory Service...
cd /d "%PROJECT_ROOT%inventary-service"
start "Inventory-Service" cmd /k "mvn spring-boot:run"
timeout /t 30 /nobreak > nul

REM ============================================
REM Step 6: Start Billing Service
REM ============================================
echo.
echo [6/9] Starting Billing Service...
cd /d "%PROJECT_ROOT%billing-service"
start "Billing-Service" cmd /k "mvn spring-boot:run"
timeout /t 30 /nobreak > nul

REM ============================================
REM Step 7: Start Kafka Spring Cloud
REM ============================================
echo.
echo [7/9] Starting Kafka Spring Cloud...
cd /d "%PROJECT_ROOT%kafka-spring-cloud"
start "Kafka-Spring-Cloud" cmd /k "mvn spring-boot:run"
timeout /t 30 /nobreak > nul

REM ============================================
REM Step 8: Start Gateway Service
REM ============================================
echo.
echo [8/9] Starting Gateway Service...
cd /d "%PROJECT_ROOT%gatewey-service"
start "Gateway-Service" cmd /k "mvn spring-boot:run"
timeout /t 30 /nobreak > nul

REM ============================================
REM Step 9: Start Agent Bot
REM ============================================
echo.
echo [9/9] Starting Agent Bot...
cd /d "%PROJECT_ROOT%agent-bot"
start "Agent-Bot" cmd /k "mvn spring-boot:run"

REM Return to project root
cd /d "%PROJECT_ROOT%"

echo.
echo ============================================
echo   STARTUP INITIATED!
echo ============================================
echo.
echo Service URLs (wait for services to fully start):
echo   - Config Server:     http://localhost:9999
echo   - Eureka Dashboard:  http://localhost:8761
echo   - Gateway:           http://localhost:8888
echo   - Customer Service:  http://localhost:8081
echo   - Inventory Service: http://localhost:8082
echo   - Billing Service:   http://localhost:8083
echo   - Kafka Cloud:       http://localhost:8085
echo   - Agent Bot:         http://localhost:8087
echo.
echo NOTE: Each service window stays open to show logs/errors.
echo.
echo To start Angular frontend:
echo   cd micro-services-app-frontend
echo   npm install
echo   ng serve
echo.
echo ============================================
pause
