package com.wintop.ms.carauction.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wintop.ms.carauction.core.config.ResultCode;
import com.wintop.ms.carauction.core.entity.ServiceResult;
import com.wintop.ms.carauction.entity.CarChaboshiLog;
import com.wintop.ms.carauction.entity.CarChaboshiStoreAccount;
import com.wintop.ms.carauction.entity.CarFinancePayLog;
import com.wintop.ms.carauction.model.CarChaboshiLogModel;
import com.wintop.ms.carauction.model.CarChaboshiStoreAccountModel;
import com.wintop.ms.carauction.model.CarFinancePayLogModel;
import com.wintop.ms.carauction.service.ICarChaboshiLogService;
import com.wintop.ms.carauction.util.AlipayUtil;
import com.wintop.ms.carauction.util.ChaboshiUtils;
import com.wintop.ms.carauction.util.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查博士日志 服务层实现
 *
 * @author ruoyi
 * @date 2019-05-08
 */
@Service
public class CarChaboshiLogServiceImpl implements ICarChaboshiLogService {
    @Autowired
    private CarChaboshiLogModel model;
    @Autowired
    private CarChaboshiStoreAccountModel storeAccountModel;
    @Autowired
    private CarFinancePayLogModel financePayLogModel;

    private IdWorker idWorker = new IdWorker(10);


    /**
     * 查询查博士日志信息
     *
     * @param id 查博士日志ID
     * @return 查博士日志信息
     */
    @Override
    public CarChaboshiLog selectCarChaboshiLogById(Long id) {
        return model.selectCarChaboshiLogById(id);
    }

    /**
     * 查询查博士日志列表
     *
     * @param carChaboshiLog 查博士日志信息
     * @return 查博士日志集合
     */
    @Override
    public List<CarChaboshiLog> selectCarChaboshiLogList(CarChaboshiLog carChaboshiLog) {
        return model.selectCarChaboshiLogList(carChaboshiLog);
    }

    /**
     * 新增查博士日志
     *
     * @param carChaboshiLog 查博士日志信息
     * @return 结果
     */
    @Override
    public int insertCarChaboshiLog(CarChaboshiLog carChaboshiLog) {
        return model.insertCarChaboshiLog(carChaboshiLog);
    }

    /**
     * 修改查博士日志
     *
     * @param carChaboshiLog 查博士日志信息
     * @return 结果
     */
    @Override
    public int updateCarChaboshiLog(CarChaboshiLog carChaboshiLog) {
        return model.updateCarChaboshiLog(carChaboshiLog);
    }

    /**
     * 删除查博士日志对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCarChaboshiLogByIds(String ids) {
        return model.deleteCarChaboshiLogByIds(ids);
    }

    @Override
    public int selectCount(CarChaboshiLog carChaboshiLog) {
        return model.selectCount(carChaboshiLog);
    }

    /**
     * 根据查博士订单id获取查询日志
     *
     * @param log
     * @return
     */
    @Override
    public CarChaboshiLog selectCarChaboshiLog(CarChaboshiLog log) {
        List<CarChaboshiLog> logs = selectCarChaboshiLogList(log);
        if (logs != null && logs.size() > 0) {
            return logs.get(0);
        }
        return null;
    }

    /**
     * 个人资金流水
     *
     * @param payLog
     */
    public int savePayLog(CarFinancePayLog payLog) {
        payLog.setId(idWorker.nextId());
        payLog.setStatus("3");//退款
        payLog.setCreateTime(new Date());
        return financePayLogModel.insert(payLog);
    }

    /**
     * 买家查询
     *
     * @param userId
     * @param userName
     * @param edition
     * @param logId
     * @param vin
     * @return
     */
    @Override
    public ServiceResult<Map<String, Object>> chaboshi(Long userId, String userName, String edition, Long logId, String vin) {
        ServiceResult result = new ServiceResult();
        Map data = new HashMap();
        JSONObject object = null;
        object = cha(edition, vin, result);

        CarChaboshiLog log = selectCarChaboshiLogById(logId);
        boolean isSuccess = false;
        if (object != null) {

            if ("0".equals(object.get("code"))) {
                /*查询成功*/
                data.put("orderId", object.getString("orderId"));
                result.setSuccess(ResultCode.SUCCESS.strValue(), ResultCode.SUCCESS.getRemark());
                result.setResult(data);

                log.setOrderId(object.getString("orderId"));
                log.setOrderMsg(object.getString("Message"));
                log.setResponseResult("1");
                isSuccess = true;
            } else {
                /*查询失败*/
                log.setOrderMsg(object.getString("Message"));
                log.setResponseResult("2");
                result.setSuccess(ResultCode.FAIL.strValue(), object.getString("Message"));
            }
        } else {
            log.setResponseResult("2");
            result.setSuccess(ResultCode.FAIL.strValue(), "查找失败！");
        }
        /*更新日志*/
        int code = updateCarChaboshiLog(log);
        if (!isSuccess) {
            /*退款到个人支付宝*/
            CarFinancePayLog payLog = financePayLogModel.selectById(log.getPayLogId());
            Map map = AlipayUtil.refundOrder(payLog);
            if ("0".equals(map.get("code"))) {
                /*退款成功*/
                int c = savePayLog(payLog);
            } else {

                /*退款失败*/
            }
            //TODO 买家退款失败 --  状态提醒
        }
        return result;
    }

