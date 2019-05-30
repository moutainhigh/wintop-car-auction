package com.wintop.ms.carauction.controller;

import com.alibaba.fastjson.JSONObject;
import com.wintop.ms.carauction.core.config.ManagerRole;
import com.wintop.ms.carauction.core.entity.PageEntity;
import com.wintop.ms.carauction.core.entity.ServiceResult;
import com.wintop.ms.carauction.entity.CarManagerUser;
import com.wintop.ms.carauction.entity.CarSaleOrder;
import com.wintop.ms.carauction.entity.ListEntity;
import com.wintop.ms.carauction.service.ICarManagerUserService;
import com.wintop.ms.carauction.service.ICarSaleOrderService;
import com.wintop.ms.carauction.util.utils.CarAutoUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/service/carSaleOrder")
public class CarSaleOrderListApi {
    private static final Logger logger = LoggerFactory.getLogger(CarSaleOrderListApi.class);
    @Autowired
    private ICarSaleOrderService iCarSaleOrderService;

    @Autowired
    private ICarManagerUserService iCarManagerUserService;

    @ApiOperation(value = "查询零售订单")
    @PostMapping(value = "getCarSaleOrderRetailList", produces="application/json; charset=UTF-8")
    public ServiceResult<ListEntity<Map<String,Object>>> getCarSaleOrderRetailList(@RequestBody JSONObject object){
        ServiceResult<ListEntity<Map<String,Object>>> result=new ServiceResult<>();
        try{
            Map paramMap = new HashMap();
            Long customerId = object.getLong("customerId");
            //查询用户权限
            CarManagerUser carManagerUser = iCarManagerUserService.selectByPrimaryKey(customerId,true);
            paramMap.put("userId",carManagerUser.getId());
            //如果用户是中心店管理员
            if(ManagerRole.ZX_ESCFZR.value() == carManagerUser.getRoleId()){
//                paramMap.put("auctionType","2");//现场车辆
//                paramMap.put("roleTyped","2");//中心店
                paramMap.put("departmentId",carManagerUser.getDepartmentId());
                paramMap.put("managerRole",carManagerUser.getRoleId());
            }
            //如果用户是店铺管理员
            if(ManagerRole.JXD_ESCFZR.value() == carManagerUser.getRoleId()){
//                paramMap.put("auctionType","1");//线上车辆
//                paramMap.put("roleTyped","3");//店铺
                paramMap.put("departmentId",carManagerUser.getDepartmentId());
                paramMap.put("managerRole",carManagerUser.getRoleId());
            }
            paramMap.put("customerId",customerId);
            int count = iCarSaleOrderService.selectCarSaleOrderCount(paramMap);
            PageEntity pageEntity= CarAutoUtils.getPageParam(object);
            paramMap.put("startRowNum",pageEntity.getStartRowNum());
            paramMap.put("endRowNum",pageEntity.getEndRowNum());
            List<CarSaleOrder> carSaleOrders = iCarSaleOrderService.selectCarSaleOrder(paramMap);
            List<Map<String,Object>> list = new ArrayList<>();
            for (CarSaleOrder carSaleOrder : carSaleOrders){
                Map<String,Object> map = new HashMap<>();
                map.put("id",carSaleOrder.getId());
                map.put("earnestMoney",carSaleOrder.getEarnestMoney());
                map.put("name", carSaleOrder.getName());
                map.put("autoInfoName", carSaleOrder.getAutoInfoName());
                map.put("vehicleAttributionCityCN",carSaleOrder.getVehicleAttributionCityCN());
                map.put("mileage",carSaleOrder.getMileage());
                map.put("reportColligationRanks",carSaleOrder.getReportColligationRanks());
                map.put("reportServicingRanks",carSaleOrder.getReportServicingRanks());
                map.put("mainPhoto",carSaleOrder.getMainPhoto());
                map.put("transactionFee",carSaleOrder.getTransactionFee());
                map.put("storeName",carSaleOrder.getStoreName());
                map.put("saleDate",carSaleOrder.getSalesDate());
                map.put("salesType",carSaleOrder.getSalesType());
                map.put("managerName",carSaleOrder.getManagerName());
                map.put("salesConsultant",carSaleOrder.getSalesConsultant());
                list.add(map);
            }
            ListEntity<Map<String,Object>> listEntity = new ListEntity<>();
            listEntity.setList(list);
            listEntity.setCount(count);
            result.setResult(listEntity);
            result.setSuccess("0","成功");
        }catch(Exception e){
            logger.info("查询零售订单信息失败",e);
            result.setError("-1","异常");
        }
        return result;

    }

    @ApiOperation(value = "查询零售订单详情")
    @PostMapping(value = "getCarSaleOrderRetail", produces="application/json; charset=UTF-8")
    public ServiceResult<CarSaleOrder> getCarSaleOrderRetail(@RequestBody JSONObject object){
        ServiceResult<CarSaleOrder> result=new ServiceResult<>();
        Map params = new HashMap();
        try{
            params.put("customerId",object.getLong("customerId"));
            params.put("id",object.getLong("orderId"));
            CarSaleOrder carSaleOrder = iCarSaleOrderService.selectCarSaleOrderRetail(params);
            result.setResult(carSaleOrder);
            result.setSuccess("0","成功");
        }catch(Exception e){
            logger.info("查询零售订单详情失败",e);
            result.setError("-1","异常");
        }
        return result;
    }
}