package com.novel.file.feign;

import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 昴星
 * @date 2023-09-04 18:52
 * @explain
 */


@FeignClient(value = "pfile",path = RouterMapping.FILE_API,
        fallback = FileFeigin.class)
public interface FileFeignApi {

}
@Component
class FileFeigin implements FileFeignApi {

}
