package com.sdmx.data;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "category")
public class Category implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@ManyToOne(cascade={CascadeType.MERGE})
	@JoinColumn(name="parent_id")
//	@Access(AccessType.PROPERTY)
	private Category parent;

	@OneToMany(mappedBy="parent")
	@OrderBy("name")
	private List<Category> children = new ArrayList<>();

	@ManyToMany(mappedBy="categories", cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
	private Set<News> news;

	@ManyToMany(mappedBy="categories", cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
	private Set<Publication> publication;

	@Transient
	private int level = 1;

	@Transient
	private String[] fillable = {"name"};

	public Map<String, String> rules() {
		Map<String, String> rules = new HashMap<>();
		rules.put("name", "required|max:255");
		rules.put("parent_id", "integer");

		return rules;
	}

	public Category() { }

	public Category(Long id) {
		this.id = id;
	}

	public Category(String name, Category parent) {
		this.name = name;
		this.parent = parent;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

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

	public Category getParent() {
		return parent;
	}

	public boolean hasParent() {
		return parent != null;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public void setParent(Long id) {
		parent = new Category(id);
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}
}
