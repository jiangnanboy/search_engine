package com.sy.searchengine.invertedlist;

import java.util.Map;

/**
 * 倒排索引，管理倒排列表
 * 以词元编号为键，以倒排列表为值的关联数组
 * Created by yanshi on 2017/6/21.
 */
public class InvertedIndex {
    int token_id;//词元编号
    PostList postList;//指向包含该词元的倒排列表
    int docs_count;//出现该词元的文档数
    int positions_count;//所有文档中该词元出现的次数和
    Map hh;//用于转化为哈希表
}
