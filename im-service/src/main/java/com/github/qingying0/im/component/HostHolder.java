
package com.github.qingying0.im.component;

import com.github.qingying0.im.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private ThreadLocal<Users> localUser = new ThreadLocal<>();

    public void setUser(Users user) {
        localUser.set(user);
    }

    public Users getUser() {
        return localUser.get();
    }

    public void delete() {
        localUser.remove();
    }
}
