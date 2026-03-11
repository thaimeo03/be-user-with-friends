package com.tht.be_user_with_friends.user.entity;

import com.tht.be_user_with_friends.common.entity.BaseEntity;
import com.tht.be_user_with_friends.common.enums.FriendShipStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "friendships", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "requester_id",
                "receiver_id"
        })
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendShip extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friendships_seq")
    @SequenceGenerator(name = "friendships_seq", sequenceName = "friendships_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "requester_id", nullable = false)
    private Long requesterId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "status", length = 50, nullable = false)
    @Builder.Default
    private FriendShipStatus status = FriendShipStatus.PENDING;
}
