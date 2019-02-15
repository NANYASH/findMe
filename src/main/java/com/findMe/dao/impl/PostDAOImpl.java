package com.findMe.dao.impl;


import com.findMe.dao.PostDAO;
import com.findMe.exception.InternalServerError;
import com.findMe.model.Post;
import com.findMe.model.Relationship;
import com.findMe.model.User;
import com.findMe.model.enums.RelationshipStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

import static com.findMe.util.Util.convertId;
import static com.findMe.util.Util.convertToBoolean;

@Transactional
@Repository
public class PostDAOImpl extends GenericDAO<Post> implements PostDAO {

    @Override
    public List<Post> findPosts(Long userPageId, String userPostedId, String byFriends) throws InternalServerError {
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Post> criteria = builder.createQuery(Post.class);
            Root<Post> root = criteria.from(Post.class);
            Predicate predicate = builder.conjunction();

            predicate = builder.and(predicate, builder.equal(root.get("userPagePosted").get("id"), userPageId));

            if (userPostedId != null && !userPostedId.isEmpty())
                predicate = builder.and(predicate, builder.equal(root.get("userPosted").get("id"), convertId(userPostedId)));
            else if (convertToBoolean(byFriends).equals(true))
                predicate = builder.and(predicate, builder.notEqual(root.get("userPosted").get("id"), userPageId));

            return getEntityManager().createQuery(criteria.select(root)
                    .where(predicate)).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public List<User> findUsersTagged(Long userPostedId, Long[] usersTaggedIds) throws InternalServerError {
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<User> criteria = builder.createQuery(User.class);
            Root<User> userRoot = criteria.from(User.class);
            Root<Relationship> relationshipRoot = criteria.from(Relationship.class);
            Predicate predicate = builder.conjunction();
            Expression<String> exp = userRoot.get("id");

            predicate = builder.and(predicate,builder.or(
                    builder.equal(userRoot.get("id"),relationshipRoot.get("relationshipId").get("userFromId")),
                    builder.equal(userRoot.get("id"),relationshipRoot.get("relationshipId").get("userToId"))));
            predicate = builder.and(predicate,builder.or(
                    builder.equal(relationshipRoot.get("relationshipId").get("userFromId"),userPostedId),
                    builder.equal(relationshipRoot.get("relationshipId").get("userToId"),userPostedId)));

            predicate = builder.and(predicate,builder.equal(relationshipRoot.get("relationshipStatus"), RelationshipStatus.ACCEPTED));
            predicate = builder.and(predicate,exp.in(usersTaggedIds));

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
