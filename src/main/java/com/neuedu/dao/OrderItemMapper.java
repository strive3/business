package com.neuedu.dao;

import com.neuedu.pojo.OrderItem;
import java.util.List;

public interface OrderItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item
     *
     * @mbg.generated
     */
    int insert(OrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item
     *
     * @mbg.generated
     */
    OrderItem selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item
     *
     * @mbg.generated
     */
    List<OrderItem> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_order_item
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(OrderItem record);
}