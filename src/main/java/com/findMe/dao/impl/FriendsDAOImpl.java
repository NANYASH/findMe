package com.findMe.dao.impl;


import com.findMe.dao.FriendsDAO;
import com.findMe.entity.RelationShipStatus;
import com.findMe.exception.BadRequestException;
import com.findMe.model.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class FriendsDAOImpl extends GenericDAO<User> implements FriendsDAO{

    private static final String  FIND_FRIENDS_BY_STATUS = "SELECT DISTINCT USER_TABLE.* FROM USER_TABLE JOIN RELATIONSHIP ON USER_FROM_ID = 1 OR USER_TO_ID = 1" +
            " WHERE STATUS = 'ACCEPTED';";

    private static final String FIND_STATUS_BY_ID = "SELECT STATUS FROM RELATIONSHIP" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ? OR USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String ADD_RELATIONSHIP = "INSERT INTO RELATIONSHIP (user_from_id, user_to_id, status) VALUES (?,?,'REQUESTED')";

    private static final String DELETE_RELATIONSHIP = "DELETE FROM RELATIONSHIP  WHERE USER_FROM_ID = ? AND USER_TO_ID = ? OR USER_FROM_ID = ? AND USER_TO_ID = ?";

    private static final String UPDATE_RELATIONSHIP = "UPDATE RELATIONSHIP SET STATUS = ?" +
            " WHERE USER_FROM_ID = ? AND USER_TO_ID = ? OR USER_FROM_ID = ? AND USER_TO_ID = ?";

    @Override
    public List<User> findFriendsByRelationshipStatus(Long userId, RelationShipStatus status) {
        return getEntityManager().createNativeQuery(FIND_FRIENDS_BY_STATUS).getResultList();
    }

    @Override
    public RelationShipStatus checkRelationship(Long userFromId, Long userToId) throws BadRequestException {
        Query query = getEntityManager().createNativeQuery(FIND_STATUS_BY_ID);
        query.setParameter(1, userFromId);
        query.setParameter(2, userToId);
        query.setParameter(3, userToId);
        query.setParameter(4, userFromId);

        try {
            return RelationShipStatus.valueOf(query.getSingleResult().toString());
        }catch (NoResultException e){
            throw new BadRequestException("No relationship between users.");
        }
    }

    @Override
    public void addRelationship(Long userFromId, Long userToId) {
        Query query = getEntityManager().createNativeQuery(ADD_RELATIONSHIP);
        query.setParameter(1,userFromId);
        query.setParameter(2,userToId);
        query.executeUpdate();
    }

    @Override
    public void deleteRelationship(Long userFromId, Long userToId) {
        Query query = getEntityManager().createNativeQuery(DELETE_RELATIONSHIP);
        query.setParameter(1, userFromId);
        query.setParameter(2, userToId);
        query.setParameter(3, userToId);
        query.setParameter(4, userFromId);
        query.executeUpdate();
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId,RelationShipStatus status) {
        Query query = getEntityManager().createNativeQuery(UPDATE_RELATIONSHIP);
        query.setParameter(1,status.toString());
        query.setParameter(2,userFromId);
        query.setParameter(2,userToId);
        query.executeUpdate();
    }

    @Override
    Class<User> getEntityClass() {
        return User.class;
    }
}
