
--------- SOLO CON EL USUARIO "SYS" y modo "DBA"

---------- em tsfree.sql - Shows all free space within tablespaces.

Prompt DEBES haber ejecutado antes free_space.sql

clear breaks;
clear computes;
set verify off;
set pagesize 66;
set linesize 79;
set newpage 0;

column temp_col new_value spool_file noprint;
column today new_value datevar noprint;
column TABLESPACE_NAME        FORMAT A15    HEADING 'Tablespace';
COLUMN PIECES                 FORMAT 9,999  HEADING 'Tablespace|Pieces';
COLUMN FILE_MBYTES            FORMAT 99,999 HEADING 'Tablespace|Mbytes';
cOLUMN FREE_MBYTES            FORMAT 99,999 HEADING 'Free|Mbytes';
COLUMN CONTIGUOUS_FREE_MBYTES FORMAT 99,999 HEADING 'Contiguous|Free|Mbytes';
COLUMN PCT_FREE               FORMAT 999    HEADING 'Percent|FREE';
COLUMN PCT_CONTIGUOUS_FREE    FORMAT 999    HEADING 'Percent|FREE|Contiguous';

ttitle left datevar right sql.pno -
       center ' Instance Data File Storage' SKIP 1 -
       center ' in ORACLE Megabytes (1048576 bytes)' -
       skip skip;

BREAK ON REPORT
COMPUTE SUM OF FILE_MBYTES ON REPORT

select to_char(sysdate,'mm/dd/yy') today,
       TABLESPACE_NAME,
       PIECES,
       (D.BYTES/1048576) FILE_MBYTES,
       (F.FREE_BYTES/1048576) FREE_MBYTES,
       ((F.FREE_BLOCKS / D.BLOCKS) * 100) PCT_FREE,
       (F.LARGEST_BYTES/1048576) CONTIGUOUS_FREE_MBYTES,
       ((F.LARGEST_BLKS / D.BLOCKS) * 100) PCT_CONTIGUOUS_FREE
from SYS.DBA_DATA_FILES D, SYS.FREE_SPACE F
See code depot for full script
where D.STATUS = 'AVAILABLE' AND
      D.FILE_ID= F.FILE_ID AND
      D.TABLESPACE_NAME = F.TABLESPACE
order by TABLESPACE_NAME;


/*

Here is the report from this script.

Tablespace         Pieces     Mbytes  Mbytes  FREE       Mbytes Contiguous
--------------- ---------- ---------- ------- ------- ---------- ----------
MASTER1_DETAILS       1      18         2        10            2   10
MASTER1_DETAILS       1      20        20       100           20  100
MASTER2_DETAILS       1       2         1        65            1   65
MASTER3_DETAILS       1       5         5        95            5   95
MASTER4_DETAILS       2       3         1        36            1   35
RBS_ONE              11     490       380        78          280   57
RBS_TWO              11     490       379        77          279   57
SYSTEM               17      60        45        76           45   75
TEMP                  1     650       650       100          650  100
TOOLS                 2      15         9        61            8   55
USERS                41     100        31        31            4    4
----------
                      13,255

This report is useful for finding the largest sized chunk of free
 space within a tablespace.

*/
