@echo off
set path=%path%;C:\Program Files\MongoDB\Server\4.4\bin
mongo localhost:27017/admin mongoSetter.js
echo "DB �ʱ⼳���� �Ϸ�Ǿ����ϴ�."
pause