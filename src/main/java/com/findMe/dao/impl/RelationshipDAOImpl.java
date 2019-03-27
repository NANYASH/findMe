package com.findMe.dao.impl;


import com.findMe.dao.RelationshipDAO;
import com.findMe.model.Relationship;
import com.findMe.model.enums.RelationshipStatus;
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

    private static final String UPDATE_RELATIONSHIP = "UPDATE RELATIONSHIP SET USER_FROM_ID = ?, USER_TO_ID = ?, STATUS = ?, LAST_UPDATE_DATE = ?" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String FIND_BY_RELATIONSHIP_STATUS = "SELECT  USER_TABLE.*" +
            " FROM USER_TABLE JOIN RELATIONSHIP ON USER_FROM_ID = USER_TABLE.ID OR USER_TO_ID = USER_TABLE.ID" +
            " WHERE  STATUS = ?" +
            " AND ((USER_FROM_ID = ? AND USER_TO_ID = user_table.id) OR (USER_TO_ID = ? AND USER_FROM_ID = user_table.id))";

    /**
     * FIND_BY_RELATIONSHIP_STATUS (second one)
     * SELECT  USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON USER_FROM_ID = USER_TABLE.ID OR USER_TO_ID = USER_TABLE.ID
     * WHERE USER_FROM_ID = ? OR USER_TO_ID = ?
     * GROUP BY (USER_TABLE.ID,STATUS)
     * HAVING   USER_TABLE.ID <> ? AND STATUS = ?
     */

    private static final String FIND_OUTGOING_REQUESTS = "SELECT DISTINCT USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON  USER_TABLE.ID = USER_TO_ID" +
            " WHERE USER_FROM_ID = ? AND STATUS = 'REQUESTED'";

    private static final String FIND_INCOMING_REQUESTS = "SELECT DISTINCT USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON  USER_TABLE.ID = USER_FROM_ID" +
            " WHERE USER_TO_ID = ? AND STATUS = 'REQUESTED'";

    private static final String COUNT_NUMBER_OF_RELATIONSHIPS_BY_STATUS = "SELECT COUNT(r) FROM User u,Relationship r " +
            " WHERE r.relationshipStatus = :status " +
            " AND ((r.relationshipId.userFromId = :userId AND r.relationshipId.userToId = u.id) OR (r.relationshipId.userToId = :userId AND r.relationshipId.userFromId = u.id))";


    private static final String COUNT_NUMBER_OF_OUTGOING_REQUESTS = "SELECT COUNT(r) FROM User u,Relationship r " +
            " WHERE r.relationshipStatus = 'REQUESTED' " +
            " AND r.relationshipId.userFromId = :userId AND r.relationshipId.userToId = u.id";


    @Override
    public void addRelationship(Relationship relationship) throws InternalServerError, BadRequestException {
        super.save(relationship);
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId, Relationship relationship) throws InternalServerError, BadRequestException {
        try {
            getEntityManager().createNativeQuery(UPDATE_RELATIONSHIP, Relationship.class)
                    .setParameter(1, userFromId)
                    .setParameter(2, userToId)
                    .setParameter(3, relationship.getRelationshipStatus().toString())
                    .setParameter(4, relationship.getLastUpdateDate())
                    .setParameter(5, relationship.getRelationshipId().getUserFromId())
                    .setParameter(6, relationship.getRelationshipId().getUserToId())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public Relationship getRelationship(Long userFromId, Long userToId) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_STATUS_BY_ID, Relationship.class)
                    .setParameter(1, userFromId)
                    .setParameter(2, userToId)
                    .setParameter(3, userToId)
                    .setParameter(4, userFromId);
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
            Query query = getEntityManager().createNativeQuery(FIND_BY_RELATIONSHIP_STATUS, User.class)
                    .setParameter(1, status.toString())
                    .setParameter(2, userId)
                    .setParameter(3, userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public List<User> findOutgoingRequests(Long userId) throws InternalServerError {
        return findRequested(userId, FIND_OUTGOING_REQUESTS);
    }

    @Override
    public List<User> findIncomingRequests(Long userId) throws InternalServerError {
        return findRequested(userId, FIND_INCOMING_REQUESTS);
    }

    @Override
    public Long getNumberOfRelationships(Long userId, RelationshipStatus status) throws InternalServerError {
        try {
            Query query = getEntityManager().createQuery(COUNT_NUMBER_OF_RELATIONSHIPS_BY_STATUS)
                    .setParameter("status", status)
                    .setParameter("userId", userId);
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public Long getNumberOfOutgoingRequests(Long userId) throws InternalServerError {
        try {
            Query query = getEntityManager().createQuery(COUNT_NUMBER_OF_OUTGOING_REQUESTS)
                    .setParameter("userId", userId);
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    Class<Relationship> getEntityClass() {
        return Relationship.class;
    }

    private List<User> findRequested(Long userId, String request) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(request, User.class)
                    .setParameter(1, userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }
}
