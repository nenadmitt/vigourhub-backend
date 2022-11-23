package com.vigourhub.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResponse<T> {
    private int totalItems;
    private List<T> items;
    private int perPage;
    private int totalPages;
    private int currentPage;
}
