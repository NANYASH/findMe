package com.findMe.dao.impl;


import com.findMe.dao.RelationshipDAO;
import com.findMe.model.Relationship;
import com.findMe.model.RelationshipId;
import com.findMe.model.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import com.findMe.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class RelationshipDAOImpl extends GenericDAO<Relationship> implements RelationshipDAO {

    private static final String FIND_STATUS_BY_ID = "SELECT * FROM RELATIONSHIP" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ? OR USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String FIND_STATUS_BY_ID_FROM_TO = "SELECT * FROM RELATIONSHIP" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String UPDATE_RELATIONSHIP = "UPDATE RELATIONSHIP SET USER_FROM_ID = ?, USER_TO_ID = ?, STATUS = ?" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String FIND_BY_RELATIONSHIP_STATUS = "SELECT  USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON USER_FROM_ID = ? OR USER_TO_ID = ?" +
            " WHERE  USER_FROM_ID = USER_TABLE.ID OR USER_TO_ID = USER_TABLE.ID" +
            " GROUP BY (USER_TABLE.ID,STATUS)" +
            " HAVING   USER_TABLE.ID <> ? AND STATUS = ?";

    /**
     * FIND_BY_RELATIONSHIP_STATUS (second one)
     * SELECT  USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON USER_FROM_ID = ? OR USER_TO_ID = ?
     * WHERE  USER_FROM_ID = USER_TABLE.ID OR USER_TO_ID = USER_TABLE.ID
     * AND USER_TABLE.ID <> ? AND STATUS = ?
     * */

    private static final String FIND_REQUESTED_FROM = "SELECT DISTINCT USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON  USER_TABLE.ID = USER_TO_ID" +
            " WHERE USER_FROM_ID = ? AND STATUS = 'REQUESTED'";

    private static final String FIND_REQUESTED_TO = "SELECT DISTINCT USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON  USER_TABLE.ID = USER_FROM_ID" +
            " WHERE USER_TO_ID = ? AND STATUS = 'REQUESTED'";

    @Override
    public void addRelationship(Long userFromId, Long userToId) throws InternalServerError, BadRequestException {
        super.save(new Relationship(new RelationshipId(userFromId, userToId), RelationshipStatus.REQUESTED));
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId,Relationship relationship) throws InternalServerError, BadRequestException {
        try {
            Query query = getEntityManager().createNativeQuery(UPDATE_RELATIONSHIP, Relationship.class);
            query.setParameter(1, userFromId);
            query.setParameter(2, userToId);
            query.setParameter(3, relationship.getRelationshipStatus().toString());
            query.setParameter(4, relationship.getRelationshipId().getUserFromId());
            query.setParameter(5, relationship.getRelationshipId().getUserToId());
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public Relationship getRelationship(Long userFromId, Long userToId) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_STATUS_BY_ID, Relationship.class);
            query.setParameter(1, userFromId);
            query.setParameter(2, userToId);
            query.setParameter(3, userToId);
            query.setParameter(4, userFromId);
            return (Relationship) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public Relationship getRelationshipFromTo(Long userFromId, Long userToId) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_STATUS_BY_ID_FROM_TO, Relationship.class);
            query.setParameter(1, userFromId);
            query.setParameter(2, userToId);
            return (Relationship) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public List<User> findByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_BY_RELATIONSHIP_STATUS, User.class);
            query.setParameter(1, userId);
            query.setParameter(2, userId);
            query.setParameter(3, userId);
            query.setParameter(4, status.toString());
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public List<User> findRequestedFrom(Long userId) throws InternalServerError {
        return findRequested(userId, FIND_REQUESTED_FROM);
    }

    @Override
    public List<User> findRequestedTo(Long userId) throws InternalServerError {
        return findRequested(userId, FIND_REQUESTED_TO);
    }

    private List<User> findRequested(Long userId, String request) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(request, User.class);
            query.setParameter(1, userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    Class<Relationship> getEntityClass() {
        return Relationship.class;
    }
}
