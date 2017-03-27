package com.woof.gankist.dao.db;

import java.util.List;

/**
 * DB基类，基本的增删改查功能
 * Created by Woof on 3/20/2017.
 */

public interface BaseDBHelper<Model, QueryParams> {

    long insert(Model model);
    void delete(QueryParams queryParams);
    void update(QueryParams queryParams, Model model);
    List<Model> query(QueryParams queryParams);
}
