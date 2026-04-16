package jp.co.monocrea.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jp.co.monocrea.entity.User;

import java.util.List;

@ApplicationScoped
public class UserService {

    public List<User> getAll() {
        return User.listAll();
    }

    public User getById(Long id) {
        return User.findById(id);
    }

    @Transactional
    public User create(User user) {
        user.persist();
        return user;
    }

    @Transactional
    public void update(Long id, User data) {
        User user = User.findById(id);
        if (user != null) {
            user.name = data.name;
        }
    }

    @Transactional
    public void delete(Long id) {
        User.deleteById(id);
    }
}