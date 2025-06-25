package com.novo.report;

import com.mysql.jdbc.Connection;
import jakarta.activation.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
class IbootStudioAppTests {

//  @Autowired
//  private DataSource dataSource;
//
  @Test
  public void testDataSource() {
      try {
          // 根据你使用的驱动版本选择类名
          Class.forName("com.mysql.jdbc.Driver");  // 5.x
          // Class.forName("com.mysql.cj.jdbc.Driver");  // 8.x

          String url = "jdbc:mysql://172.20.1.34:8806/novo_report_pro";
          String user = "novo";
          String password = "GodIsLove";

          try (java.sql.Connection conn = java.sql.DriverManager.getConnection(url, user, password)) {
              System.out.println("直接连接成功！数据库版本: " + conn.getMetaData().getDatabaseProductVersion());
          }
      } catch (Exception e) {
          System.err.println("直接连接失败: " + e.getMessage());
          e.printStackTrace();
      }
  }
}
