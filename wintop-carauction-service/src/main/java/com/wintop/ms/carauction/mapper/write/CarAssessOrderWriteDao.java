package com.wintop.ms.carauction.mapper.write;

import com.wintop.ms.carauction.entity.CarAssessOrder;

/**
 * 评估采购单 数据层
 * 
 * @author ruoyi
 * @date 2019-05-05
 */
public interface CarAssessOrderWriteDao
{

	/**
     * 新增评估采购单
     *
     * @param carAssessOrder 评估采购单信息
     * @return 结果
     */
	public int insertCarAssessOrder(CarAssessOrder carAssessOrder);

	/**
     * 修改评估采购单
     *
     * @param carAssessOrder 评估采购单信息
     * @return 结果
     */
	public int updateCarAssessOrder(CarAssessOrder carAssessOrder);
	
	/**
     * 删除评估采购单
     * 
     * @param id 评估采购单ID
     * @return 结果
     */
	public int deleteCarAssessOrderById(Long id);
	
	/**
     * 批量删除评估采购单
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCarAssessOrderByIds(String[] ids);
	
}