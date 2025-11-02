package com.ait.mrbfp.repository;

import com.ait.mrbfp.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, String> {
    boolean existsByRoomName(String roomName);
}
