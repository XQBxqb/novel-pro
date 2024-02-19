package com.novel.resource.controller;

import com.novel.resource.centor.ResourceConfigCentor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 昴星
 * @date 2023-09-04 16:14
 * @explain
 */


@ResponseBody
@Controller
@Slf4j
public class HelloWorld {
    @Autowired
    private ResourceConfigCentor resourceConfigCentor;

    @RequestMapping(value = "hello")
    public void hello(){
        System.out.println(resourceConfigCentor);
        log.info("ttttt");
        return ;
    }
}
