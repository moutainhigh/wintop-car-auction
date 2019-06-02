package com.wintop.ms.carauction.service;

import com.wintop.ms.carauction.entity.CarOrderRetail;

public interface ICarOrderRetailService {

    /**
     * 插入记录
     * @param record
     * @return
     */
    public void insertSelective(CarOrderRetail record);

    /**
     * 订单明细表
     * @param id
     * @return
     */
    public CarOrderRetail selectRetailById(Long id);
}
