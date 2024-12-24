package top.torx.mybatis.extension;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.torx.core.domain.PageParam;
import top.torx.core.domain.RestResult;
import top.torx.core.domain.ValidWay;
import top.torx.mybatis.util.PageHelper;

import java.util.List;

/**
 * @author LiuYuHua
 * @date 2024/12/23 15:26
 */
public abstract class SuperController<T, S extends SuperService<T>> {

    @Autowired
    protected S baseService;

    @PostMapping("/create")
    public RestResult create(@RequestBody @Validated(ValidWay.Create.class) T entity) {
        return handleCreate(entity);
    }

    @PutMapping("/update")
    public RestResult update(@RequestBody @Validated(ValidWay.Update.class) T entity) {
        return handleUpdate(entity);
    }

    @DeleteMapping("/delete")
    public RestResult delete(@RequestBody List<Long> ids) {
        return handleDelete(ids);
    }

    @GetMapping("/get/{id}")
    public RestResult get(@PathVariable("id") Long id) {
        return handleGet(id);
    }

    @PostMapping("/page")
    public RestResult page(@RequestBody PageParam<T> pageParam) {
        return handlePage(pageParam);
    }

    protected RestResult handleCreate(T entity) {
        baseService.save(entity);
        return RestResult.success(entity);
    }

    protected RestResult handleUpdate(T entity) {
        baseService.updateById(entity);
        return RestResult.success(entity);
    }

    protected RestResult handleDelete(List<Long> ids) {
        baseService.removeByIds(ids);
        return RestResult.success();
    }

    protected RestResult handleGet(Long id) {
        return RestResult.success(baseService.getById(id));
    }

    protected RestResult handlePage(PageParam<T> pageParam) {
        Page<T> coursePage = PageHelper.buildPage(pageParam);
        return RestResult.success(baseService.page(coursePage));
    }

}
