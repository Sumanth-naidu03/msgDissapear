# VanishChat - Disappearing Messages App 

A modern, secure messaging application built with Spring Boot and vanilla JavaScript that automatically deletes messages after a configurable time period. Messages disappear forever, ensuring your conversations remain private.

<img width="506" height="211" alt="image" src="https://github.com/user-attachments/assets/c2909e97-9392-489d-9849-469b17e96e61" />


##  Features

###  User Authentication
- **User Registration**: Create new accounts with username and password
- **Secure Login**: Authentication system with input validation
- **Session Management**: Persistent login sessions with sign-out functionality

###  Smart Messaging System
- **Disappearing Messages**: Configurable auto-deletion (24 hours, 7 days, 30 days, 90 days)
- **Real-time Countdown**: Live TTL display with color-coded urgency
- **Two-way Conversations**: See messages from both directions in chat threads
- **Automatic Message Cleanup**: Background scheduler removes expired messages

###  Contact Management
- **Auto-Discovery**: Users automatically appear when they message you
- **Manual Add**: Search and add contacts by username
- **Smart Contact List**: Shows all conversation partners automatically
- **No Mutual Contact Required**: Receive messages without pre-adding users

###  Modern UI/UX
- **Dark Theme**: Eye-friendly dark interface design
- **Responsive Layout**: Works on desktop and mobile devices
- **Real-time Updates**: Messages refresh every 3 seconds
- **Toast Notifications**: Instant feedback for user actions
- **Intuitive Navigation**: Simple, clean chat dashboard layout

###  Technical Features
- **RESTful API**: Clean REST endpoints for all operations
- **PostgreSQL Database**: Reliable data persistence
- **Scheduled Cleanup**: Automatic expired message removal
- **Input Validation**: Server-side and client-side validation
- **Error Handling**: Graceful error management and user feedback

##  Screenshots

### Login & Registration
<img width="548" height="663" alt="image" src="https://github.com/user-attachments/assets/68ffb5e1-cfe6-4d8a-b8a7-5f37c6a51fd0" />



### Chat Dashboard
<img width="1592" height="567" alt="image" src="https://github.com/user-attachments/assets/f3c096c0-b814-4c14-bbe0-da8ece3297df" />


### Message Timeline
<img width="352" height="260" alt="image" src="https://github.com/user-attachments/assets/13a7a743-5aa1-4352-b832-52447cda7053" />


### Contact Management
<img width="330" height="175" alt="image" src="https://github.com/user-attachments/assets/69c1158e-b49f-4a12-b398-6492e6a5862a" />


### Timer Settings
<img width="290" height="175" alt="image" src="https://github.com/user-attachments/assets/f29b5a13-45ca-49e8-9b94-60ea6778f549" />


##  Quick Start

### Prerequisites
- **Java 17+** (or Java 24 as configured)
- **PostgreSQL** database server
- **Maven** (included via wrapper)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/msgDissapear.git
   cd msgDissapear
   ```

2. **Set up PostgreSQL Database**
   ```bash
   # Create database
   createdb -U postgres msgdissapear
   
   # Or use the included batch file on Windows
   createdb.bat
   ```

3. **Configure Database Connection**
   
   Edit `msgDissapear/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/msgdissapear
   spring.datasource.username=postgres
   spring.datasource.password=YourPassword
   ```

4. **Run the Application**
   ```bash
   cd msgDissapear
   ./mvnw spring-boot:run
   
   # Or on Windows
   mvnw.cmd spring-boot:run
   ```

5. **Access the Application**
   
   Open your browser and navigate to: `http://localhost:8080`

##  Architecture

### Backend (Spring Boot)
```
├──  controller/          # REST API endpoints
├──  service/            # Business logic layer
├──  repository/         # Data access layer
├──  entity/            # JPA entities (User, Message, Contact)
├──  dto/               # Data transfer objects
├──  scheduler/         # Background cleanup tasks
└──  resources/         # Configuration and static files
```

### Database Schema
```sql
-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- Messages table  
CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    sender VARCHAR(255) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    read BOOLEAN NOT NULL DEFAULT FALSE
);

-- Contacts table
CREATE TABLE contacts (
    id BIGSERIAL PRIMARY KEY,
    owner VARCHAR(255) NOT NULL,
    contact_username VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    UNIQUE(owner, contact_username)
);
```

