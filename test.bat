@echo off
echo === Sending message (TTL: 60 seconds) ===
curl -s -X POST http://localhost:8080/api/messages -H "Content-Type: application/json" -d "{\"sender\":\"alice\",\"recipient\":\"bob\",\"content\":\"Hi Bob! This message will disappear in 60 seconds.\",\"ttlSeconds\":60}"
echo.
echo.
echo === Bob's inbox ===
curl -s http://localhost:8080/api/messages/inbox/bob
echo.
echo.
echo === Alice's sent messages ===
curl -s http://localhost:8080/api/messages/sent/alice
echo.
