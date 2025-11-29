package bimatlaptrinh.com.batch.reader;

import bimatlaptrinh.com.batch.model.User;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class UserReader {
    @Bean
    public JdbcCursorItemReader<User> dbReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<User>()
                .name("dbUserReader")
                .dataSource(dataSource)
                .sql("SELECT id, name, email FROM USERS")
                .rowMapper((rs, rowNum) -> {
                    User u = new User();
                    u.setId(rs.getLong("id"));
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    return u;
                })
                .build();
    }
}
