package com.sdmx.data;

import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "variable")
public class Variable implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="parent_id")
	private Variable parent;

	@OneToMany(mappedBy="variable")
	private List<DataSet> dataSets;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="parent")
	@OrderBy("name")
	private List<Variable> children = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Variable getParent() {
		return parent;
	}

	public void setParent(Variable parent) {
		this.parent = parent;
	}

	public List<Variable> getChildren() {
		return children;
	}

	public void setChildren(List<Variable> children) {
		this.children = children;
	}
}
