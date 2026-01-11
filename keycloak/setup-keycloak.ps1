# ============================================
# Keycloak Auto-Configuration Script
# ============================================
# This script automatically configures Keycloak with:
# - Realm: ecom-realm
# - Client: ecom-frontend (for Angular)
# - Client: ecom-bot (for Telegram bot)
# - User: testuser / password
# ============================================

param(
    [string]$KeycloakUrl = "http://localhost:8080",
    [string]$AdminUser = "admin",
    [string]$AdminPassword = "admin",
    [string]$RealmName = "ecom-realm"
)

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  KEYCLOAK AUTO-CONFIGURATION SCRIPT" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# ============================================
# Step 1: Wait for Keycloak to be ready
# ============================================
Write-Host "[1/7] Checking if Keycloak is ready..." -ForegroundColor Yellow

$maxAttempts = 30
$attempt = 0
$ready = $false

while (-not $ready -and $attempt -lt $maxAttempts) {
    try {
        $response = Invoke-WebRequest -Uri "$KeycloakUrl/realms/master" -Method Get -UseBasicParsing -ErrorAction Stop
        if ($response.StatusCode -eq 200) {
            $ready = $true
        }
    }
    catch {
        $attempt++
        Write-Host "  Waiting for Keycloak... ($attempt/$maxAttempts)" -ForegroundColor Gray
        Start-Sleep -Seconds 2
    }
}

if (-not $ready) {
    Write-Host "ERROR: Keycloak is not responding at $KeycloakUrl" -ForegroundColor Red
    Write-Host "Make sure Keycloak is running: docker-compose up -d" -ForegroundColor Red
    exit 1
}

Write-Host "  Keycloak is ready!" -ForegroundColor Green

# ============================================
# Step 2: Get Admin Access Token
# ============================================
Write-Host "[2/7] Getting admin access token..." -ForegroundColor Yellow

$tokenBody = "grant_type=password&client_id=admin-cli&username=$AdminUser&password=$AdminPassword"

