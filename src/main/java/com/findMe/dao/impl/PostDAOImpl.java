package com.findMe.dao.impl;


import com.findMe.dao.PostDAO;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.Relationship;
import com.findMe.model.User;
import com.findMe.model.enums.RelationshipStatus;
import com.findMe.model.viewData.PostFilterData;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

import static com.findMe.util.Util.convertToBoolean;

@Transactional
@Repository
public class PostDAOImpl extends GenericDAO<Post> implements PostDAO {

    private static final String SELECT_FRIENDS_PAGES_POSTS = "SELECT * FROM POST WHERE USER_PAGE_ID IN" +
            " (SELECT ID FROM USER_TABLE" +
            " JOIN RELATIONSHIP ON USER_TABLE.ID = RELATIONSHIP.USER_FROM_ID OR USER_TABLE.ID = RELATIONSHIP.USER_TO_ID" +
            " WHERE STATUS = 'ACCEPTED' AND ((USER_FROM_ID = ? AND USER_TO_ID = user_table.id) OR (USER_TO_ID = ? AND USER_FROM_ID = user_table.id))) " +
            " ORDER BY POST.DATE_POSTED";


    @Override
    public List<Post> findPosts(PostFilterData postFilterData) throws InternalServerError {
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Post> criteria = builder.createQuery(Post.class);
            Root<Post> root = criteria.from(Post.class);
            Predicate predicate = builder.conjunction();

            predicate = builder.and(predicate, builder.equal(root.get("userPagePosted").get("id"), postFilterData.getUserPageId()));

            if (postFilterData.getUserPostedId() != null)
                predicate = builder.and(predicate, builder.equal(root.get("userPosted").get("id"), postFilterData.getUserPostedId()));
            else if (convertToBoolean(postFilterData.getByFriends()).equals(true))
                predicate = builder.and(predicate, builder.notEqual(root.get("userPosted").get("id"), postFilterData.getUserPageId()));

            return getEntityManager().createQuery(criteria.select(root)
                    .where(predicate)).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public List<Post> findNews(Long userId) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(SELECT_FRIENDS_PAGES_POSTS, Post.class);
            query.setParameter(1, userId);
            query.setParameter(2, userId);
            return query.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }


     /*private static final String SELECT_FRIENDS_PAGES_POSTS2 = "SELECT p FROM Post p WHERE p.userPagePosted.id in" +
            " (SELECT User.id FROM Relationship, User WHERE Relationship.relationshipStatus = 'ACCEPTED' AND " +
            " ((Relationship.userToId = User.id AND Relationship.userFromId = :userId) OR" +
            " (Relationship.userFromId = User.id AND Relationship.userToId = :userId)))" +
            " AND p.userPagePosted.id not in :usersIds" +
            " ORDER BY p.datePosted";


      public List<Post> findPostsByFriendsPages(Long userId, List<Long> usersIds) throws InternalServerError {
        try {
            Relationship result = null;
            List<Post> resultList = getEntityManager().createQuery(SELECT_FRIENDS_PAGES_POSTS2, Post.class)
                    .setParameter("userId",userId)
                    .setParameter("usersIds", usersIds)
                    .getResultList();
            return resultList;
        }catch (Exception e){
            e.printStackTrace();
            throw new InternalServerError();
        }
    }*/


    @Override
    public List<User> findUsersTagged(Long userPostedId, Long[] usersTaggedIds) throws InternalServerError {
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> userRoot = criteria.from(User.class);
            Root<Relationship> relationshipRoot = criteria.from(Relationship.class);
            Predicate predicate = builder.conjunction();
            Expression<String> exp = userRoot.get("id");

            predicate = builder.and(predicate, builder.or(
                    builder.equal(userRoot.get("id"), relationshipRoot.get("relationshipId").get("userFromId")),
                    builder.equal(userRoot.get("id"), relationshipRoot.get("relationshipId").get("userToId"))));
            predicate = builder.and(predicate, builder.or(
                    builder.equal(relationshipRoot.get("relationshipId").get("userFromId"), userPostedId),
                    builder.equal(relationshipRoot.get("relationshipId").get("userToId"), userPostedId)));

            predicate = builder.and(predicate, builder.equal(relationshipRoot.get("relationshipStatus"), RelationshipStatus.ACCEPTED));
            predicate = builder.and(predicate, exp.in(usersTaggedIds));

            return getEntityManager().createQuery(criteria.select(userRoot)
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

}
