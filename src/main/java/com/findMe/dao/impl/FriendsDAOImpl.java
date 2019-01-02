package com.findMe.dao.impl;


import com.findMe.dao.FriendsDAO;
import com.findMe.entity.RelationshipStatus;
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
public class FriendsDAOImpl extends GenericDAO<User> implements FriendsDAO {
    private static final String FIND_STATUS_BY_ID = "SELECT STATUS FROM RELATIONSHIP" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ? OR USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String FIND_STATUS_BY_ID_FROM_TO = "SELECT STATUS FROM RELATIONSHIP" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String FIND_BY_RELATIONSHIP_STATUS = "SELECT  USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON USER_FROM_ID = ? OR USER_TO_ID = ?" +
            " WHERE  USER_FROM_ID = ID OR USER_TO_ID = ID" +
            " GROUP BY (ID,STATUS)" +
            " HAVING  ID <> ? AND STATUS = ?";

    private static final String FIND_REQUESTED_FROM = "SELECT DISTINCT USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON ID = USER_TO_ID" +
            " WHERE USER_FROM_ID = ? AND STATUS = ?";

    private static final String FIND_REQUESTED_TO = "SELECT DISTINCT USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON ID = USER_FROM_ID" +
            " WHERE USER_TO_ID = ? AND STATUS = ?";

    private static final String ADD_RELATIONSHIP = "INSERT INTO RELATIONSHIP (user_from_id, user_to_id, status) VALUES (?,?,'REQUESTED')";

    private static final String DELETE_RELATIONSHIP = "DELETE FROM RELATIONSHIP  WHERE USER_FROM_ID = ? AND USER_TO_ID = ? OR USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String UPDATE_RELATIONSHIP = "UPDATE RELATIONSHIP SET STATUS = ?" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ?";

    @Override
    public List<User> findByRelationshipStatus(Long userId, RelationshipStatus status) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_BY_RELATIONSHIP_STATUS,User.class);
            query.setParameter(1,userId);
            query.setParameter(2,userId);
            query.setParameter(3,userId);
            query.setParameter(4,status.toString());
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public List<User> findRequestedFrom(Long userId, RelationshipStatus status) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_REQUESTED_FROM,User.class);
            query.setParameter(1,userId);
            query.setParameter(2,status.toString());
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public List<User> findRequestedTo(Long userId, RelationshipStatus status) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_REQUESTED_TO,User.class);
            query.setParameter(1,userId);
            query.setParameter(2,status.toString());
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public void addRelationship(Long userFromId, Long userToId) throws InternalServerError, BadRequestException {
        if (getRelationship(userFromId,userToId) != null)
          throw new BadRequestException("Users already have relationship.");
        try {
            Query query = getEntityManager().createNativeQuery(ADD_RELATIONSHIP);
            query.setParameter(1, userFromId);
            query.setParameter(2, userToId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws InternalServerError, BadRequestException {
        validateUpdate(getRelationshipFromTo(userFromId,userToId),status);
        try {
            Query query = getEntityManager().createNativeQuery(UPDATE_RELATIONSHIP);
            query.setParameter(1, status.toString());
            query.setParameter(2, userFromId);
            query.setParameter(3, userToId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    @Override
    public void deleteRelationship(Long userFromId, Long userToId) throws InternalServerError, BadRequestException {
        if (getRelationship(userFromId,userToId) != RelationshipStatus.ACCEPTED)
            throw new BadRequestException("Users are not friends.");
        delete(userFromId,userToId);
    }

    @Override
    public void rejectRequest(Long userFromId, Long userToId) throws InternalServerError, BadRequestException {
        if (getRelationshipFromTo(userFromId,userToId) != RelationshipStatus.REQUESTED)
            throw new BadRequestException("No requests to this user.");
        delete(userFromId,userToId);
    }

    @Override
    Class<User> getEntityClass() {
        return User.class;
    }

    private RelationshipStatus getRelationship(Long userFromId, Long userToId) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_STATUS_BY_ID);
            query.setParameter(1, userFromId);
            query.setParameter(2, userToId);
            query.setParameter(3,userToId);
            query.setParameter(4,userFromId);
            return RelationshipStatus.valueOf(query.getSingleResult().toString());
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }


    private RelationshipStatus getRelationshipFromTo(Long userFromId, Long userToId) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(FIND_STATUS_BY_ID_FROM_TO);
            query.setParameter(1, userFromId);
            query.setParameter(2, userToId);
            return RelationshipStatus.valueOf(query.getSingleResult().toString());
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    private void delete(Long userFromId, Long userToId) throws InternalServerError {
        try {
            Query query = getEntityManager().createNativeQuery(DELETE_RELATIONSHIP);
            query.setParameter(1, userFromId);
            query.setParameter(2, userToId);
            query.setParameter(3, userToId);
            query.setParameter(4, userFromId);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError();
        }
    }

    private void validateUpdate(RelationshipStatus currentStatus, RelationshipStatus newStatus) throws BadRequestException {
        if (currentStatus == null)
            throw new BadRequestException("No requests from this user.");

        if (currentStatus == RelationshipStatus.REQUESTED && newStatus == RelationshipStatus.ACCEPTED)
            return;
        if (currentStatus == RelationshipStatus.REQUESTED && newStatus == RelationshipStatus.REJECTED)
            return;
        if (currentStatus == RelationshipStatus.REJECTED && newStatus == RelationshipStatus.REQUESTED)
            return;

        throw new BadRequestException("Action cannot be performed for this user.");
    }

}
