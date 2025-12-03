package com.example.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PageResponse<T> {
    @JsonProperty("content")
    private List<T> content;
    
    @JsonProperty("totalElements")
    private Long totalElements;
    
    @JsonProperty("totalPages")
    private Integer totalPages;
    
    @JsonProperty("number")
    private Integer number;
    
    @JsonProperty("size")
    private Integer size;
    
    @JsonProperty("first")
    private Boolean first;
    
    @JsonProperty("last")
    private Boolean last;
    
    @JsonProperty("numberOfElements")
    private Integer numberOfElements;
    
    @JsonProperty("empty")
    private Boolean empty;
    
    public PageResponse() {}
    
    public List<T> getContent() {
        return content;
    }
    
    public void setContent(List<T> content) {
        this.content = content;
    }
    
    public Long getTotalElements() {
        return totalElements;
    }
    
    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
    
    public Integer getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
    
    public Integer getNumber() {
        return number;
    }
    
    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public Integer getSize() {
        return size;
    }
    
    public void setSize(Integer size) {
        this.size = size;
    }
    
    public Boolean isFirst() {
        return first;
    }
    
    public void setFirst(Boolean first) {
        this.first = first;
    }
    
    public Boolean isLast() {
        return last;
    }
    
    public void setLast(Boolean last) {
        this.last = last;
    }
    
    public Integer getNumberOfElements() {
        return numberOfElements;
    }
    
    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
    
    public Boolean isEmpty() {
        return empty;
    }
    
    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }
}

