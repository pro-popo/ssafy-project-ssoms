import cx_Oracle
import random
import time
#pip install cx_Oracle
#python version 3.8

OID = "c##TESTDB"
OPASS = "testdb"
OID2 = "c##TESTDB2"
OID3 = "c##TESTDB3"
OID4 = "c##TESTDB4"
OID5 = "c##TESTDB5"
#OURL = "k3b303.p.ssafy.io:8000"
OURL = "localhost:1521"
#1521
#CLRExtProc

class Schema:
    def __init__(self, OID, OPASS, OURL, start):
        self.connection = cx_Oracle.connect(OID,OPASS,OURL)
        self.start = start
        cursor = self.connection.cursor()
        cursor.execute("select tname from tab")
        tables = []
        #cursor.fetchall()
        for ta in cursor:
            tables.append(ta[0])
        print(tables)
        if "TABLE"+str(start) not in tables:
            cur = self.connection.cursor()
            cur.execute("""CREATE TABLE table"""+str(start)+"""(
                idx number primary key,
                text1 varchar2(20) not null
            )
            """)
            cur.execute("""CREATE SEQUENCE TABLE"""+str(start)+"""_SEQ
                START WITH 1
                INCREMENT BY 1
            """)
            print("create table"+str(start))
            cur.close()
        if "TABLE"+str(start+1) not in tables:
            cur = self.connection.cursor()
            cur.execute("""CREATE TABLE table"""+str(start+1)+"""(
                idx number primary key,
                fx_idx number,
                text2 varchar2(20) not null,
                CONSTRAINT FK_IDX FOREIGN KEY(fx_idx)
                REFERENCES table"""+str(start)+"""(idx)
                ON DELETE CASCADE
            )
            """)
            cur.execute("""CREATE SEQUENCE TABLE"""+str(start+1)+"""_SEQ
                START WITH 1
                INCREMENT BY 1
            """)
            print("create table"+str(start+1))
            cur.close()

        if "TABLE"+str(start+2) not in tables:
            cur = self.connection.cursor()
            cur.execute("""CREATE TABLE table"""+str(start+2)+"""(
                idx number primary key,
                text3 varchar2(20) not null
            )
            """)
            cur.execute("""CREATE SEQUENCE TABLE"""+str(start+2)+"""_SEQ
                START WITH 1
                INCREMENT BY 1
            """)
            print("create table"+str(start+2))
            cur.close()
        cursor.close()

    def insert(self, table):
        cursor = self.connection.cursor()
        cursor.execute("select * from table"+table)
        curlist = cursor.fetchall()

        if curlist is not None or len(curlist) <= 1000:
            if table != str(self.start+1):
                cursor.execute("insert into table"+table+" values(TABLE" + table+"_SEQ.NEXTVAL, 'test')")
            else:
                cursor.execute("select idx from table"+str(self.start))
                idxs = cursor.fetchall()
                if idxs is not None and len(idxs) != 0:
                    fkidx = random.randrange(0, len(idxs))
                    cursor.execute("insert into table"+table+" values(TABLE" + table+"_SEQ.NEXTVAL, " + str(idxs[fkidx][0]) +", 'test')")
        cursor.close()
        self.connection.commit()

    def select(self, table):
        cursor = self.connection.cursor()
        if table == str(self.start+1):
            cursor.execute("select * from table"+table +", table"+str(self.start)+" where table"+str(self.start+1)+".fx_idx = table"+str(self.start)+".idx")
        else :
            cursor.execute("select * from table"+table)
        cursor.close()

    def delete(self, table):
        cursor = self.connection.cursor()
        cursor.execute("select * from table"+table)
        curlist = cursor.fetchall()
        if curlist is not None and len(curlist) != 0:
            ridx = random.randrange(0, len(curlist))
            cursor.execute("delete from table"+table+ " where idx = " + str(curlist[ridx][0]))
        cursor.close()
        self.connection.commit()

    def delete_all_tables(self):
        cursor = self.connection.cursor()
        cursor.execute("DROP SEQUENCE TABLE"+str(self.start+2)+"_SEQ")
        cursor.execute("DROP SEQUENCE TABLE"+str(self.start+1)+"_SEQ")
        cursor.execute("DROP SEQUENCE TABLE"+str(self.start)+"_SEQ")
        
        cursor.execute("drop table table"+str(self.start+2))
        cursor.execute("drop table table"+str(self.start+1))
        cursor.execute("drop table table"+str(self.start))
        cursor.close()
        self.connection.commit()
    def __del__(self):
        self.connection.close()

s1 = Schema(OID, OPASS, OURL, 1)
s2 = Schema(OID2, OPASS, OURL, 4)
s3 = Schema(OID3, OPASS, OURL, 7)
s4 = Schema(OID4, OPASS, OURL, 10)
s5 = Schema(OID5, OPASS, OURL, 13)

while True:
    num = random.randint(1, 3)
    num2 = random.randint(4, 6)
    num3 = random.randint(7, 9)
    num4 = random.randint(10, 12)
    num5 = random.randint(13, 15)
    order = random.randint(1, 100)
    print(str(num) + "    " + str(order))
    if order <= 25:
        s1.insert(str(num))
        s2.insert(str(num2))
        s3.insert(str(num3))
        time.sleep(0.5)
        s4.insert(str(num4))
        s5.insert(str(num5))
    elif order <= 75:
        s1.select(str(num))
        s2.select(str(num2))
        s3.select(str(num3))
        time.sleep(0.5)
        s4.select(str(num4))
        s5.select(str(num5))
    else :
        s1.delete(str(num))
        s2.delete(str(num2))
        s3.delete(str(num3))
        time.sleep(0.5)
        s4.delete(str(num4))
        s5.delete(str(num5))
    time.sleep(0.5)
# delete_all_tables()
