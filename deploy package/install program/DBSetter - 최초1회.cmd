@echo off
setlocal
set /p dbpw=MariaDB ��й�ȣ�� �Է����ּ���:
set path=%path%;C:\Program Files\MariaDB 10.5\bin
mysql -u root -p%dbpw% < DBSetter.sql

echo "DB �ʱ⼳���� �Ϸ�Ǿ����ϴ�."
pause