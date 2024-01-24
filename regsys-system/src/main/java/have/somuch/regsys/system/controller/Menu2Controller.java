package have.somuch.regsys.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import have.somuch.regsys.common.common.BaseController;
import have.somuch.regsys.system.entity.Menu;
import have.somuch.regsys.system.entity.Menu2;
import have.somuch.regsys.system.mapper.Menu2Mapper;
import have.somuch.regsys.system.mapper.MenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/menu2")
public class Menu2Controller extends BaseController {

    @Autowired
    private Menu2Mapper menu2Mapper;
    @Autowired
    private MenuMapper menuMapper;

    @GetMapping("/index")
    public void index() {
        List<Menu2> menu2List = getChildrenMenuAll(0);
        menu2List.forEach(item -> {
            Menu menu = new Menu();
            BeanUtils.copyProperties(item, menu);
            menu.setPid(0);
            menu.setTarget("_self");
            menuMapper.insert(menu);
            if (!item.getChildren().isEmpty()) {
                item.getChildren().forEach(item2 -> {
                    Menu menu2 = new Menu();
                    BeanUtils.copyProperties(item2, menu2);
                    // 设置PID
                    menu2.setPid(menu.getId());
                    menu2.setTarget("_self");
                    menuMapper.insert(menu2);
                    if (!item2.getChildren().isEmpty()) {
                        item2.getChildren().forEach(item3 -> {
                            Menu menu3 = new Menu();
                            BeanUtils.copyProperties(item3, menu3);
                            // 设置PID
                            menu3.setPid(menu2.getId());
                            menu3.setTarget("_self");
                            menuMapper.insert(menu3);
                        });
                    }
                });
            }
        });
    }

    public List<Menu2> getChildrenMenuAll(Integer pid) {
        QueryWrapper<Menu2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", pid);
        queryWrapper.eq("status", 1);
        queryWrapper.eq("mark", 1);
        queryWrapper.orderByAsc("sort");
        List<Menu2> menuList = menu2Mapper.selectList(queryWrapper);
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                List<Menu2> childrenList = getChildrenMenuAll(item.getId());
                item.setChildren(childrenList);
            });
        }
        return menuList;
    }

}
