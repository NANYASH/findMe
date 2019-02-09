package com.findMe.dao.impl;


import com.findMe.dao.PostDAO;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class PostDAOImpl extends GenericDAO<Post> implements PostDAO {

    private static final String FIND_BY_USER_ID = "SELECT * FROM POST WHERE USER_POSTED_ID = ?" +
            " ORDER BY DATE_POSTED DESC";
    private static final String FIND_BY_USER_PAGE_ID = "SELECT * FROM POST WHERE USER_PAGE_ID = ?" +
            " ORDER BY DATE_POSTED DESC";
    private static final String FIND_BY_USER_PAGE_ID_WITHOUT_OWNER = "SELECT * FROM POST" +
            " WHERE USER_PAGE_ID = ? AND USER_PAGE_ID <> USER_POSTED_ID" +
            " ORDER BY DATE_POSTED DESC;";

    @Override
    public List<Post> findByUserId(Long id) throws InternalServerError {
        return findById(id, FIND_BY_USER_ID);
    }

    @Override
    public List<Post> findByUserPageId(Long id) throws InternalServerError {
        return findById(id, FIND_BY_USER_PAGE_ID);
    }

    @Override
    public List<Post> findByUserPageIdWithoutOwner(Long id) throws InternalServerError {
        return findById(id, FIND_BY_USER_PAGE_ID_WITHOUT_OWNER);
    }

    @Override
    public List<User> findUsersTagged(Long[] usersTaggedIds) throws InternalServerError {
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            Expression<String> exp = root.get("id");
            Predicate predicate = exp.in(usersTaggedIds);
            return getEntityManager().createQuery(criteria.select(root)
                    .where(predicate)).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    Class<Post> getEntityClass() {
        return Post.class;
    }


    private List<Post> findById(Long id, String request) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(request, Post.class);
            query.setParameter(1, id);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

}
