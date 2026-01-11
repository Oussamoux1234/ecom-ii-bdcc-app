# Keycloak Setup Guide for Ecom Microservices

## Step 1: Start Keycloak with Docker

```powershell
cd "c:\Projets IntelliJ IDEA\ecom_ii_bdcc_app\keycloak"
docker-compose up -d
```

Wait about 30 seconds for Keycloak to start, then open:
**http://localhost:8080**

## Step 2: Login to Admin Console

- Click **Administration Console**
- Username: `admin`
- Password: `admin`

## Step 3: Create Realm

1. Hover over "master" dropdown (top left) → Click **Create Realm**
2. Realm name: `ecom-realm`
3. Click **Create**

## Step 4: Create Client for Angular Frontend

1. Go to **Clients** → **Create client**
2. Configure:
   - Client type: **OpenID Connect**
   - Client ID: `ecom-frontend`
   - Click **Next**
3. Client authentication: **OFF** (public client)
   - Click **Next**
4. Valid redirect URIs: 
   - `http://localhost:4200/*`
   - `http://localhost:*`
5. Web origins: 
   - `http://localhost:4200`
   - `http://localhost:*`
6. Click **Save**

## Step 5: Create Client for Telegram Bot (Optional)

1. Go to **Clients** → **Create client**
2. Configure:
   - Client type: **OpenID Connect**
   - Client ID: `ecom-bot`
   - Click **Next**
3. Client authentication: **ON** (confidential)
   - Click **Next**
4. Service accounts roles: **ON**
5. Click **Save**
6. Go to **Credentials** tab → Copy the **Client secret**
7. Update `agent-bot/src/main/resources/application.properties`:
   ```properties
   keycloak.client-secret=YOUR_CLIENT_SECRET_HERE
   ```

## Step 6: Create Test User

1. Go to **Users** → **Add user**
2. Fill in:
   - Username: `testuser`
   - Email: `test@example.com`
   - First name: `Test`
   - Last name: `User`
3. Click **Create**
4. Go to **Credentials** tab
5. Click **Set password**
   - Password: `password`
   - Temporary: **OFF**
6. Click **Save**

## Step 7: Enable Security in Services

Update each service's properties in `config-repo/`:

```properties
# Change from false to true
security.enabled=true
```

Files to update:
- `config-repo/customer-service.properties`
- `config-repo/billing-service.properties`
- `config-repo/inventory-service.properties`
- `config-repo/gateway-service.properties`

## Step 8: Restart All Services

After updating properties, restart all microservices to apply security.

## Testing

1. Open http://localhost:4200
2. You'll be redirected to Keycloak login
3. Login with: `testuser` / `password`
4. You should see the application with data!

## Troubleshooting

- **Keycloak not starting?** Check Docker is running: `docker ps`
- **Login redirect loops?** Clear browser cookies for localhost
- **CORS errors?** Make sure Gateway was restarted after security changes
