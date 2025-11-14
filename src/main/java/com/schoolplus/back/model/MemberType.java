package com.schoolplus.back.model;

public enum MemberType {
  Undefined, Student, Teacher, Principal, Customer, Supplier;

  public static MemberType fromCode(String code) {
    for (MemberType type : values()) {
      if (type.toString().equals(code))
        return type;
    }
    return Undefined;
  }
}
