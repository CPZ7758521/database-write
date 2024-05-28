package com.pandora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Write_mariadb_on_update {
    public static void main(String[] args) {
        //定义链接
        Connection conn = null;
        //定义预编译sql
        PreparedStatement ps = null;
        //定义结果集
        ResultSet rs = null;

        //sql语句
        String dropSql = "drop table test.a";
        String createSql = "create table if not exists test.a (\n" +
                "tage int,\n" +
                "name varchar(20),\n" +
                "tsex varchar(20),\n" +
                "`create_time` timestamp not null default current_timestamp(),\n" +
                "`update_time` timestamp not null default current_timestamp() on update current_timestamp(),\n" +
                "unique key (name)\n" +
                ") ENGIN=InnoDB CHARSET=utf-8";

        String querySql = "insert into test.a (age,name,sex) values (?,?,?) on duplicate key update age = ?, name = ?, sex = ?";


    }
}
