# SSAFY_FINAL_PJT

document : https://www.notion.so/PJT-135ade814b2e411dbe4ecfd603322ebd

10.15 JIRA connect test

OracleTest.py 실행법

bash에서

pip install cx_Oracle

python OracleTest.py

끝

만약 안되면

파이썬 버전확인 3.8

anaconda로 환경 구축 후 돌리는것을 추천



---
DB MONITORING
## PC Monitoring(실시간)

* ### PC resource

  * 전체 CPU 점유율
  * 전체 Memory 점유율
  * 스키마 CPU 점유율
  * 스키마 Memory 점유율

* ### Traffic Monitoring

  * 트래픽 상위 테이블
  * 트래픽 상위 사용자

* ### Query Monitoring

  * Query 조회
  * Top Query 조회

![image-20201022181145613](C:\Users\user\AppData\Roaming\Typora\typora-user-images\image-20201022181145613.png)



## 사용 Query

##### 1. 실행한 명령 쿼리만 나옴

```sql
SELECT
MODULE AS PROGRAM_NM
,PARSING_SCHEMA_NAME AS USER_ID
--,FIRST_LOAD_TIME AS LOAD_TIME
,SQL_FULLTEXT AS SQLTEXT
FROM V$SQL
WHERE MODULE like '%%' AND PARSING_SCHEMA_NAME IN('C##YB')
ORDER BY FIRST_LOAD_TIME ASC;
```

---

##### 2. 스키마별 통계

```sql
select parsing_schema_name
     , count(*) sql_cnt
     , count(distinct substr(sql_text, 1, 100)) sql_cnt2
     , sum(executions) executions
     , round(avg(buffer_gets/executions)) buffer_gets
     , round(avg(disk_reads/executions)) disk_reads
     , round(avg(rows_processed/executions)) rows_processed
     , round(avg(cpu_time/executions/1000000),2) "CPU_TIME(AVG)"
     , round(avg(elapsed_time/executions/1000000),2) "ELAPSED_TIME(AVG)"
     , count(case when elapsed_time/executions/1000000 >= 10 then 1 end) "BAD SQL"
     , round(max(elapsed_time/executions/1000000),2) "ELAPSED_TIME(MAX)"
     , round(max(cpu_time/executions/1000000),2) "CPU_TIME(MAX)"
     , round(sum(cpu_time/tot_cpu*100),2)
     , round(sum(elapsed_time/tot_elapsed*100),2)
from   v$sql, 
    (SELECT sum(elapsed_time) tot_elapsed,
            sum(cpu_time) tot_cpu
    FROM v$sql) t
where  last_active_time >= sysdate - 7
and    executions > 0
group by parsing_schema_name;
```

* 스키마 이름
* 카운트
* query
* 실행횟수
* 평균 논리적 블록 읽기
* 평균 disk 읽기
* 평균 cpu시간
* 평균 실행시간
* bad sql 발생
* 최대 실행시간
* 최대 cpu 시간
* 해당스키마 / 다른스키마 총합 실행시간
* 해당스키마 / 다른스키마 총합 cpu시간



---

##### 3. CPU 사용량 기준 조회

