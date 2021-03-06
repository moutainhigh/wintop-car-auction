package com.wintop.ms.carauction.service;

import com.alibaba.fastjson.JSONObject;
import com.wintop.ms.carauction.core.entity.ServiceResult;
import com.wintop.ms.carauction.entity.CarAuto;
import com.wintop.ms.carauction.entity.CarAutoAuction;

import java.util.List;
import java.util.Map;

public interface ICarAutoService {

    ServiceResult<Integer> countByExample(Map<String,Object> map);

    ServiceResult<CarAuto> selectByPrimaryKey(Long id);

    ServiceResult<List<CarAuto>> selectByExample(Map<String,Object> map);

    ServiceResult<Integer> deleteByPrimaryKey(Long id);

    ServiceResult<Integer> updateByPrimaryKeySelective(CarAuto record);

    ServiceResult<CarAuto> updateByPrimaryKey(CarAuto record);

    ServiceResult<CarAuto> insert(CarAuto record);

    ServiceResult<Integer> insertSelective(CarAuto record);

    /**
     * 车辆信息接口
     * @return
     */
    CarAuto selectByCarId(Map<String,Object> map);

    /**
     * 根据条件查询记录
     */
    public List<CarAuto> selectAuctionCarList(Map<String,Object> map);

    /**
     * 查询总数量
     * @param map
     * @return
     */
    public int selectAuctionCarCount(Map<String,Object> map);

    /**
     * 根据条件查询线上拍的车辆列表
     */
     List<CarAuto> selectOnlineCarList(Map<String,Object> map);

    /**
     * 根据条件查询线上拍的车辆总数量
     * @param map
     * @return
     */
     int selectOnlineCarCount(Map<String,Object> map);

    /***
     * 查询用户最新发布的车辆
     * @param paramMap
     * @return
     */
     CarAuto selectMyLastAuto(Map paramMap);

    /**
     * 根据条件查询发拍的车辆列表
     */
    List<CarAuto> selectHairShotCarList(Map<String,Object> map);

    /**
     * 根据条件查询线发拍的车辆数量
     * @param map
     * @return
     */
    int selectHairShotCarCount(Map<String,Object> map);

    /**
     * @Author 付陈林
     * @Date 2018年3月21号
     * @About 获取所有可以参加竞拍的车辆，1、status为5 等待开拍  2、竞拍类型为线下竞拍  3、未加入到已参加的竞拍列表中
     * */
    ServiceResult<List<CarAuto>> getLocaleAuctionCar(String searchParam);
    /**
     *查询所有的车辆列表
     * @param map
     * @return
     */
    ServiceResult<List<CarAuto>> getAllCarAutoList(Map<String,Object> map);
    /**
     *查询所有的车辆数目
     * @Author zhangzijiuan
     * @param map
     * @return
     */
     ServiceResult<Integer> getAllCarAutoCount(Map<String,Object> map);

    /**
     * 审核撤回审批的车辆
     *@Author:zhangzijuan
     *@date 2018/3/23
     *@param:
     */
     Integer revokeApprove(JSONObject object);
    /**
     * 审批发拍的车辆
     *@Author:zhangzijuan
     *@date 2018/3/24
     *@param:
     */
     Integer approveCarAuto(JSONObject object);

    /**
     *获取当日上新车辆数
     */
    Integer selectDayCarCount(Map<String,Object> map);

     CarAuto selectById(Long id);

    int updateByIdSelective(CarAuto record);

    /**
     *  申请二拍
     * */
     Integer setAgainAuction(JSONObject object,CarAuto carAuto);

    /**
     * 根据车辆id查询起拍价和保留价
     */
    CarAutoAuction  selectCarInfoById(JSONObject object);
}