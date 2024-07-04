package com.n16.qltv.service;

import com.n16.qltv.daos.PublisherDAO;
import com.n16.qltv.facade.DaoFacade;
import com.n16.qltv.model.Customer;
import com.n16.qltv.model.Publisher;

import java.util.ArrayList;

public class PublisherService extends PublisherDAO {
    private ArrayList<Publisher> publisherArrayList ;
    private DaoFacade daoFacade = new DaoFacade();
    public PublisherService (ArrayList<Publisher> publisherArrayList){
        this.publisherArrayList = publisherArrayList;
    }

    public synchronized ArrayList<Publisher> findPublisherByName(String keyword) {
        ArrayList<Publisher> foundPublishers = new ArrayList<>();
        try {
            this.publisherArrayList = getListItem();
            for(Publisher item : this.publisherArrayList) {
                if(item.getPublisherName().trim().toLowerCase().contains(keyword)) {
                    foundPublishers.add(item);
                }
            }
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        return foundPublishers;
    }

    public boolean checkExistPublisher(String name) {
        boolean check = false;
        publisherArrayList = daoFacade.publisherDAO.getListItem();
        for (Publisher publisher : publisherArrayList)
        {
            if(publisher.getPublisherName().equals(name.trim()))
                check = true;
        }
            return check;
    }
}
