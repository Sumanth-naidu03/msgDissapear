@echo off
cd /d c:\Users\admin\Downloads\msgDissapear\msgDissapear
echo Y | mvnw.cmd spring-boot:run > run_log.txt 2>&1
