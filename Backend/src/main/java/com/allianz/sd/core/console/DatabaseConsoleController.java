package com.allianz.sd.core.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/database")
public class DatabaseConsoleController {

    @Autowired
    private EntityManager entityManager;


    @RequestMapping("/databaseStatus")
    public String testAllTableExist(Model model) {

        Map<String,Boolean> tableValidationMap = new LinkedHashMap<>();
        Map<String,Boolean> sequenceValidationMap = new LinkedHashMap<>();

        Set<EntityType<?>> entityTypeSet = entityManager.getMetamodel().getEntities();
        for(EntityType<?> entityType : entityTypeSet) {

            Class c = entityType.getJavaType();

            Annotation[] annotations = c.getDeclaredAnnotations();
            if(annotations != null) {
                for(Annotation annotation : annotations) {
                    if(annotation instanceof Table) {
                        Table table = (Table) annotation;
                        String tableName = table.name();
                        String sequenceName = null;
                        boolean tableSuccess = false;
                        boolean sequenceSuccess = false;

                        try{
                            entityManager.createNativeQuery("select count(*) from " + tableName).getSingleResult();
                            tableSuccess = true;
                        }catch(Exception e) {}

                        Field[] fields = c.getDeclaredFields();
                        for(Field field : fields) {
                            SequenceGenerator sequenceGenerator = field.getAnnotation(SequenceGenerator.class);
                            if(sequenceGenerator != null) {
                                sequenceName = sequenceGenerator.sequenceName();

                                try{
                                    entityManager.createNativeQuery("select "+sequenceName+".nextval from dual").getSingleResult();
                                    sequenceSuccess = true;
                                }catch(Exception e) {}

                            }
                        }

                        tableValidationMap.put(tableName,tableSuccess);
                        if(sequenceName != null) sequenceValidationMap.put(sequenceName,sequenceSuccess);
                    }
                }
            }
        }


        model.addAttribute("tableValidationMap",tableValidationMap);
        model.addAttribute("sequenceValidationMap",sequenceValidationMap);

        return "databaseStatus";
    }

    @RequestMapping("/showTables")
    public void showTables() {
        Set<EntityType<?>> entityTypeSet = entityManager.getMetamodel().getEntities();
        for(EntityType<?> entityType : entityTypeSet) {

            Class c = entityType.getJavaType();

            Annotation[] annotations = c.getDeclaredAnnotations();
            if(annotations != null) {
                for(Annotation annotation : annotations) {
                    if(annotation instanceof Table) {
                        Table table = (Table) annotation;
//                        System.out.println("Table name : "+table.name());

                        Field[] fields = c.getDeclaredFields();
                        for(Field field : fields) {
                            SequenceGenerator sequenceGenerator = field.getAnnotation(SequenceGenerator.class);
                            if(sequenceGenerator != null) {
//                                System.out.println("SequenceGenerator name : "+sequenceGenerator.sequenceName());
                            }
                        }
                    }

                }
            }


        }
    }

}
