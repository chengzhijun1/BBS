package cn.edu.ncu.bbs.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * page类就是要获得总记录数、每页显示条数、和当前页数来查询数据库
 * @param <T>
 */
@Data
@Repository
public class Page<T> {
    private Integer totalCount; // 总记录数
    private Integer totalPage ; // 总页码      计算
    private Integer currentPage ; //当前页码
    private Integer pageSize = 10;//每页显示条数
    private Integer start;   //每页查询数据库的开始位置     计算
    private List<T> list ; // 每页的数据

    public Page(){

    }

    /**
     * Page类的构造方法
     * @param totalCount   总记录数
     * @param pageSize     每页显示条数
     */
    public Page(int totalCount){
        this.totalCount = totalCount;
    }
    public Page(int totalCount,int pageSize){
        this.totalCount = totalCount;
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     *
     * @return totalPage
     */
    public int getTotalPage() {
        totalPage = totalCount/pageSize;
        if(totalCount % pageSize != 0){
            totalPage++;
        }

        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        if(currentPage < 1){
            currentPage = 1;
        }
        else if(getTotalPage()>0&&currentPage>getTotalPage()){
            currentPage = getTotalPage();
        }

        start = (currentPage-1)*pageSize;
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}