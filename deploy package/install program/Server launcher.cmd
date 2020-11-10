@echo off
echo "������ �����մϴ�."
start chrome http://172.30.1.49:8080/ssoms
java -jar monitoring-0.0.1-SNAPSHOT.war --SetupPath=C:\\Program Files (x86)\\SSOMS
pause