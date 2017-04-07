package com.sinoiov.lhjh.impl;

import com.sinoiov.lhjh.beans.Employee;
import com.sinoiov.lhjh.config.MyDataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;


/**
 * 雇员信息处理类
 * Created by lidawei on 2017/4/7.
 */
public class EmployProcessor {
    DataSource dataSource = MyDataSourceFactory.getMySQLDataSource();

    /**
     * 这是一个传统的处理MySQL连接查询数据的做法，里面和业务相关的代码很少，它有大量的代码是冗余的，尤其是在许多地方要多次
     * 连接数据库的时候，我们会发现相似的代码何其之多。。。
     * @param id 指定雇员id
     * @return 获得雇员信息
     */
    public Employee getEmployeeById(long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT id, firstName, lastName, salary FROM employee WHERE id=?");
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            Employee employee = null;
            if (rs.next()) {
                employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setSalary(rs.getBigDecimal("salary"));
            }
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 在这里我们使用Spring提供的JdbcTemplate（它利用了JDK5的JdbcTemplate实现），使得执行数据库操作时避免传统的JDBC样板
     * 代码成为了可能。写这个方法的程序员，只需要关注查询雇员信息的业务逻辑就好了，不需要去迎合JDBC驱动的API上的各种约束。
     * @param id 指定雇员id
     * @return 获得雇员信息
     */
    public Employee getEmployeeById2(long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject("SELECT id, firstName, lastName, salary FROM employee WHERE id=?", new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                Employee employee = new Employee();
                employee.setId(rs.getLong("id"));
                employee.setFirstName(rs.getString("firstName"));
                employee.setLastName(rs.getString("lastName"));
                employee.setSalary(rs.getBigDecimal("salary"));
                return employee;
            }
        }, id);
    }
}
