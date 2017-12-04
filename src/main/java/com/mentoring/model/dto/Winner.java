package com.mentoring.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Class represents DTO version of Person POJO. Used only for UI representation.
 */
@JsonPropertyOrder({"id", "name"})
public class Winner {

    @JsonProperty("winner-index")
    private Integer index;
    private String name;

    public Winner(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
