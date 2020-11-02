import cx_Oracle
import random
import time
#pip install cx_Oracle
#python version 3.8

OID = "c##TESTDB"
OPASS = "testdb"
OID2 = "c##TESTDB2"
OURL = "k3b303.p.ssafy.io:8000"
#1521
#CLRExtProc
connection = cx_Oracle.connect(OID,OPASS,OURL)
connection2 = cx_Oracle.connect(OID2,OPASS,OURL)

def init():
    cursor = connection.cursor()
    cursor.execute("select tname from tab")
    tables = []
    #cursor.fetchall()
    for ta in cursor:
        tables.append(ta[0])
    print(tables)
    if "TABLE1" not in tables:
        cur = connection.cursor()
        cur.execute("""CREATE TABLE table1(
            idx number primary key,
            text1 varchar2(20) not null
        )
        """)
        cur.execute("""CREATE SEQUENCE TABLE1_SEQ
            START WITH 1
            INCREMENT BY 1
        """)
        print("create table1")
        cur.close()

    if "TABLE2" not in tables:
        cur = connection.cursor()
        cur.execute("""CREATE TABLE table2(
            idx number primary key,
            fx_idx number,
            text2 varchar2(20) not null,
            CONSTRAINT FK_IDX FOREIGN KEY(fx_idx)
            REFERENCES table1(idx)
            ON DELETE CASCADE
        )
        """)
        cur.execute("""CREATE SEQUENCE TABLE2_SEQ
            START WITH 1
            INCREMENT BY 1
        """)
        print("create table2")
        cur.close()

    if "TABLE3" not in tables:
        cur = connection.cursor()
        cur.execute("""CREATE TABLE table3(
            idx number primary key,
            text3 varchar2(20) not null
        )
        """)
        cur.execute("""CREATE SEQUENCE TABLE3_SEQ
            START WITH 1
            INCREMENT BY 1
        """)
        print("create table3")
        cur.close()
    cursor.close()

def init2():
    cursor = connection2.cursor()
    cursor.execute("select tname from tab")
    tables = []
    #cursor.fetchall()
    for ta in cursor:
        tables.append(ta[0])
    print(tables)
    if "TABLE7" not in tables:
        cur = connection2.cursor()
        cur.execute("""CREATE TABLE table7(
            idx number primary key,
            text1 varchar2(20) not null
        )
        """)
        cur.execute("""CREATE SEQUENCE TABLE7_SEQ
            START WITH 1
            INCREMENT BY 1
        """)
        print("create table7")
        cur.close()
    if "TABLE8" not in tables:
        cur = connection2.cursor()
        cur.execute("""CREATE TABLE table8(
            idx number primary key,
            text2 varchar2(20) not null
        )
        """)
        cur.execute("""CREATE SEQUENCE TABLE8_SEQ
            START WITH 1
            INCREMENT BY 1
        """)
        print("create table8")
        cur.close()
    if "TABLE9" not in tables:
        cur = connection2.cursor()
        cur.execute("""CREATE TABLE table9(
            idx number primary key,
            text3 varchar2(20) not null
        )
        """)
        cur.execute("""CREATE SEQUENCE TABLE9_SEQ
            START WITH 1
            INCREMENT BY 1
        """)
        print("create table9")
        cur.close()
    cursor.close()

def insert(table):
    cursor = connection.cursor()
    cursor.execute("select * from table"+table)
    curlist = cursor.fetchall()

    if curlist is not None or len(curlist) <= 1000:
        if table != "2":
            cursor.execute("insert into table"+table+" values(TABLE" + table+"_SEQ.NEXTVAL, 'test')")
        else:
            cursor.execute("select idx from table1")
            idxs = cursor.fetchall()
            if idxs is not None and len(idxs) != 0:
                fkidx = random.randrange(0, len(idxs))
                cursor.execute("insert into table"+table+" values(TABLE" + table+"_SEQ.NEXTVAL, " + str(idxs[fkidx][0]) +", 'test')")
    cursor.close()
    connection.commit()

def insert2(table):
    cursor = connection2.cursor()
    cursor.execute("select * from table"+table)
    curlist = cursor.fetchall()

    if curlist is not None or len(curlist) <= 1000:
       cursor.execute("insert into table"+table+" values(TABLE" + table+"_SEQ.NEXTVAL, 'test')")
    cursor.close()
    connection2.commit()

def select(table):
    cursor = connection.cursor()
    if table =="2":
        cursor.execute("select * from table"+table +", table1 where table2.fx_idx = table1.idx")
    else :
        cursor.execute("select * from table"+table)
    cursor.close()

def select2(table):
    cursor = connection2.cursor()
    cursor.execute("select * from table"+table)
    cursor.close()

def delete(table):
    cursor = connection.cursor()
    cursor.execute("select * from table"+table)
    curlist = cursor.fetchall()
    if curlist is not None and len(curlist) != 0:
        ridx = random.randrange(0, len(curlist))
        cursor.execute("delete from table"+table+ " where idx = " + str(curlist[ridx][0]))
    cursor.close()
    connection.commit()

def delete2(table):
    cursor = connection2.cursor()
    cursor.execute("select * from table"+table)
    curlist = cursor.fetchall()
    if curlist is not None and len(curlist) != 0:
        ridx = random.randrange(0, len(curlist))
        cursor.execute("delete from table"+table+ " where idx = " + str(curlist[ridx][0]))
    cursor.close()
    connection2.commit()

def delete_all_tables():
    cursor = connection.cursor()
    cursor.execute("DROP SEQUENCE TABLE3_SEQ")
    cursor.execute("DROP SEQUENCE TABLE2_SEQ")
    cursor.execute("DROP SEQUENCE TABLE1_SEQ")
    
    cursor.execute("drop table table3")
    cursor.execute("drop table table2")
    cursor.execute("drop table table1")

    cursor2 = connection2.cursor()
    cursor2.execute("DROP SEQUENCE TABLE7_SEQ")
    cursor2.execute("drop table table7")
    cursor2.execute("DROP SEQUENCE TABLE8_SEQ")
    cursor2.execute("drop table table8")
    cursor2.execute("DROP SEQUENCE TABLE9_SEQ")
    cursor2.execute("drop table table9")
    cursor.close()
    connection.commit()
    cursor2.close()
    connection2.commit()

init()
init2()

while True:
    num = random.randint(1, 3)
    num2 = random.randint(7, 9)
    order = random.randint(1, 100)
    print(str(num) + "    " + str(order))
    if order <= 25:
        insert(str(num))
        insert2(str(num2))
    elif order <= 75:
        select(str(num))
        select2(str(num2))
    else :
        delete(str(num))
        delete2(str(num2))
    time.sleep(1)
# delete_all_tables()
connection.close()
connection2.close()