```sql
set pages 400 lines 400
col graph format a30
col SQL_FULL_TEXT for a40
col username for a15
col module for a20
col exec for 999999999
set long 40
SELECT /* Finding SQL sort by CPU time usage */
       main.sql_id, main.parsing_schema_name username, module, to_char(LAST_ACTIVE_TIME,'yyyy-mm-dd hh24:mi:ss') LAST_ACTIVE_TIME, main.executions exec,main.cpu_time/1000 "CPU_TIME(Sec)", main.cpu_time_ratio
		, '['||rpad(nvl(lpad('*', main.cpu_time_ratio/5, '*'), '-'), 20, '-')||']' "Graph"
		, (SELECT -- listagg( sql_text , ' ' ) WITHIN GROUP ( ORDER BY piece)
                 rtrim(xmlagg(xmlelement(e, sql_text ,' ').extract('//text()') order by piece).GetClobVal(),' ')
          FROM  v$sqltext b WHERE b.sql_id = main.sql_id GROUP BY sql_id ) as sql_full_text
FROM ( SELECT sql_id ,             
              plan_hash_value ,
              parsing_schema_name ,
              executions,
              buffer_gets,
              disk_reads ,
              direct_writes,
              cpu_time ,
              elapsed_time ,
			  LAST_ACTIVE_TIME,
			  module,
              round(cpu_time/sum(cpu_time) OVER()*100,1) cpu_time_ratio ,
              round(elapsed_time/sum(elapsed_time) OVER () *100 ,1) elapsed_time_ratio
       FROM   v$sqlarea a
       WHERE  executions > 1 and
	   parsing_schema_name not in ('SYSAUX','SYSMAN','XDB','HR','ODM','OUTLN','OE','SH','PM','IX','WK_TEST','PERFSTAT','DBSNMP','OLAPSYS','QS_CS','QS_CB','QS_CBADM','QS_OS','QS_WS','QS','QS_ADM','ODM_MTR' ,'WKPROXY','QS_ES','ANONYMOUS','WKSYS','WMSYS','APEX_030200','APEX_PUBLIC_USER','APPQOSSYS','BI','CTXSYS','DIP','EXFSYS','FLOWS_FILES','MDDATA','MDSYS','MGMT_VIEW','ORACLE_OCM','ORDDATA','ORDPLUGINS','ORDSYS','OWBSYS','OWBSYS_AUDIT','SCOTT','SI_INFORMTN_SCHEMA','SPATIAL_CSW_ADMIN_USR','SPATIAL_WFS_ADMIN_USR','XS$NULL')
	   and
	   NOT (module LIKE 'Golden%'
                    OR module LIKE 'TOAD%'
                    OR module LIKE 'toad%'
                    OR module LIKE 'Orange%'
                    OR module LIKE 'exp%'
                    OR module LIKE 'SQL*Plus%'
                    OR module LIKE 'SQL Developer%'
                    OR module LIKE 'oracle@%'
                    OR module LIKE 'sqlplus@%'
                    )
            AND NOT (TRIM(SQL_TEXT) LIKE '%:Q%'
			        OR TRIM(SQL_TEXT) LIKE 'declare%'
                    OR TRIM(SQL_TEXT) LIKE 'DECLARE%'
                    OR TRIM(SQL_TEXT) LIKE 'truncate%'
                    OR TRIM(SQL_TEXT) LIKE 'TRUNCATE%'
                    OR TRIM(SQL_TEXT) LIKE '%dba\_%' escape '\'
                    OR TRIM(SQL_TEXT) LIKE '%DBA\_%' escape '\'
                    OR TRIM(SQL_TEXT) LIKE 'SQLGateMain%'
                    OR TRIM(SQL_TEXT) LIKE 'ALTER SESSION%'
                    OR TRIM(SQL_TEXT) LIKE '%v$%'
                    OR TRIM(SQL_TEXT) LIKE '%x$%')
       ORDER BY cpu_time/sum(cpu_time) OVER()*100 desc ) main
WHERE rownum <= 20 ;
```

---

##### 4. 처리량 기준 조회

