
--  free_space.sql --------- SOLO CON EL USUARIO "SYS" y modo "DBA"

--  Ejecuta esto antes del Formatea-espacio-libre.sql,
-- asi creas LA vista free_space que usas en Formatea-espacio-libre.sql


drOP VIEW SYS.FREE_SPACE;

CREATE VIEW SYS.FREE_SPACE AS
SELECT
       TABLESPACE_NAME TABLESPACE,
       FILE_ID,
       COUNT(*)    PIECES,
       SUM(BYTES)  FREE_BYTES,
       SUM(BLOCKS) FREE_BLOCKS,
       MAX(BYTES)  LARGEST_BYTES,
       MAX(BLOCKS) LARGEST_BLKS
FROM  
       SYS.DBA_FREE_SPACE
GROUP  BY TABLESPACE_NAME, FILE_ID;

-------- 
