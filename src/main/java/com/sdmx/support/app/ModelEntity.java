package com.sdmx.support.app;

import com.sdmx.Application;
import com.sdmx.error.exception.FormValidationException;
import com.sdmx.error.exception.HttpException;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ModelEntity implements Application {

    private ModelEntityFactory factory;

    private Class classEntity;

    private CriteriaBuilder builder;

    public ModelEntity(ModelEntityFactory factory, Class classEntity) {
        this.factory = factory;
        this.classEntity = classEntity;
    }

    public ModelEntityFactory getFactory() {
        return factory;
    }

    /** SELECTION */

    public Object find(Object id) {
        EntityManager em = factory.em();
        Object object = em.find(classEntity, id);
        em.close();

        return object;
    }

    public Object find(Map<String, Object> param) {
        EntityManager em = factory.em();
        Object object = em.find(classEntity, param);
        em.close();

        return object;
    }

    public Object findOrFail(Object id) {
        Object entity = find(id);

        if (entity == null) {
            throw new HttpException(404);
        }

        return entity;
    }

    public List<?> all() {
        CriteriaQuery query = query();
        query.select(query.from(classEntity));

        EntityManager em = factory.em();
        List<?> result = em.createQuery(query).getResultList();
        em.close();

        return result;
    }

    /** DATA MANIPULATION */

    public Object create(Object model) {
        try {
            EntityManager em = factory.em();
            em.getTransaction().begin();

            em.persist(model);

            em.getTransaction().commit();
            em.close();

            return model;
        }
        catch (FormValidationException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object update(Object model) {
        try {
            EntityManager em = factory.em();
            em.getTransaction().begin();

            em.merge(model);

            em.getTransaction().commit();
            em.close();

            return model;
        }
        catch (FormValidationException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object save(Map<String, Object> value) {
        try {
            EntityManager em = factory.em();
            em.getTransaction().begin();

            Object model = fill(classEntity.newInstance(), value);

            em.persist(model);

            em.getTransaction().commit();
            em.close();

            return model;
        }
        catch (FormValidationException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object update(Object id, Map<String, Object> value) {
        try { return updateOrFail(id, value); } catch (HttpException e) { return null; }
    }

    public void delete(Object id) {
        try { deleteOrFail(id); } catch (HttpException e) { }
    }

    public Object updateOrFail(Object id, Map<String, Object> value) {
        Object entity = fill(findOrFail(id), value);

        EntityManager em = factory.em();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();

        return entity;
    }

    public void deleteOrFail(Object id) {
        Object entity = findOrFail(id);

        EntityManager em = factory.em();
        em.getTransaction().begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        em.getTransaction().commit();
        em.close();
    }

    /** HELPERS */

    public Object fill(Object entity, Map<String, Object> value) {
        Class<?> entityClass = entity.getClass();
        List<String> fillable = null;
        Map<String, String> rules = null;

        // Fetch Fillable
        try {
            Field fillableField = entityClass.getDeclaredField("fillable");
            fillableField.setAccessible(true);
            fillable = Arrays.asList((String[]) fillableField.get(entity));
        }
        catch (Exception e) { e.printStackTrace(); }

        // Fetch Rules
        try {
            Method rulesMethod = entityClass.getDeclaredMethod("rules");
            rulesMethod.setAccessible(true);
            rules = (Map<String, String>) rulesMethod.invoke(entity);
        }
        catch (Exception e) { e.printStackTrace(); }

        // Fill Attribute
        for (Field field : entityClass.getDeclaredFields()) {
            String fieldName = field.getName();
            field.setAccessible(true);

            // not listed in fillable
            // applied only if fillable declared
            if (fillable instanceof List && !fillable.contains(fieldName)) {
                continue;
            }

            try {
                // auto set updated time
                if (field.getType() == Date.class && fieldName == "updated") {
                    field.set(entity, new Date());
                }
                // set attribute
                else if (value.containsKey(fieldName)) {
                    String fieldRule;
                    Validator validator = factory.getValidator();

                    // fetch attribute rules
                    if (rules instanceof Map && rules.containsKey(fieldName)) {
                        fieldRule = rules.get(fieldName);
                    }
                    else {
                        fieldRule = StringUtils.join(validator.guessFieldRule(field), "|");
                    }

                    // validate value
                    validator.validateAttributeOrFail(fieldName, fieldRule, value.get(fieldName));

                    // set value
                    field.set(entity, value.get(fieldName));
                }
            }
            catch (Exception e) { e.printStackTrace(); }
        }

        return entity;
    }

    public EntityManager createEntityManager() {
        return getFactory().em();
    }

    public CriteriaBuilder builder() {
        if (builder == null) {
            builder = factory.getEntityManagerFactory().getCriteriaBuilder();
        }

        return builder;
    }

    public CriteriaQuery query() {
        return builder().createQuery(classEntity);
    }
}
