package cn.andylhl.crowd.service;

import cn.andylhl.crowd.entity.Menu;

import java.util.List;

/***
 * @Title: MenuService
 * @Description: 菜单业务类
 * @author: lhl
 * @date: 2020/12/30 9:47
 */
public interface MenuService {

    /**
     * 查询全部的menu对象
     * @return
     */
    List<Menu> getAll();

    /**
     * 新增节点对象
     * @param menu
     */
    void saveMenu(Menu menu);

    /**
     * 更新节点
     * @param menu
     */
    void updateMenu(Menu menu);

    /**
     * 根据id删除节点
     * @param id
     */
    void removeMenuById(Integer id);
}
