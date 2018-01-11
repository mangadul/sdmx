package com.sdmx.data;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "news_approval")
public class NewsApproval implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "approvedBy")
	private Account approvedBy;

	@OneToOne
	@JoinColumn(nullable = false)
	private News news;

	private Date approvedAt;

	public NewsApproval() {
		approvedAt = new Date();
	}

	public NewsApproval(News news, Account approvedBy) {
		this.news = news;
		this.approvedBy = approvedBy;
		approvedAt = new Date();
	}
}