```sql
set pages 400 lines 400
col graph format a30
col SQL_FULL_TEXT for a40
col username for a15
col module for a20
col exec for 999999999
set long 40
SELECT /* Finding SQL sort by Logical Reads usage */
       main.sql_id, main.parsing_schema_name username, module, to_char(LAST_ACTIVE_TIME,'yyyy-mm-dd hh24:mi:ss') LAST_ACTIVE_TIME,main.executions exec, main.buffer_gets, main.buffer_gets_ratio
		, '['||rpad(nvl(lpad('*', main.buffer_gets_ratio/5, '*'), '-'), 20, '-')||']' "Graph"
       , (SELECT -- listagg( sql_text , ' ' ) WITHIN GROUP ( ORDER BY piece)
                 rtrim(xmlagg(xmlelement(e, sql_text ,' ').extract('//text()') order by piece).GetClobVal(),' ')
          FROM  v$sqltext b WHERE b.sql_id = main.sql_id GROUP BY sql_id ) as sql_full_text
FROM ( SELECT a.sql_id ,             
              plan_hash_value ,
              parsing_schema_name ,
              executions,
              buffer_gets,
              disk_reads ,
              direct_writes,
              cpu_time ,
              elapsed_time ,
			  LAST_ACTIVE_TIME,
			  module,
			  round(buffer_gets/sum(buffer_gets) OVER()*100,1) buffer_gets_ratio,
              round(cpu_time/sum(cpu_time) OVER()*100,1) cpu_time_ratio ,
              round(elapsed_time/sum(elapsed_time) OVER () *100 ,1) elapsed_time_ratio
       FROM   v$sqlarea a
       WHERE  executions > 1 and
	   parsing_schema_name not in ('SYSAUX','SYSMAN','XDB','HR','ODM','OUTLN','OE','SH','PM','IX','WK_TEST','PERFSTAT','DBSNMP','OLAPSYS','QS_CS','QS_CB','QS_CBADM','QS_OS','QS_WS','QS','QS_ADM','ODM_MTR' ,'WKPROXY','QS_ES','ANONYMOUS','WKSYS','WMSYS','APEX_030200','APEX_PUBLIC_USER','APPQOSSYS','BI','CTXSYS','DIP','EXFSYS','FLOWS_FILES','MDDATA','MDSYS','MGMT_VIEW','ORACLE_OCM','ORDDATA','ORDPLUGINS','ORDSYS','OWBSYS','OWBSYS_AUDIT','SCOTT','SI_INFORMTN_SCHEMA','SPATIAL_CSW_ADMIN_USR','SPATIAL_WFS_ADMIN_USR','XS$NULL')
	   and
	   NOT (module LIKE 'Golden%'
                    OR module LIKE 'TOAD%'
                    OR module LIKE 'toad%'
                    OR module LIKE 'Orange%'
                    OR module LIKE 'exp%'
                    OR module LIKE 'SQL*Plus%'
                    OR module LIKE 'SQL Developer%'
                    OR module LIKE 'oracle@%'
                    OR module LIKE 'sqlplus@%'
                    )
            AND NOT (TRIM(SQL_TEXT) LIKE '%:Q%'
			        OR TRIM(SQL_TEXT) LIKE 'declare%'
                    OR TRIM(SQL_TEXT) LIKE 'DECLARE%'
                    OR TRIM(SQL_TEXT) LIKE 'truncate%'
                    OR TRIM(SQL_TEXT) LIKE 'TRUNCATE%'
                    OR TRIM(SQL_TEXT) LIKE '%dba\_%' escape '\'
                    OR TRIM(SQL_TEXT) LIKE '%DBA\_%' escape '\'
                    OR TRIM(SQL_TEXT) LIKE 'SQLGateMain%'
                    OR TRIM(SQL_TEXT) LIKE 'ALTER SESSION%'
                    OR TRIM(SQL_TEXT) LIKE '%v$%'
                    OR TRIM(SQL_TEXT) LIKE '%x$%')
       ORDER BY buffer_gets_ratio desc ) main
WHERE rownum <= 20 ;
```

---

##### 5. 실행 시간 기준 조회

