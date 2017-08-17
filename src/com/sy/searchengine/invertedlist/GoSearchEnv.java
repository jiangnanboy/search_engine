package com.sy.searchengine.invertedlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 应用程序的全局配置
 * Created by yanshi on 2017/6/22.
 */
public class GoSearchEnv {
    public String db_path;//数据库路径
    public int token_len;//词元的长度。N-gram中的的取值
    public CompressMethod compress;//压缩倒排列表等数据的方法
    public int enable_phrase_search;//是否进行短语检索
    public InvertedIndex ii_buffer;//用于更新倒排索引的缓冲区（buffer)
    public int ii_buffer_count;//用于更新倒排索引的缓冲区中的文档数
    public int ii_buffer_update_threshold;//缓冲区中文档数的阈值
    public int indexed_count;//建立索引的文档数

    // 与sqlite3相关的配置
    public Connection conn=null; // sqlite3的实例

    //根据文章title得到文章id
    public PreparedStatement get_document_id=null;
    public int getDocumentID(String title){
        int id=0;
        try {
            get_document_id.setString(1,title);
            ResultSet resultSet=get_document_id.executeQuery();
            id=resultSet.getInt("id");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //根据文章id得到文章title
    public PreparedStatement get_document_title=null;
    public String getDocumentTittle(int id){
        String title=null;
        try {
            get_document_title.setInt(1, id);
            ResultSet resultSet=get_document_title.executeQuery();
            title=resultSet.getString("title");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return title;
    }

    public PreparedStatement insert_document=null;
    public boolean inseartDocument(String title,String body){
        boolean flag=true;
        try {
            insert_document.setString(1,title);
            insert_document.setString(2,body);
            int i=insert_document.executeUpdate();
            if(i==0)
                flag=false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据文档id更新正文body
     */
    public PreparedStatement update_document=null;
    public boolean updateDocument(int id,String body){
        boolean flag=true;
        try {
            update_document.setString(1,body);
            update_document.setInt(2,id);
            int i=update_document.executeUpdate();
            if(i==0)
                flag=false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public PreparedStatement get_token_id=null;
    public void getTokenID(){
        try {
            get_token_id.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PreparedStatement get_token=null;
    public void getToken(){
        try {
            get_token.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PreparedStatement store_token=null;
    public void storeToken(){
        try {
            store_token.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PreparedStatement get_postings=null;
    public void getPostings(){
        try {
            get_postings.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PreparedStatement update_postings=null;
    public void updatePostings(){
        try {
            update_postings.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PreparedStatement get_settings=null;
    public void getSetings(){
        try {
            get_settings.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PreparedStatement replace_settings=null;
    public void replaceSettings(){
        try {
            replace_settings.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PreparedStatement get_document_count=null;
    public void getDocumentCount(){
        try {
            get_document_count.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交，如有错，则回滚
     */
    public void commit_rollback(){
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try{
                if(conn!=null){
                    conn.rollback();
                }
            }catch(SQLException e2){
                e2.printStackTrace();
            }
        }
    }

}
