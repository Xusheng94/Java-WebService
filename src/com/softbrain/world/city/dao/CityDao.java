package com.softbrain.world.city.dao;

import com.softbrain.world.city.utils.DBHelper_MySQL;

import java.sql.Statement;

/**
 * Created by Administrator on 2016/12/21.
 */
public class CityDao {
    /**
     * 插入数据
     */
    public void insert(String sql){
        try {
            Statement stmt = DBHelper_MySQL.getCon().createStatement();
            int i = stmt.executeUpdate("INSERT INTO `w_place` VALUES " + sql);
            if (i > 0) {
                System.out.println("插入成功");
            }
        } catch (Exception e) {
            System.out.print("MYSQL ERROR:" + e.getMessage());
        }
    }
}
