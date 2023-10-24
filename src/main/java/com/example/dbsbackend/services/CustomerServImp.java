package com.example.dbsbackend.services;

import com.example.dbsbackend.entities.Customer;
import com.example.dbsbackend.exceptions.RecordNotFoundException;
import com.example.dbsbackend.repositories.CustomerRepository;
import com.example.dbsbackend.viewmodels.CustomerCreateViewModel;
import com.example.dbsbackend.viewmodels.CustomerUpdateModel;
import com.example.dbsbackend.viewmodels.CustomerViewModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServImp implements CustomerService{
    private final CustomerRepository repo;
    public CustomerServImp(CustomerRepository repo) {
        this.repo=repo;
    }

    @Override
    public List<CustomerViewModel> getAll()
    {
        return repo.findAll().stream().map(c->toViewModel(c)).collect(Collectors.toList());
    }

    @Override
    public CustomerViewModel getById(int id)
    {
        return toViewModel(getEntityById(id));
    }

    @Override
    public void deleteById(int id) {
        repo.delete(getEntityById(id));
    }

    @Override
    public CustomerViewModel create(CustomerCreateViewModel customer) {
        return toViewModel(repo.saveAndFlush(toEntity(customer)));
    }

    @Override
    public CustomerViewModel update(int id, CustomerUpdateModel customer) {
        return toViewModel(repo.saveAndFlush(toEntity(getEntityById(id),customer)));
    }

    private Customer getEntityById(int id)
    {
        return repo.findById(id)
                    .orElseThrow(
                            ()-> new RecordNotFoundException(
                                    String.format("Customer with id:%d is not found",id)
                            )
                    );
    }
    private CustomerViewModel toViewModel(Customer entity)
    {
        var viewModel=new CustomerViewModel();
        BeanUtils.copyProperties(entity, viewModel);
        return viewModel;
    }
    private Customer toEntity(CustomerCreateViewModel viewModel) {
        var entity = new Customer();
        BeanUtils.copyProperties(viewModel, entity);
        return entity;
    }

    private Customer toEntity(Customer entityDb, CustomerUpdateModel viewModel) {
        BeanUtils.copyProperties(viewModel, entityDb);
        return entityDb;
    }
}
