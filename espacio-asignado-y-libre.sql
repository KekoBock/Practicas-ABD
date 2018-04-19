
-- Muestra espacio asignado y espacio libre para cada TableSpace
-- dentro de los "data files" de oracle (no de windows)

SELECT
   a.tablespace_name,
   a.file_name,
   a.bytes allocated_bytes,
   b.free_bytes
FROM
   dba_data_files a,
   (SELECT file_id, SUM(bytes) free_bytes
    FROM dba_free_space b GROUP BY file_id) b
WHERE
   a.file_id=b.file_id
ORDER BY
   a.tablespace_name;
