package cn.andylhl.crowd.web.controller;

import cn.andylhl.crowd.entity.Menu;
import cn.andylhl.crowd.service.MenuService;
import cn.andylhl.crowd.utils.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Title: MenuController
 * @Description: 菜单控制器
 * @author: lhl
 * @date: 2020/12/30 9:47
 */

@Controller
public class MenuController {

    private Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Resource
    private MenuService menuService;

    /**
     * 返回根节点，获取整个树结构。（建立父子关系）
     * @return
     */
    @RequestMapping("/menu/get/whole/tree.json")
    public @ResponseBody Object getWholeTree(){

        // 1. 查询全部的Menu对象
        List<Menu> menuList = menuService.getAll();

        // 2. 声明根节点
        Menu root = null;

        // 3. 存储id和menu对象的对应关系，便于直接查询父节点的对象引用地址
        Map<Integer, Menu> menuMap = new HashMap<>();
        for (Menu menu : menuList) {
            menuMap.put(menu.getId(), menu);
        }

        // 4. 查找根节点，组装父子结点（子节点添加到父节点的children集合）
        for (Menu menu : menuList) {

            // 获取当前结点的pid
            Integer pid = menu.getPid();

            if (pid == null) {
                // 如果当前结点没有父节点，说明该节点是根节点
                root = menu;
                continue;
            }

            // 当前结点不是根节点，则将该节点组装到父节点的children集合中
            Menu father = menuMap.get(pid); // 通过id与menu对象的对应关系，直接获取父节点对象引用地址
            father.getChildren().add(menu);
        }

        return ResultEntity.successWithData(root);
    }

}
