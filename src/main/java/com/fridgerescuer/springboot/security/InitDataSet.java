package com.fridgerescuer.springboot.security;

import com.fridgerescuer.springboot.security.entity.Authority;
import com.fridgerescuer.springboot.security.entity.User;
import com.fridgerescuer.springboot.security.repository.AuthorityRepository;
import com.fridgerescuer.springboot.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class InitDataSet {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private MongoTemplate template;

    public void setData(){
        User admin = User.builder().username("admin").
                password("$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi").
                nickname("admin").activated(true).build();
        User user = User.builder().username("user").
                password("$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC").
                nickname("user").activated(true).build();

        User savedAdmin = userRepository.save(admin);
        User savedUser = userRepository.save(user);

        Authority role_user = Authority.builder().authorityName("ROLE_USER").build();
        Authority role_admin = Authority.builder().authorityName("ROLE_ADMIN").build();

        Authority adminAuth = authorityRepository.save(role_admin);
        Authority userAuth = authorityRepository.save(role_user);


        setReference(savedAdmin.getId(),adminAuth);
        setReference(savedAdmin.getId(),userAuth);
        setReference(savedUser.getId(),userAuth);
    }

    private void setReference(String userId ,Authority auth){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));

        Update update = new Update();
        update.push("authorities",auth);

        template.updateMulti(query, update, User.class);

    }
}
