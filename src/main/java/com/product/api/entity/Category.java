package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.sun.istack.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    Integer category_id;

    @NotNull
    @Column(name = "category")
    String category;

    @NotNull
    @Column(name = "acronym")
    String acronym;

    @NotNull
    @Column(name = "status")
    @Min(value = 0, message = "status must be 0 or 1")
    @Max(value = 1, message = "status must be 0 or 1")
    @JsonIgnore
    Integer status;

    public Category(Integer category_id, String category, String acronym) {
        this.category_id = category_id;
        this.category = category;
        this.acronym = acronym;
    }

    public Category(Integer category_id, String category, String acronym, Integer status) {
        this.category_id = category_id;
        this.category = category;
        this.acronym = acronym;
        this.status = status;
    }

    public Category() {

    }

    public Integer getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public String getAcronym() {
        return acronym;
    }

    public Integer getStatus() {
        return status;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", category='" + category + '\'' +
                ", acronym='" + acronym + '\'' +
                ", status=" + status +
                '}';
    }

}
