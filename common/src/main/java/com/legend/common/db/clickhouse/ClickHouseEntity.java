package com.legend.common.db.clickhouse;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author legend xu
 * @date 2022/9/22
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface ClickHouseEntity {
    public String clusterName();
    public String tableName();
}
