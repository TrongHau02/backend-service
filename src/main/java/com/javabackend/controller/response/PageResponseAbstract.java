package com.javabackend.controller.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public abstract class PageResponseAbstract implements Serializable {
    public int pageNumber;
    public int pageSize;
    public long totalElement;
    public int totalPages;
}
