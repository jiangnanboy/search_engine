package com.sy.searchengine.invertedlist;

import com.sun.javafx.tk.PlatformImage;

import java.util.List;

/**倒排列表类
 * 以文档编号和位置信息为元素的链表结构
 * Created by yanshi on 2017/6/21.
 */
public class PostList {
    int document_id;//文档编号
    List<Integer> positions;//位置信息数组
    int positions_count;//位置个数
    PostList next;//指向下一个倒排列表的指针

    public PostList(int document_id,List<Integer> positions,int positions_count,PostList next){
        this.document_id=document_id;
        this.positions=positions;
        this.positions_count=positions_count;
        this.next=next;
    }

    public void setDocument_id(int document_id){
        document_id=document_id;
    }
    public void getPositions(List<Integer> positions){
        positions=positions;
    }
    public void getPositions_count(int positions_count){
        positions_count=positions_count;
    }
    public void getPostList(PostList next){
        next=next;
    }

    public int getDocument_id(){
        return document_id;
    }
    public List<Integer> getPositions(){
        return positions;
    }
    public int getPositions_count(){
        return positions_count;
    }
    public PostList getPostList(){
        return next;
    }

}
