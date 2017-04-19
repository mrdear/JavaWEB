package cn.mrdear.util.vo;

import java.util.List;

/**
 * 用于包装接口返回
 * @author Niu Li
 * @since 2017/4/19
 */
public class ResultWrapper<T> {
  /**
   * 状态码
   */
  private Integer status;
  /**
   * 状态信息
   */
  private String msg;
  /**
   * 数据data
   */
  private T data;


  public ResultWrapper OK(){
    //todo 自己根据错误码填充
    this.status = 1;
    this.msg = "Success";
    return this;
  }

  public ResultWrapper OKWithData(T data) {
    this.OK();
    this.data = data;
    return this;
  }

  public ResultWrapper CustomStatus(Integer status, String msg) {
    this.status = status;
    this.msg = msg;
    return this;
  }


  /**
   * 用于滚动数据的返回,举例瀑布流
   * @param <E>
   */
  public static class ScrollData<E>{
    private Integer hasMore;

    private Integer nextSatrt;

    private List<E> objectList;

    public ScrollData(boolean hasMore, Integer nextSatrt, List<E> objectList) {
      this.hasMore = hasMore?1:0;
      this.nextSatrt = nextSatrt;
      this.objectList = objectList;
    }
  }

  /**
   * 用于分页数据返回
   * @param <E>
   */
  private static class PaginationData<E>{
    private Integer total;
    private Integer limit;
    private List<E> objectList;

    public PaginationData(Integer limit, Integer total, List<E> objectList) {
      this.limit = limit;
      this.total = total;
      this.objectList = objectList;
    }
  }

}
