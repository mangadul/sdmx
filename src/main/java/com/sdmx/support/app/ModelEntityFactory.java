package com.sdmx.support.app;

import com.sdmx.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
public class ModelEntityFactory implements Application {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private Validator validator;

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public Validator getValidator() {
        return validator;
    }

    public EntityManager em() {
        return entityManagerFactory.createEntityManager();
    }

    public ModelEntity create(Class classEntity) {
        return new ModelEntity(this, classEntity);
    }
}