```sql
set pages 400 lines 400
col graph format a30
col SQL_FULL_TEXT for a40
col username for a15
col module for a20
col exec for 999999999
set long 40
SELECT /* Finding SQL sort by elapsed time */
       main.sql_id, main.parsing_schema_name username, module, to_char(LAST_ACTIVE_TIME,'yyyy-mm-dd hh24:mi:ss') LAST_ACTIVE_TIME, main.executions exec, main.elapsed_time/1000 "ELAPSED_TIME(Sec)", main.elapsed_time_ratio
		, '['||rpad(nvl(lpad('*', main.elapsed_time_ratio/5, '*'), '-'), 20, '-')||']' "Graph"
       , (SELECT -- listagg( sql_text , ' ' ) WITHIN GROUP ( ORDER BY piece)
                 rtrim(xmlagg(xmlelement(e, sql_text ,' ').extract('//text()') order by piece).GetClobVal(),' ')
          FROM  v$sqltext b WHERE b.sql_id = main.sql_id GROUP BY sql_id ) as sql_full_text
FROM ( SELECT sql_id ,             
              plan_hash_value ,
              parsing_schema_name ,
              executions,
              buffer_gets,
              disk_reads ,
              direct_writes,
              cpu_time ,
              elapsed_time ,
			  LAST_ACTIVE_TIME,
			  module,
              round(cpu_time/sum(cpu_time) OVER()*100,1) cpu_time_ratio ,
              round(elapsed_time/sum(elapsed_time) OVER () *100 ,1) elapsed_time_ratio
       FROM   v$sqlarea a
       WHERE  executions > 1 and
	   parsing_schema_name not in ('SYSAUX','SYSMAN','XDB','HR','ODM','OUTLN','OE','SH','PM','IX','WK_TEST','PERFSTAT','DBSNMP','OLAPSYS','QS_CS','QS_CB','QS_CBADM','QS_OS','QS_WS','QS','QS_ADM','ODM_MTR' ,'WKPROXY','QS_ES','ANONYMOUS','WKSYS','WMSYS','APEX_030200','APEX_PUBLIC_USER','APPQOSSYS','BI','CTXSYS','DIP','EXFSYS','FLOWS_FILES','MDDATA','MDSYS','MGMT_VIEW','ORACLE_OCM','ORDDATA','ORDPLUGINS','ORDSYS','OWBSYS','OWBSYS_AUDIT','SCOTT','SI_INFORMTN_SCHEMA','SPATIAL_CSW_ADMIN_USR','SPATIAL_WFS_ADMIN_USR','XS$NULL')
	   and
	   NOT (module LIKE 'Golden%'
                    OR module LIKE 'TOAD%'
                    OR module LIKE 'toad%'
                    OR module LIKE 'Orange%'
                    OR module LIKE 'exp%'
                    OR module LIKE 'SQL*Plus%'
                    OR module LIKE 'SQL Developer%'
                    OR module LIKE 'oracle@%'
                    OR module LIKE 'sqlplus@%'
                    )
            AND NOT (TRIM(SQL_TEXT) LIKE '%:Q%'
			        OR TRIM(SQL_TEXT) LIKE 'declare%'
                    OR TRIM(SQL_TEXT) LIKE 'DECLARE%'
                    OR TRIM(SQL_TEXT) LIKE 'truncate%'
                    OR TRIM(SQL_TEXT) LIKE 'TRUNCATE%'
                    OR TRIM(SQL_TEXT) LIKE '%dba\_%' escape '\'
                    OR TRIM(SQL_TEXT) LIKE '%DBA\_%' escape '\'
                    OR TRIM(SQL_TEXT) LIKE 'SQLGateMain%'
                    OR TRIM(SQL_TEXT) LIKE 'ALTER SESSION%'
                    OR TRIM(SQL_TEXT) LIKE '%v$%'
                    OR TRIM(SQL_TEXT) LIKE '%x$%')
       ORDER BY elapsed_time_ratio desc ) main
WHERE rownum <= 20 ;                 
```

출처 : https://unioneinc.tistory.com/63

---

참고하면 좋은 곳 : http://dbcafe.co.kr/wiki/index.php/ORACLE_%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81

##### 6. 총 cpu time (안되는거같음)

```sql
SELECT NAME, VALUE
FROM V$SYSSTAT
WHERE NAME IN ('CPU used by this session', 'parse time cpu');
```

---

##### parsing 부분이랑 cpu 부분은 링크 타고 들어가서 조금 더 찾아봐야할듯.





---

##### 8. 특정 기간 동안 CPU 점유율 Top 50 Query 찾기

http://apollo89.com/wordpress/?p=543

