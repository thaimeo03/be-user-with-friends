package com.tht.be_user_with_friends.notification.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_seq")
  @SequenceGenerator(name = "notifications_seq", sequenceName = "notifications_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "requester_id", nullable = false)
  private Long requesterId;

  @Column(name = "receiver_id", nullable = false)
  private Long receiverId;

  @Column(name = "message", length = 255)
  private String message;

  @Column(name = "is_read", nullable = false)
  @Builder.Default
  private boolean isRead = false;

  @Column(name = "read_at")
  private LocalDateTime readAt;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;
}
