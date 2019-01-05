package com.findMe.dao.impl;


import com.findMe.dao.FriendsDAO;
import com.findMe.entity.Relationship;
import com.findMe.entity.RelationshipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.exception.InternalServerError;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import static com.findMe.dao.impl.FriendsValidator.validateDelete;
import static com.findMe.dao.impl.FriendsValidator.validateReject;
import static com.findMe.dao.impl.FriendsValidator.validateUpdate;

@Repository
@Transactional
public class FriendsDAOImpl extends GenericDAO<Relationship> implements FriendsDAO {
    private static final String FIND_STATUS_BY_ID = "SELECT * FROM RELATIONSHIP" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ? OR USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String FIND_STATUS_BY_ID_FROM_TO = "SELECT * FROM RELATIONSHIP" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ?";


    @Override
    public void addRelationship(Long userFromId, Long userToId) throws InternalServerError, BadRequestException {
        Relationship status = getRelationship(userFromId, userToId);
        if (status == null)
            super.create(new Relationship(userFromId, userToId, RelationshipStatus.REQUESTED));
        else if (status.getRelationshipStatus() == RelationshipStatus.NOT_FRIENDS) {
            status.setUserFromId(userFromId);
            status.setUserToId(userToId);
            status.setRelationshipStatus(RelationshipStatus.REQUESTED);
            super.update(status);
        } else throw new BadRequestException("Action cannot be performed for this user.");
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws InternalServerError, BadRequestException {
        Relationship relationship = getRelationshipFromTo(userFromId, userToId);
        validateUpdate(relationship, status);
        relationship.setRelationshipStatus(status);
        super.update(relationship);
    }

    @Override
    public void deleteRelationship(Long userFromId, Long userToId) throws InternalServerError, BadRequestException {
        Relationship relationship = getRelationship(userFromId, userToId);
        validateDelete(relationship);
        relationship.setRelationshipStatus(RelationshipStatus.NOT_FRIENDS);
        super.update(relationship);
    }

    @Override
    public void rejectRequest(Long userFromId, Long userToId) throws InternalServerError, BadRequestException {
        Relationship relationship = getRelationshipFromTo(userFromId, userToId);
        validateReject(relationship);
        relationship.setRelationshipStatus(RelationshipStatus.NOT_FRIENDS);
        super.update(relationship);
    }

    @Override
    Class<Relationship> getEntityClass() {
        return Relationship.class;
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

    private Relationship getRelationshipFromTo(Long userFromId, Long userToId) throws InternalServerError {
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

}
