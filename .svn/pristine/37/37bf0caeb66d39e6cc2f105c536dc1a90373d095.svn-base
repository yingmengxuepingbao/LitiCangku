package com.penghaisoft.framework.entity;

import com.penghaisoft.framework.constant.Constant;

/**
 * Class:
 *
 * @author 秦超
 * 2018/2/5
 */
public class PageNumber {
    //当前页
    Integer currentPage;
    //每页数据条数
    Integer numberOnePage;
    //数据查询起始位置
    Integer startNumber;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        if(currentPage == null){
            this.currentPage = 1;
        }else{
            this.currentPage = currentPage;
        }
    }

    public Integer getNumberOnePage() {
        return numberOnePage;
    }

    public void setNumberOnePage(Integer numberOnePage) {
        if(numberOnePage == null){
            this.numberOnePage = Constant.ConfigInfo.NUMBER_ONE_PAGE;
        }else{
            this.numberOnePage = numberOnePage;
        }
    }

    public Integer getStartNumber() {
        if(currentPage != null && numberOnePage != null){
            return (currentPage-1) * numberOnePage;
        }else {
            return null;
        }
    }

    public void setStartNumber(Integer startNumber) {
        this.startNumber = startNumber;
    }
}
