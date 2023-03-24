package com.penghaisoft.framework.permissionmanagement.model.entity;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.exception.AggregateException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Resources implements Comparable<Resources> {
    private List<Resources> subMenu;

    public List<Resources> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Resources> subMenu) {
        this.subMenu = subMenu;
    }

    private Integer id;

    private Integer parentId;

    private Integer level;

    private Integer sequence;

    private String title;

    private String resCode;

    private String description;

    private String icon;

    private Byte isModifiable;

    private String resType;

    private String url;

    public Integer getId() {
        return id;
    }

    public Resources() {
        this.id = 0;
        this.parentId = 0;
        this.level = 0;
        this.sequence = 0;
        this.title = "";
        this.resCode = "";
        this.description = "";
        this.icon = "";
        this.isModifiable = 1;
        this.resType = null;
        this.url = "";
    }

    public Resources(Integer id, Integer parentId, Integer level, Integer sequence, String title, String resCode, String description, String icon, String resType, String url) {
        this.id = id;
        this.parentId = parentId;
        this.level = level;
        this.sequence = sequence;
        this.title = title;
        this.resCode = resCode;
        this.description = description;
        this.icon = icon;
        this.resType = resType;
        this.url = url;
        this.isModifiable = 1;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Byte getIsModifiable() {
        return isModifiable;
    }

    public void setIsModifiable(Byte isModifiable) {
        this.isModifiable = isModifiable;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType == null ? null : resType.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    @Override
    public int compareTo(Resources o) {
        return this.getSequence() - o.getSequence();
    }


}