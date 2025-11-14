package com.schoolplus.back.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSummary {
    private String id;
    private String name;
    private MemberType type;    

    public MemberSummary(Member m) {
        if (m == null) return;
        this.id = m.getId();
        this.name = m.getName();
        this.type = m.getType();
    }
}
