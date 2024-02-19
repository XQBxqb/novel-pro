package com.novel.user.controller;

import com.novel.core.router.RouterMapping;
import com.novel.core.thread.ThreadInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 昴星
 * @date 2023-10-06 14:43
 * @explain
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class HelloController {
    @GetMapping("/"+RouterMapping.USER_MNG_API+"/hello")
    public void hello(){
        String s = ThreadInfo.getUserSession().get();
        log.info("----hello-----");
    }
}