try {
    $tokenResponse = Invoke-RestMethod -Uri "$KeycloakUrl/realms/master/protocol/openid-connect/token" `
        -Method Post `
        -ContentType "application/x-www-form-urlencoded" `
        -Body $tokenBody
    
    $accessToken = $tokenResponse.access_token
    Write-Host "  Token obtained!" -ForegroundColor Green
}
catch {
    Write-Host "ERROR: Failed to get admin token. Check admin credentials." -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    exit 1
}

$headers = @{
    Authorization  = "Bearer $accessToken"
    "Content-Type" = "application/json"
}

# ============================================
# Step 3: Create Realm
# ============================================
Write-Host "[3/7] Creating realm '$RealmName'..." -ForegroundColor Yellow

$realmConfig = @{
    realm                  = $RealmName
    enabled                = $true
    registrationAllowed    = $true
    loginWithEmailAllowed  = $true
    duplicateEmailsAllowed = $false
    resetPasswordAllowed   = $true
    editUsernameAllowed    = $false
    bruteForceProtected    = $true
} | ConvertTo-Json

try {
    $null = Invoke-RestMethod -Uri "$KeycloakUrl/admin/realms/$RealmName" `
        -Method Get -Headers $headers -ErrorAction Stop
    Write-Host "  Realm '$RealmName' already exists, skipping..." -ForegroundColor Gray
}
catch {
    try {
        Invoke-RestMethod -Uri "$KeycloakUrl/admin/realms" `
            -Method Post -Headers $headers -Body $realmConfig | Out-Null
        Write-Host "  Realm '$RealmName' created!" -ForegroundColor Green
    }
    catch {
        Write-Host "ERROR: Failed to create realm - $($_.Exception.Message)" -ForegroundColor Red
    }
}

# ============================================
# Step 4: Create/Update Angular Frontend Client
# ============================================
Write-Host "[4/7] Configuring client 'ecom-frontend'..." -ForegroundColor Yellow

# First try to delete existing client
try {
    $clients = Invoke-RestMethod -Uri "$KeycloakUrl/admin/realms/$RealmName/clients?clientId=ecom-frontend" `
        -Method Get -Headers $headers
    if ($clients.Count -gt 0) {
        $clientUuid = $clients[0].id
        Invoke-RestMethod -Uri "$KeycloakUrl/admin/realms/$RealmName/clients/$clientUuid" `
            -Method Delete -Headers $headers | Out-Null
        Write-Host "  Deleted existing client..." -ForegroundColor Gray
    }
}
catch {}

$frontendClient = @{
    clientId                  = "ecom-frontend"
    name                      = "Ecom Angular Frontend"
    enabled                   = $true
    publicClient              = $true
    directAccessGrantsEnabled = $true
    standardFlowEnabled       = $true
    implicitFlowEnabled       = $false
    redirectUris              = @(
        "http://localhost:4200/*",
        "http://localhost:63887/*",
        "http://localhost/*"
    )
    webOrigins                = @(
        "http://localhost:4200",
        "http://localhost:63887",
        "http://localhost",
        "+"
    )
    protocol                  = "openid-connect"
} | ConvertTo-Json

try {
    Invoke-RestMethod -Uri "$KeycloakUrl/admin/realms/$RealmName/clients" `
        -Method Post -Headers $headers -Body $frontendClient | Out-Null
    Write-Host "  Client 'ecom-frontend' configured with CORS for all localhost ports!" -ForegroundColor Green
}
catch {
    Write-Host "  Warning: $($_.Exception.Message)" -ForegroundColor Yellow
}

# ============================================
# Step 5: Create Bot Client (Confidential)
# ============================================
Write-Host "[5/7] Creating client 'ecom-bot'..." -ForegroundColor Yellow

$botClient = @{
    clientId                  = "ecom-bot"
    name                      = "Ecom Telegram Bot"
    enabled                   = $true
    publicClient              = $false
    serviceAccountsEnabled    = $true
    directAccessGrantsEnabled = $true
    standardFlowEnabled       = $false
    protocol                  = "openid-connect"
    secret                    = "ecom-bot-secret-123"
} | ConvertTo-Json

try {
    Invoke-RestMethod -Uri "$KeycloakUrl/admin/realms/$RealmName/clients" `
        -Method Post -Headers $headers -Body $botClient | Out-Null
    Write-Host "  Client 'ecom-bot' created!" -ForegroundColor Green
    Write-Host "  Bot Client Secret: ecom-bot-secret-123" -ForegroundColor Cyan
}
catch {
    if ($_.Exception.Response.StatusCode.value__ -eq 409) {
        Write-Host "  Client 'ecom-bot' already exists, skipping..." -ForegroundColor Gray
    }
    else {
        Write-Host "  Warning: $($_.Exception.Message)" -ForegroundColor Yellow
    }
}

# ============================================
# Step 6: Create Test User
# ============================================
Write-Host "[6/7] Creating user 'testuser'..." -ForegroundColor Yellow

$testUser = @{
    username      = "testuser"
    email         = "test@example.com"
    firstName     = "Test"
    lastName      = "User"
    enabled       = $true
    emailVerified = $true
    credentials   = @(
        @{
            type      = "password"
            value     = "password"
            temporary = $false
        }
    )
} | ConvertTo-Json -Depth 3

try {
    Invoke-RestMethod -Uri "$KeycloakUrl/admin/realms/$RealmName/users" `
        -Method Post -Headers $headers -Body $testUser | Out-Null
    Write-Host "  User 'testuser' created!" -ForegroundColor Green
}
catch {
    if ($_.Exception.Response.StatusCode.value__ -eq 409) {
        Write-Host "  User 'testuser' already exists, skipping..." -ForegroundColor Gray
    }
    else {
        Write-Host "  Warning: $($_.Exception.Message)" -ForegroundColor Yellow
    }
}

# ============================================
# Step 7: Verification
# ============================================
Write-Host "[7/7] Verifying configuration..." -ForegroundColor Yellow

try {
    $clients = Invoke-RestMethod -Uri "$KeycloakUrl/admin/realms/$RealmName/clients?clientId=ecom-frontend" `
        -Method Get -Headers $headers
    if ($clients.Count -gt 0) {
        Write-Host "  ecom-frontend client verified!" -ForegroundColor Green
        Write-Host "  Web Origins: $($clients[0].webOrigins -join ', ')" -ForegroundColor Cyan
    }
}
catch {}

# ============================================
# Done!
# ============================================
Write-Host ""
Write-Host "============================================" -ForegroundColor Green
Write-Host "  KEYCLOAK CONFIGURATION COMPLETE!" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Green
Write-Host ""
Write-Host "Realm:           $RealmName" -ForegroundColor White
Write-Host "Frontend Client: ecom-frontend (public)" -ForegroundColor White
Write-Host "Bot Client:      ecom-bot (secret: ecom-bot-secret-123)" -ForegroundColor White
Write-Host "Test User:       testuser / password" -ForegroundColor White
Write-Host ""
Write-Host "CORS Origins:    localhost:4200, localhost:63887, +" -ForegroundColor Cyan
Write-Host ""
Write-Host "Keycloak Admin:  $KeycloakUrl/admin" -ForegroundColor Cyan
Write-Host ""
