package com.sy.searchengine.util;

import com.sy.searchengine.database.DataBase;
import com.sy.searchengine.invertedlist.GoSearchEnv;

/**
 * Created by yanshi on 2017/6/21.
 */
public class GoSearch {
    /**
     * 为文档标题及正文构建倒排索引和用于存储文档的数据库
     * 处理：1.取出词元
     * 2.为每个词建立倒排表，并更新小倒排索引
     * 3.当小倒排索引增长到一定大小，就将其与存储器上的倒排索引进行合并
     * 当文档添加到数据库中，建立倒排索引
     * @param[in] env 存储着应用程序运行环境的结构体
     * @param[in] title 文档标题，为NULL时将会清空缓冲区
     * @param[in] body 文档正文
     */
    public void addDocument(GoSearchEnv env,String title,String body){
        if(title!=null && body!=null){
            String utf32_body;
            int body32_len,document_id;
            int title_size,body_size;
            title_size=title.length();
            body_size=body.length();
            //将文档存储到数据库中并获取该文档对应的文档编号
            DataBase db=new DataBase();
            db.DBAddDocument(env, title, title_size, body, body_size);
            document_id=db.DBGetDocumentID(env, title, title_size);
            //转换文档正文的字符编码
            Util util=new Util();
            utf32_body=util.utf8_to_utf32(body);
            if(utf32_body!=null){
                //为文档创建倒排列表

            }
        }
    }
}




















