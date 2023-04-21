SELECT 'google_hotelinfo'                                                  AS table_name
     , 'address'                                                           AS field_name
     , '2023-04-12'                                                        AS data_date
     , t1.total                                                            AS total_count
     , t2.change                                                           AS change_count
     , if(nvl(CAST((t1.total - t2.change) / t1.total * 100 AS decimal(10, 2)) + 0, NULL) IS NULL, 0,
          CAST((t1.total - t2.change) / t1.total * 100 AS decimal(10, 2))) AS mom
FROM (
         SELECT count(a.hotelId) AS total
         FROM ods_htl_htlvendorpricemdb.google_hotelinfo a
                  INNER JOIN (
             SELECT hotelId
             FROM ods_htl_htlvendorpricemdb.google_hotelinfo
             WHERE d = '2023-04-12'
         ) b
                             ON a.hotelId = b.hotelId
         WHERE a.d = '2023-04-13'
           AND datachange_lasttime > '2023-04-12'
           AND datachange_lasttime < '2023-04-13'
     ) t1,
     (
         SELECT count(a.address) AS change
         FROM ods_htl_htlvendorpricemdb.google_hotelinfo a
                  INNER JOIN (
             SELECT hotelId, address
             FROM ods_htl_htlvendorpricemdb.google_hotelinfo
             WHERE d = '2023-04-12'
         ) b
                             ON a.hotelId = b.hotelId
                                 AND a.address = b.address
         WHERE a.d = '2023-04-13'
           AND datachange_lasttime > '2023-04-12'
           AND datachange_lasttime < '2023-04-13'
     ) t2
UNION ALL
SELECT 'google_hotelinfo'                                                  AS table_name
     , 'longitude'                                                         AS field_name
     , '2023-04-12'                                                        AS data_date
     , t1.total                                                            AS total_count
     , t2.change                                                           AS change_count
     , if(nvl(CAST((t1.total - t2.change) / t1.total * 100 AS decimal(10, 2)) + 0, NULL) IS NULL, 0,
          CAST((t1.total - t2.change) / t1.total * 100 AS decimal(10, 2))) AS mom
FROM (
         SELECT count(a.hotelId) AS total
         FROM ods_htl_htlvendorpricemdb.google_hotelinfo a
                  INNER JOIN (
             SELECT hotelId
             FROM ods_htl_htlvendorpricemdb.google_hotelinfo
             WHERE d = '2023-04-12'
         ) b
                             ON a.hotelId = b.hotelId
         WHERE a.d = '2023-04-13'
           AND datachange_lasttime > '2023-04-12'
           AND datachange_lasttime < '2023-04-13'
     ) t1,
     (
         SELECT count(a.longitude) AS change
         FROM ods_htl_htlvendorpricemdb.google_hotelinfo a
                  INNER JOIN (
             SELECT hotelId, longitude
             FROM ods_htl_htlvendorpricemdb.google_hotelinfo
             WHERE d = '2023-04-12'
         ) b
                             ON a.hotelId = b.hotelId
                                 AND a.longitude = b.longitude
         WHERE a.d = '2023-04-13'
           AND datachange_lasttime > '2023-04-12'
           AND datachange_lasttime < '2023-04-13'
     ) t2
UNION ALL
SELECT 'google_hotelinfo'                                                  AS table_name
     , 'latitude'                                                          AS field_name
     , '2023-04-12'                                                        AS data_date
     , t1.total                                                            AS total_count
     , t2.change                                                           AS change_count
     , if(nvl(CAST((t1.total - t2.change) / t1.total * 100 AS decimal(10, 2)) + 0, NULL) IS NULL, 0,
          CAST((t1.total - t2.change) / t1.total * 100 AS decimal(10, 2))) AS mom
FROM (
         SELECT count(a.hotelId) AS total
         FROM ods_htl_htlvendorpricemdb.google_hotelinfo a
                  INNER JOIN (
             SELECT hotelId
             FROM ods_htl_htlvendorpricemdb.google_hotelinfo
             WHERE d = '2023-04-12'
         ) b
                             ON a.hotelId = b.hotelId
         WHERE a.d = '2023-04-13'
           AND datachange_lasttime > '2023-04-12'
           AND datachange_lasttime < '2023-04-13'
     ) t1,
     (
         SELECT count(a.latitude) AS change
         FROM ods_htl_htlvendorpricemdb.google_hotelinfo a
                  INNER JOIN (
             SELECT hotelId, latitude
             FROM ods_htl_htlvendorpricemdb.google_hotelinfo
             WHERE d = '2023-04-12'
         ) b
                             ON a.hotelId = b.hotelId
                                 AND a.latitude = b.latitude
         WHERE a.d = '2023-04-13'
           AND datachange_lasttime > '2023-04-12'
           AND datachange_lasttime < '2023-04-13'
     ) t2
