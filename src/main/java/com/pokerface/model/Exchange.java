package com.pokerface.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EXCHANGE")
public class Exchange {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
	
	@Column(name = "PHONE")
    private String phone;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PHONE", referencedColumnName="PHONE", insertable=false, updatable=false)
    private User owner;
	
	@Column(name = "EX_RATE")
	private String exRate;
	
	@Column(name = "BUY")
    private Boolean buy;
	
	@Column(name = "AMOUNT")
    private Long amount;
	
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "END_TIME")
	private Date endTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getExRate() {
		return exRate;
	}

	public void setExRate(String exRate) {
		this.exRate = exRate;
	}

	public Boolean getBuy() {
		return buy;
	}

	public void setBuy(Boolean buy) {
		this.buy = buy;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
