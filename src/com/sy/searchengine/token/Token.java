package com.sy.searchengine.token;

import com.sy.searchengine.invertedlist.GoSearchEnv;
import com.sy.searchengine.invertedlist.InvertedIndex;

/**
 * Created by yanshi on 2017/7/16.
 */
public class Token {

    /**
     *
     * @param env
     * @param documentID
     * @param text
     * @param token_len N-gram长度N的取值
     * @param postings  倒排列表的数组（也可视作是指向小倒排索引的指针）。若传入的指针指向了NULL，
     *                    则表示要新建一个倒排列表的数组（小倒排索引）。若传入的指针指向了之前就已经存在的倒排列表的数组，
     *                    则表示要添加元素
     * @return 0成功，-1失败
     */
    public int text_to_postings_lists(GoSearchEnv env,int documentID,String text,int token_len,InvertedIndex postings){
    }
}
