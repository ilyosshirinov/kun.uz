package com.example.service;

import com.example.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository typeRepository;

}
