package com.wesayweb.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "first_impression")
@EntityListeners(AuditingEntityListener.class)
public class FirstImpression implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String impressiontitle;
	private String impressiondecription;
	private Long impressiongivenby;
	private Long impressiongivenfor;
	@Column(name = "impressionapprovestatus", nullable = false, columnDefinition = "int default 0")
	private int impressionapprovestatus;
	
	@Column(name = "impressiondeletestatus", nullable = false, columnDefinition = "int default 0")
	private int impressiondeletestatus;
	private Date creationdate = new Date();
	private Date updateondate = new Date();
	private Date approveddate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImpressiontitle() {
		return impressiontitle;
	}
	public void setImpressiontitle(String impressiontitle) {
		this.impressiontitle = impressiontitle;
	}
	public String getImpressiondecription() {
		return impressiondecription;
	}
	public void setImpressiondecription(String impressiondecription) {
		this.impressiondecription = impressiondecription;
	}
	public Long getImpressiongivenby() {
		return impressiongivenby;
	}
	public void setImpressiongivenby(Long impressiongivenby) {
		this.impressiongivenby = impressiongivenby;
	}
	public Long getImpressiongivenfor() {
		return impressiongivenfor;
	}
	public void setImpressiongivenfor(Long impressiongivenfor) {
		this.impressiongivenfor = impressiongivenfor;
	}
	public int getImpressionapprovestatus() {
		return impressionapprovestatus;
	}
	public void setImpressionapprovestatus(int impressionapprovestatus) {
		this.impressionapprovestatus = impressionapprovestatus;
	}
	public int getImpressiondeletestatus() {
		return impressiondeletestatus;
	}
	public void setImpressiondeletestatus(int impressiondeletestatus) {
		this.impressiondeletestatus = impressiondeletestatus;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public Date getUpdateondate() {
		return updateondate;
	}
	public void setUpdateondate(Date updateondate) {
		this.updateondate = updateondate;
	}
	public Date getApproveddate() {
		return approveddate;
	}
	public void setApproveddate(Date approveddate) {
		this.approveddate = approveddate;
	}
 

}