package com.globapp.globapp.data.local;

import com.globapp.globapp.data.local.dao.NewsDAO;
import com.globapp.globapp.data.services.INewsInserter;
import com.globapp.globapp.model.News;

public class NewsInserterLocal implements INewsInserter {
    private final NewsDAO newsDAO;

    public NewsInserterLocal(){
        newsDAO = LocalDB.getInstance().newsDAO();
    }

    @Override
    public void insert(News news) {
        newsDAO.insert(news);
    }
}
