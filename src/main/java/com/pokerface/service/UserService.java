package com.pokerface.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.aspectj.weaver.loadtime.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pokerface.model.Exchange;
import com.pokerface.model.User;
import com.pokerface.repos.ExchangeRepo;
import com.pokerface.repos.UserRepository;

@Component
public class UserService {
	@Autowired
    private ExchangeRepo repository;
	
	@Autowired
    private UserRepository userRepo;
	
    @PersistenceContext
    private EntityManager em;

    public List<Exchange> findExchange(String loginId) {
    	Query query = em
                .createQuery("From Exchange ex WHERE ex.phone = :loginId and ex.endTime is null order by ex.createTime desc");
    	query.setParameter("loginId", loginId);
    	@SuppressWarnings("unchecked")
		List<Exchange> list = query.getResultList();
    	return list;
    }
    
    public List<Exchange> findAll() {
    	return repository.findAll();
    }
    
    public List<Exchange> findValidExgs() {
    	Query query = em
                .createQuery("From Exchange ex WHERE ex.endTime is null order by ex.createTime desc");
    	@SuppressWarnings("unchecked")
		List<Exchange> list = query.getResultList();
    	return list;
    }
    
    public void endExchange(Long id) {
    	EntityManagerFactory entityManagerFactory = em.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
    	StringBuffer sb = new StringBuffer("Update Exchange ex set ex.endTime = :now where ex.id = :id");
    	Query query = entityManager.createQuery(sb.toString());
    	query.setParameter("now", new Date());
    	query.setParameter("id", id);
    	EntityTransaction tx = entityManager.getTransaction();
    	tx.begin();
    	query.executeUpdate();
    	tx.commit();
    }
    
    public void saveExg(Exchange exg) {
        repository.save(exg);
    }
    
    public void saveUser(User user) {
        userRepo.save(user);
    }
    
    public boolean phoneExists(String phone){
    	Query query = em
                .createQuery("From User user WHERE user.phone = :phone");
    	query.setParameter("phone", phone);
    	@SuppressWarnings("unchecked")
		List<Agent> list = query.getResultList();
    	return list!=null && list.size()>0;
    }
    
    public User findUser(String loginId){
    	Query query = em
                .createQuery("From User user WHERE user.phone = :loginId");
    	query.setParameter("loginId", loginId);
    	@SuppressWarnings("unchecked")
		List<User> list = query.getResultList();
    	if(list == null || list.size() == 0){
    		return null;
    	} else {
    		return list.get(0);
    	}
    }
    
    public void changePwd(Long id, String newPwd){
    	EntityManagerFactory entityManagerFactory = em.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
    	StringBuffer sb = new StringBuffer("Update User user set user.pwd = :newPwd where user.id = :id");
    	Query query = entityManager.createQuery(sb.toString());
    	query.setParameter("newPwd", newPwd);
    	query.setParameter("id", id);
    	EntityTransaction tx = entityManager.getTransaction();
    	tx.begin();
    	query.executeUpdate();
    	tx.commit();
    }
    
    public void changeMy(Long id, String name, String wechat){
    	EntityManagerFactory entityManagerFactory = em.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
    	StringBuffer sb = new StringBuffer("Update User user set user.name = :name, user.weChat = :wechat where user.id = :id");
    	Query query = entityManager.createQuery(sb.toString());
    	query.setParameter("name", name);
    	query.setParameter("wechat", wechat);
    	query.setParameter("id", id);
    	EntityTransaction tx = entityManager.getTransaction();
    	tx.begin();
    	query.executeUpdate();
    	tx.commit();
    }
}
