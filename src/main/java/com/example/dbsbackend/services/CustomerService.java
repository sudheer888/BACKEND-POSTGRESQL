package com.example.dbsbackend.services;

import com.example.dbsbackend.viewmodels.CustomerCreateViewModel;
import com.example.dbsbackend.viewmodels.CustomerUpdateModel;
import com.example.dbsbackend.viewmodels.CustomerViewModel;

import java.util.List;

public interface CustomerService {
    List<CustomerViewModel> getAll();
    CustomerViewModel getById(int id);
    void deleteById(int id);
    CustomerViewModel create(CustomerCreateViewModel customer);
    CustomerViewModel update(int id, CustomerUpdateModel customer);

}
