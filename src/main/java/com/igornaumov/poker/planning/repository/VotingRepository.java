package com.igornaumov.poker.planning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igornaumov.poker.planning.entity.VoteEntity;
import com.igornaumov.poker.planning.entity.VoteId;

@Repository
public interface VotingRepository extends JpaRepository<VoteEntity, VoteId> {

}
