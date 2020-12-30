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
}