##  API Endpoints

### Authentication
- `POST /api/users/register` - Create new user account
- `POST /api/users/login` - User login
- `GET /api/users` - List all registered users

### Messages
- `POST /api/messages` - Send a new message
- `GET /api/messages/inbox/{username}` - Get received messages
- `GET /api/messages/sent/{username}` - Get sent messages
- `GET /api/messages/conversation/{userA}/{userB}` - Get conversation between two users
- `DELETE /api/messages/{id}` - Delete specific message

### Contacts
- `POST /api/contacts` - Add new contact
- `GET /api/contacts/{username}` - Get user's contact list
- `GET /api/contacts/{username}/conversations` - Get all conversation partners

## ⚙️ Configuration

### Message TTL Options
- **24 hours** (86400 seconds) - Default
- **7 days** (604800 seconds)
- **30 days** (2592000 seconds)  
- **90 days** (7776000 seconds)

### Cleanup Scheduler
Messages are automatically deleted every 60 seconds. Configure in `application.properties`:
```properties
message.cleanup.cron=0/60 * * * * *
```

### Database Settings
```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/msgdissapear
spring.datasource.username=postgres
spring.datasource.password=YourPassword

# JPA/Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## 🔒 Security Considerations

**Important**: This application uses plain text password storage for simplicity. In production, implement proper security measures:

- Use **Spring Security** with password hashing (BCrypt)
- Add **JWT tokens** or session management
- Implement **HTTPS/TLS** encryption
- Add **input sanitization** and **SQL injection** protection
- Consider **rate limiting** for API endpoints

##  Testing

### Manual Testing Flow
1. **Register** two user accounts (e.g., "Alice" and "Bob")
2. **Login as Alice** → Send message to "Bob" 
3. **Logout** → **Login as Bob** → Bob should see Alice's message automatically
4. **Reply as Bob** → Messages appear in both directions
5. **Wait for TTL** → Messages disappear after configured time

### API Testing with curl
```bash
# Register user
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Send message
curl -X POST http://localhost:8080/api/messages \
  -H "Content-Type: application/json" \
  -d '{"sender":"alice","recipient":"bob","content":"Hello!","ttlSeconds":3600}'
```

##  Features Breakdown

| Feature | Status | Description |
|---------|---------|-------------|
| User Registration | Complete | Create accounts with username/password |
|  User Authentication | Complete | Login system with session management |
|  Send Messages | Complete | Send messages with configurable TTL |
|  Receive Messages | Complete | View incoming messages in conversations |
|  Auto-Delete Messages | Complete | Scheduled cleanup of expired messages |
|  Contact Management | Complete | Manual add + auto-discovery of message senders |
|  Real-time Countdown | Complete | Live TTL display with color coding |
|  Two-way Conversations | Complete | Messages from both directions in single thread |
|  Modern UI | Complete | Dark theme, responsive design |
|  PostgreSQL Integration | Complete | Full database persistence |

##  Roadmap

### Upcoming Features
- [ ] **Message Encryption** - End-to-end encryption for message content
- [ ] **Group Chats** - Support for multi-user conversations
- [ ] **File Attachments** - Send images and files with TTL
- [ ] **Message Reactions** - React to messages with emojis
- [ ] **Push Notifications** - Real-time message notifications
- [ ] **Mobile App** - Native iOS/Android applications
- [ ] **Message Search** - Search through conversation history
- [ ] **User Status** - Online/offline indicators

### Technical Improvements
- [ ] **Spring Security Integration** - Proper authentication and authorization
- [ ] **WebSocket Support** - Real-time message delivery
- [ ] **Redis Caching** - Improved performance for message retrieval
- [ ] **Docker Support** - Containerized deployment
- [ ] **Unit Tests** - Comprehensive test coverage
- [ ] **API Documentation** - Swagger/OpenAPI integration

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request



##  Authors

- **Your Name** - *Initial work* - [YourGitHub](https://github.com/Sumanth-naidu03)

## 🙏 Acknowledgments

- Built with **Spring Boot** framework
- **PostgreSQL** for reliable data storage
- Modern **CSS** and **JavaScript** for the frontend
- Inspired by Signal and Telegram's disappearing message features

---

** Start chatting with messages that vanish - because some conversations are meant to be temporary!**
