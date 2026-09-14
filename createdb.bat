@echo off
set PGPASSWORD=Uma@1927
"C:\Program Files\PostgreSQL\18\bin\createdb.exe" -U postgres msgdissapear
echo Exit code: %ERRORLEVEL%
