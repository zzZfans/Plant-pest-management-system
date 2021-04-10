package com.zfans.ppms.controller.user;

import com.zfans.ppms.ret.R;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Zfans
 * @DateTime 2021/4/7 14:31
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class LoginController {
    @Data
    static class User {
        String username;
        String password;
    }

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("login")
    public R login(@RequestBody User user) {
        if (!"admin".equals(user.password)) {
            return R.error().message("密码错误！");
        }
        return R.ok().data("token", "admin");
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("info")
    public R info() {
        return R.ok()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://ppmsbk.oss-cn-chengdu.aliyuncs.com/avatar/avatar.JPG");
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("logout")
    public R logout() {
        return R.ok();
    }
}
