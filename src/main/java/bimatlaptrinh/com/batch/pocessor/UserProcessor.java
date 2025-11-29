package bimatlaptrinh.com.batch.pocessor;

import bimatlaptrinh.com.batch.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserProcessor implements ItemProcessor<User, User> {
    @Override
    public User process(User user) throws Exception {
        if (!user.getEmail().contains("@")) {
            return null; // skip user lỗi
        }
        user.setName(user.getName().toUpperCase());
        return user;
    }
}
