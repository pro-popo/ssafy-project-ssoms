@echo off
setlocal
set /p dbpw=MariaDB 비밀번호를 입력해주세요:
set path=%path%;C:\Program Files\MariaDB 10.5\bin
mysql -u root -p%dbpw% < DBSetter.sql

echo "DB 초기설정이 완료되었습니다."
pause