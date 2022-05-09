package com.xendv.ReportLoader.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Component
public class CompanyInfoRepositoryImpl {//implements CustomCompanyInfoRepository {

    @PersistenceContext
    private EntityManager entityManager;

/*    @Override
    public Iterable<CompanyInfo> findAllByOkpo(BigDecimal okpo) {
        String hql = "SELECT e FROM CompanyInfo e where e.okpo = :okpo";
        TypedQuery<CompanyInfo> query = entityManager.createQuery(hql, CompanyInfo.class);
        query.setParameter("okpo", okpo);
        var result = query.getResultList();
        return result;
    }*/
}
