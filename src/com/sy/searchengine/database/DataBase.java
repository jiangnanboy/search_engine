package com.sy.searchengine.database;

import org.sqlite.*;
import com.sy.searchengine.invertedlist.GoSearchEnv;

import java.sql.*;

/**
 *
 * Created by yanshi on 2017/6/27.
 */
public class DataBase {

    /**
     * 初始化数据库
     * @param[in] env 存储着应用程序运行环境的结构体
     * @param[in] db_path 待初始化的数据库文件的名字
     * @return sqlite3的错误代码
     * @retval 0 成功
     */
    public int init_database(GoSearchEnv env,String db_path){
        System.out.println("DB init...");
        Connection conn=env.conn;
        PreparedStatement prst=null;
        try{
            Class.forName("org.sqlite.JDBC");
            conn= DriverManager.getConnection("jdbc:sqlite:"+db_path);
            conn.setAutoCommit(false);//关闭自动提交
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 1;
        }

        execCreate(conn,prst,"CREATE TABLE settings (" +
                "  key   TEXT PRIMARY KEY," +
                "  value TEXT" +
                ");");

        execCreate(conn, prst, "CREATE TABLE documents (" +
                "  id      INTEGER PRIMARY KEY," + /* auto increment */
                "  title   TEXT NOT NULL," +
                "  body    TEXT NOT NULL" +
                ");");

        execCreate(conn,prst, "CREATE TABLE tokens (" +
                "  id         INTEGER PRIMARY KEY," +
                "  token      TEXT NOT NULL," +
                "  docs_count INT NOT NULL," +
                "  postings   BLOB NOT NULL" +
                ");");

        execCreate(conn,prst,"CREATE UNIQUE INDEX token_index ON tokens(token);");
        execCreate(conn,prst,"CREATE UNIQUE INDEX title_index ON documents(title);");

        execPrepare(conn, env.get_document_id, "SELECT id FROM documents WHERE title = ?;");
        execPrepare(conn,env.get_document_title,"SELECT title FROM documents WHERE id = ?;");
        execPrepare(conn,env.insert_document,"INSERT INTO documents (title, body) VALUES (?, ?);");
        execPrepare(conn,env.update_document,"UPDATE documents set body = ? WHERE id = ?;");
        execPrepare(conn,env.get_token_id,"SELECT id, docs_count FROM tokens WHERE token = ?;");
        execPrepare(conn,env.get_token,"SELECT token FROM tokens WHERE id = ?;");
        execPrepare(conn,env.store_token,"INSERT OR IGNORE INTO tokens (token, docs_count, postings)");
        execPrepare(conn,env.get_postings, "SELECT docs_count, postings FROM tokens WHERE id = ?;");
        execPrepare(conn,env.update_postings, "UPDATE tokens SET docs_count = ?, postings = ? WHERE id = ?;");
        execPrepare(conn,env.get_settings,"SELECT value FROM settings WHERE key = ?;");
        execPrepare(conn,env.replace_settings, "INSERT OR REPLACE INTO settings (key, value) VALUES (?, ?);");
        execPrepare(conn,env.get_document_count, "SELECT COUNT(*) FROM documents;");

        return 0;
    }

    /**
     * 创建表
     * @param conn
     * @param prst
     * @param createSql
     */
    public void execCreate(Connection conn,PreparedStatement prst,String createSql){
        try {
            prst=conn.prepareStatement(createSql);
            prst.executeUpdate();
            prst.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 准备操作
     * @param conn
     * @param prst
     * @param createSql
     */
    public void execPrepare(Connection conn,PreparedStatement prst,String createSql){
        try {
            prst=conn.prepareStatement(createSql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
    }

    /**
     * 关闭数据库，
     * @param env 运行环境
     */
    public void close(GoSearchEnv env){
        try {
            env.get_document_id.close();
            env.get_document_title.close();
            env.insert_document.close();
            env.update_document.close();
            env.get_token_id.close();
            env.get_token.close();
            env.store_token.close();
            env.get_postings.close();
            env.update_postings.close();
            env.get_settings.close();
            env.replace_settings.close();
            env.get_document_count.close();
            env.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据指定的文档标题获得文档编号
     * @param env 运行环境
     * @param title 文档标题
     * @param size  文档标题的字节数
     * @return 文档编号
     */
    public int DBGetDocumentID(GoSearchEnv env, String title, int size){
        int id;
        id=env.getDocumentID(title);
        return id;
    }

    /**
     *根据指定的文档编号获取文档标题
     * @param env 运行环境
     * @param id 文档编号
     * @return 文档标题
     */
    public String DBGetDocumentTitle(GoSearchEnv env,int id){
        String title;
        title=env.getDocumentTittle(id);
        return title;
    }

    /**
     * 将文档添加到documents表中
     * @param env
     * @param title 文档标题
     * @param titleSize 文档标题的字节数
     * @param body 文档正文
     * @param bodySize 文档正文的字节数
     * @return
     */
    public boolean DBAddDocument(GoSearchEnv env,String title,int titleSize,String body,int bodySize){
        boolean rec;
        int document_id=DBGetDocumentID(env,title,titleSize);
        if(document_id!=0){
           rec=env.updateDocument(document_id,body);
        }else{
            rec=env.inseartDocument(title,body);
        }
        return rec;
    }



}
