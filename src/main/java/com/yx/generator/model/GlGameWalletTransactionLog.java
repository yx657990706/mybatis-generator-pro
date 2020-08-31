package com.yx.generator.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "gl_game_wallet_transaction_log")
public class GlGameWalletTransactionLog implements Serializable {
    /**
     *gl_game_wallet_transaction_log
     */
    private static final long serialVersionUID = 1L;

    /**
     * 流水号
     */
    @Id
    private Long rid;

    /**
     * 交易单号
     */
    @Column(name = "trade_id")
    private String tradeId;

    /**
     * 发起渠道
     */
    @Column(name = "game_platform_id")
    private Integer gamePlatformId;

    /**
     * 行为 1-投注2-取消投注  3-派奖 4-取消派奖 
     */
    private Integer action;

    /**
     * 用戶Id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用戶名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 状态：0 -处理中 ,1-处理完成
     */
    private Integer status;

    /**
     * 重试次数(max < 5)
     */
    @Column(name = "retry_count")
    private Integer retryCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}