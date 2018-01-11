package com.sdmx.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "data_flow")
public class DataFlow implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "jsonb")
	private String meta;

	@Column(columnDefinition = "text")
	private String note;

	@ManyToMany(targetEntity=Category.class, cascade={CascadeType.ALL})
	@JoinTable(
		name="category_data_flow",
		joinColumns={@JoinColumn(name="data_flow_id")},
		inverseJoinColumns={@JoinColumn(name="category_id")}
	)
	private Set<Category> categories = new HashSet<>();

	@ManyToMany(targetEntity=Variable.class, cascade={CascadeType.ALL})
	@JoinTable(
		name="data_flow_item",
		joinColumns={@JoinColumn(name="data_flow_id")},
		inverseJoinColumns={@JoinColumn(name="variable_id")}
	)
	private Set<Variable> variables = new HashSet<>();

	private Date updated;

	private Date created;

	@Transient
	private String[] fillable = {"name", "category", "meta", "note", "variables", "updated"};

	public DataFlow() {
		created = new Date();
		updated = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public JsonNode getMetaAsJson() {
		try {
			return (new ObjectMapper()).readTree(meta);
		}
		catch (Exception e) {
			System.out.println("Failed to read JSON: "+ e.getMessage());
			return null;
		}
	}

	public void setMeta(JsonNode meta) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

		try {
			this.meta = mapper.writeValueAsString(meta);
		}
		catch (Exception e) {
			System.out.println("Failed to write JSON Object: "+ e.getMessage());
		}
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		note = note;
	}

	public Set<Variable> getVariables() {
		return variables;
	}

	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getCreated() {
		return created;
	}
}
