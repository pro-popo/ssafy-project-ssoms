@echo off
set path=%path%;C:\Program Files\MongoDB\Server\4.4\bin
mongo localhost:27017/admin mongoSetter.js
echo "DB 초기설정이 완료되었습니다."
pause