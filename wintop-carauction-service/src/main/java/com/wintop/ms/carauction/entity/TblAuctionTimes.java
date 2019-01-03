package com.wintop.ms.carauction.entity;

import java.io.Serializable;
import java.util.Date;

public class TblAuctionTimes implements Serializable {

    private static final long serialVersionUID = -3573385811247971118L;
    private Long id;

    /**
     * 基站主键id
     */
    private Long bsId;

    /**
     * 拍牌显示名字
     */
    private String boardRealName;

    /**
     * 基站物理ID(十六进制)
     */
    private String stationRealCode;

    /**
     * 基站代码
     */
    private String stationCode;

    /**
     * 后台拍卖场次ID
     */
    private Long localAuctionId;

    /**
     * 拍卖场次名字
     */
    private String auctionTimes;

    /**
     * 开拍时间
     */
    private Date startAuctionTime;

    /**
     * 结束时间
     */
    private Date endAuctionTime;

    /**
     * 总拍卖数量
     */
    private Integer num;

    /**
     * 是否有效(0:有效；1:无效)
     */
    private String enable;

    /**
     * 备注
     */
    private String remarks;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 基站物理ID(十六进制)
     */
    public String getStationRealCode() {
        return stationRealCode;
    }

    /**
     * @param stationRealCode 
	 *            基站物理ID(十六进制)
     */
    public void setStationRealCode(String stationRealCode) {
        this.stationRealCode = stationRealCode;
    }

    /**
     * @return 基站代码
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * @param stationCode 
	 *            基站代码
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * @return 后台拍卖场次ID
     */
    public Long getLocalAuctionId() {
        return localAuctionId;
    }

    /**
     * @param localAuctionId 
	 *            后台拍卖场次ID
     */
    public void setLocalAuctionId(Long localAuctionId) {
        this.localAuctionId = localAuctionId;
    }

    /**
     * @return 拍卖场次名字
     */
    public String getAuctionTimes() {
        return auctionTimes;
    }

    /**
     * @param auctionTimes 
	 *            拍卖场次名字
     */
    public void setAuctionTimes(String auctionTimes) {
        this.auctionTimes = auctionTimes;
    }

    /**
     * @return 开拍时间
     */
    public Date getStartAuctionTime() {
        return startAuctionTime;
    }

    /**
     * @param startAuctionTime 
	 *            开拍时间
     */
    public void setStartAuctionTime(Date startAuctionTime) {
        this.startAuctionTime = startAuctionTime;
    }

    /**
     * @return 结束时间
     */
    public Date getEndAuctionTime() {
        return endAuctionTime;
    }

    /**
     * @param endAuctionTime 
	 *            结束时间
     */
    public void setEndAuctionTime(Date endAuctionTime) {
        this.endAuctionTime = endAuctionTime;
    }

    /**
     * @return 总拍卖数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * @param num 
	 *            总拍卖数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * @return 是否有效(0:有效；1:无效)
     */
    public String getEnable() {
        return enable;
    }

    /**
     * @param enable 
	 *            是否有效(0:有效；1:无效)
     */
    public void setEnable(String enable) {
        this.enable = enable;
    }

    /**
     * @return 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks 
	 *            备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getBsId() {
        return bsId;
    }

    public void setBsId(Long bsId) {
        this.bsId = bsId;
    }

    public String getBoardRealName() {
        return boardRealName;
    }

    public void setBoardRealName(String boardRealName) {
        this.boardRealName = boardRealName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}