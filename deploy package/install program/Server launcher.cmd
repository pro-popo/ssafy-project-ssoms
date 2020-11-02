@echo off
echo "서버를 개설합니다."
start chrome http://localhost:8080/ssoms
java -jar monitoring-0.0.1-SNAPSHOT.war --SetupPath=C:\\Program Files (x86)\\SSOMS
pause