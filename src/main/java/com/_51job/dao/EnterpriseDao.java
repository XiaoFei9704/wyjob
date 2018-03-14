package com._51job.dao;

        import org.hibernate.SessionFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Repository;

@Repository
public class EnterpriseDao extends MyDao {

    @Autowired
    public EnterpriseDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}

