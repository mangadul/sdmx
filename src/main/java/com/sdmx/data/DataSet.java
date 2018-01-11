package com.sdmx.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "data_set")
public class DataSet implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 4)
	private String year;

	@Column(nullable = false, length = 2)
	private String month;

	private BigDecimal value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "variable_id", nullable = false)
	private Variable variable;

	@Column(columnDefinition = "jsonb")
	private String attributes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public JsonNode getAttributesAsJson() {
		try {
			return (new ObjectMapper()).readTree(attributes);
		}
		catch (Exception e) {
			System.out.println("Failed to read JSON: "+ e.getMessage());
			return null;
		}
	}

	public void setAttributes(JsonNode attributes) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

		try {
			this.attributes = mapper.writeValueAsString(attributes);
		}
		catch (Exception e) {
			System.out.println("Failed to write JSON Object: "+ e.getMessage());
		}
	}
}
