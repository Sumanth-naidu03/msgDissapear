# Setup Guide

## Quick Setup Instructions

### 1. Database Setup (PostgreSQL)

**Option A: Using createdb command**
```bash
createdb -U postgres msgdissapear
```

**Option B: Using SQL**
```sql
CREATE DATABASE msgdissapear;
```

**Option C: Using the provided batch file (Windows)**
```cmd
createdb.bat
```

### 2. Configure Database Password

Edit `msgDissapear/src/main/resources/application.properties`:
```properties
spring.datasource.password=YourActualPassword
```

### 3. Run the Application

```bash
cd msgDissapear
./mvnw spring-boot:run
```

### 4. Access the App

Open browser: http://localhost:8080

### 5. Test the Features

1. **Register** a new account (e.g., username: "alice", password: "test123")
2. **Login** with your credentials  
3. **Click + Add** to add a contact by username
4. **Send a message** and watch the countdown timer
5. **Register another account** in a different browser/incognito tab to test two-way messaging

## Common Issues

### Database Connection Failed
- Ensure PostgreSQL is running
- Check username/password in application.properties
- Verify database "msgdissapear" exists

### Port 8080 Already in Use
```bash
# Kill process using port 8080
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

### Maven Build Fails
```bash
# Clean and rebuild
./mvnw clean install
```