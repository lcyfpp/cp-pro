package com.linkedaim.admin.system.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_menu")
public class Menu implements Serializable {
    /**
     * 菜单/按钮id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上级菜单id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    private String type;

    /**
     * 状态 1-启用 0-禁用
     */
    private String status;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Long orderNum;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 获取菜单/按钮id
     * @return id - 菜单/按钮id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置菜单/按钮id
     * @param id 菜单/按钮id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取上级菜单id
     * @return parent_id - 上级菜单id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置上级菜单id
     * @param parentId 上级菜单id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取菜单/按钮名称
     * @return menu_name - 菜单/按钮名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单/按钮名称
     * @param menuName 菜单/按钮名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * 获取菜单url
     * @return url - 菜单url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单url
     * @param url 菜单url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取图标
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 获取类型 0菜单 1按钮
     * @return type - 类型 0菜单 1按钮
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型 0菜单 1按钮
     * @param type 类型 0菜单 1按钮
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取状态 1-启用 0-禁用
     * @return status - 状态 1-启用 0-禁用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 1-启用 0-禁用
     * @param status 状态 1-启用 0-禁用
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取排序
     * @return order_num - 排序
     */
    public Long getOrderNum() {
        return orderNum;
    }

    /**
     * 设置排序
     * @param orderNum 排序
     */
    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取创建时间
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取权限标识
     * @return perms - 权限标识
     */
    public String getPerms() {
        return perms;
    }

    /**
     * 设置权限标识
     * @param perms 权限标识
     */
    public void setPerms(String perms) {
        this.perms = perms == null ? null : perms.trim();
    }
}
