package com.schoolplus.back.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
  @Column(name = "create_at")
  private Timestamp createAt;

  @Column(name = "update_at")
  private Timestamp updateAt;

  @PrePersist
  protected void onCreate() {
    this.createAt = new Timestamp(System.currentTimeMillis());
    this.updateAt = this.createAt;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updateAt = new Timestamp(System.currentTimeMillis());
  }
}
