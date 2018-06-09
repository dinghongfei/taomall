package com.taomall.search.service;

import com.taomall.common.pojo.SearchResult;

public interface SearchService {

    SearchResult search(String queryString,int page,int rows) throws Exception;
}
