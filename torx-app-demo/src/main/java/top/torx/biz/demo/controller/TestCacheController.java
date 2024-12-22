package top.torx.biz.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LiuYuHua
 * @Date: 2024/12/22 15:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/cache")
public class TestCacheController {

    @Cacheable(value = "ww", key = "'test'")
    @GetMapping("/cacheable")
    public String cacheable() {
        System.out.println("cacheable");
        return null;
    }

    @CacheEvict(value = "ww", key = "'test'")
    @GetMapping("/cacheEvict")
    public String cacheEvict() {
        System.out.println("cacheEvict");
        return "aaa";
    }
}
