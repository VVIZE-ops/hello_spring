package com.zlw.hellowirld.repository;

import com.zlw.hellowirld.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository//用于标识UserRepositoryimpl 类是一个可注入的bean 。
public class UserRepositoryImpl implements UserRepository {

    //用来生成一个递增的id ，作为用户的唯一编号。
    private  static AtomicLong counterId = new AtomicLong();

    //模拟数据的存储，
    private final ConcurrentMap<Long , User> userConcurrentMap =new ConcurrentHashMap<Long,User>();

    @Override
    public User saveOrUpdateUser(User user) {
        Long id =user.getId();
        if (id==null){
            id=counterId.incrementAndGet();
            user.setId(id);
        }
        this.userConcurrentMap.put(id,user);
        return user;
    }

    @Override
    public void deleteUsere(Long id) {
        this.userConcurrentMap.remove(id);

    }

    @Override
    public User getUserById(Long id) {
        return this.userConcurrentMap.get(id);
    }

    @Override
    public List<User> userList() {
        return new ArrayList<User>(this.userConcurrentMap.values());
    }
}