    /**
     * 店铺的查博士查询
     *
     * @param payment
     * @return
     */
    @Override
    public ServiceResult<Map<String, Object>> chaboshiStore(BigDecimal payment, JSONObject obj) {

        String edition = obj.getString("edition");
        String vin = obj.getString("vin");
        Long storeId = obj.getLong("storeId");
        Long userId = obj.getLong("userId");
        String userName = obj.getString("userName");

        ServiceResult result = new ServiceResult();
        Map data = new HashMap();
        JSONObject object = null;

        object = cha(edition, vin, result);

        /*log 创建存储*/
        CarChaboshiLog log = new CarChaboshiLog();
        log.setId(idWorker.nextId());
        log.setStoreId(storeId);
        log.setUserName(userName);
        log.setUserId(userId);
        log.setEdition(edition);
        log.setMoney(payment);
        log.setSourceType("1");
        log.setVin(vin);
        log.setUserType("2");
        log.setCreateTime(new Date());

        /*车型信息*/
        log.setVehicleId(obj.getLong("vehicleId"));
        log.setVehicleType(obj.getString("vehicleType"));
        log.setPhoto(obj.getString("photo"));
        log.setEngineNum(obj.getString("engineNum"));


        if (object != null) {
            if ("0".equals(object.get("code"))) {
                data.put("orderId", object.getString("orderId"));
                result.setSuccess(ResultCode.SUCCESS.strValue(), ResultCode.SUCCESS.getRemark());
                result.setResult(data);

                log.setOrderId(object.getString("orderId"));
                log.setOrderMsg(object.getString("Message"));
                log.setResponseResult("1");
                /*扣款*/
                CarChaboshiStoreAccount account = new CarChaboshiStoreAccount();
                account.setStoreId(storeId);
                List<CarChaboshiStoreAccount> accounts = storeAccountModel.selectCarChaboshiStoreAccountList(account);

                if (accounts != null && accounts.size() > 0) {
                    CarChaboshiStoreAccount c = accounts.get(0);
                    c.setId(idWorker.nextId());
                    c.setBalance(c.getBalance().subtract(payment));

                    /*小于零 扣款失败*/
                    if (c.getBalance().compareTo(BigDecimal.ZERO) == -1) {
                        log.setResponseResult("2");//失败
                        result.setSuccess(ResultCode.FAIL.strValue(), "扣款失败！");

                    } else {
                        c.setUserName(userName);
                        c.setUserId(userId);
                        c.setCreateTime(new Date());
                        c.setServiceType("2");//查询报告
                        c.setType("2");//出账
                        /*插入流水记录*/
                        storeAccountModel.insertCarChaboshiStoreAccount(c);
                    }

                } else {
                    /*没有找到流水记录*/
                    log.setResponseResult("2");//失败
                    result.setSuccess(ResultCode.FAIL.strValue(), "扣款失败！");
                }
            } else {
                /*查博士查找失败*/
                log.setResponseResult("2");
                log.setOrderMsg(object.getString("Message"));

                result.setSuccess(ResultCode.FAIL.strValue(), object.getString("Message"));
            }
        } else {
            /*查询失败*/
            log.setResponseResult("2");
            result.setSuccess(ResultCode.FAIL.strValue(), "查找失败！");
        }

        insertCarChaboshiLog(log);
        return result;
    }

    /**
     * 根据您历史--订单查找
     *
     * @param log
     * @param userId
     * @param userName
     * @return
     */

    @Override
    public ServiceResult<Map<String, Object>> chaboshiOrder(CarChaboshiLog log, Long userId, String userName) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<>();

        if (log == null || log.getOrderId() == null) {
            result.setSuccess(ResultCode.FAIL.strValue(), "未发现查博士订单！");
        } else {
            JSONObject orderStatus = ChaboshiUtils.orderStatus(log.getOrderId());

            log.setId(idWorker.nextId());
            log.setMoney(new BigDecimal(0));
            log.setSourceType("2");
            log.setUserId(userId);
            log.setUserName(userName);
            log.setCreateTime(new Date());


            if ("1104".equals(orderStatus.get("code"))) {
                /*已出报告*/
                Map cbs = ChaboshiUtils.reportDetail(log.getOrderId());

                log.setResponseResult("1");

                result.setResult(cbs);
                result.setSuccess(ResultCode.SUCCESS.strValue(), ResultCode.SUCCESS.getRemark());
            } else if ("1102".equals(orderStatus.get("code"))) {
                /*查询中*/
                result.setSuccess(ResultCode.FAIL.strValue(), "订单正在查询中！");
                log.setResponseResult("3");
            } else {
                /*其他情况*/
                log.setResponseResult("2");
                log.setOrderMsg(orderStatus.getString("Message"));

                result.setSuccess(ResultCode.FAIL.strValue(), orderStatus.getString("Message"));
                log.setResponseResult("3");
            }

            /*写入日志*/
            insertCarChaboshiLog(log);
        }
        return result;
    }

    /**
     * 查博士查询
     *
     * @param edition
     * @param vin
     * @param result
     * @return
     */
    private JSONObject cha(String edition, String vin, ServiceResult result) {
        JSONObject object = null;
        if ("1".equals(edition)) {
            //维修版本
            object = ChaboshiUtils.repairReport(vin);
        } else if ("2".equals(edition)) {
            //综合版本
            object = ChaboshiUtils.report(vin);
        } else {
            result.setSuccess(ResultCode.FAIL.strValue(), ResultCode.NO_OBJECT.getRemark());
        }
        return object;
    }
}