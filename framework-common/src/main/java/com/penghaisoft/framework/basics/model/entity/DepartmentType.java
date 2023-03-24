package com.penghaisoft.framework.basics.model.entity;

/**
 * Class:部门属性实体
 *
 * @author 秦超
 * 2018/1/30
 */
public class DepartmentType {

    //属性id
    Integer id;
    //属性名称
    String name;

    public DepartmentType(){}
    public DepartmentType(Integer id, String name){
        if(id == null){
            this.id = 0;
        }else {
            this.id = id;
        }

        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
