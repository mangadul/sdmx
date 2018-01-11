package com.sdmx.data;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
public class PublicationApproval implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "approvedBy")
	private Account approvedBy;

	@OneToOne
	@JoinColumn(nullable = false)
	private Publication publication;

	private Date approvedAt;

	public PublicationApproval() {
		approvedAt = new Date();
	}

	public PublicationApproval(Publication publication, Account approvedBy) {
		this.publication = publication;
		this.approvedBy = approvedBy;
		approvedAt = new Date();
	}
}
