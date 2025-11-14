CREATE TABLE department (
	id varchar(36) NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT department_pkey PRIMARY KEY (id)
);

CREATE TABLE class (
	id varchar(36) NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT class_pkey PRIMARY KEY (id)
);

CREATE TABLE subject (
	id varchar(36) NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT subject_pkey PRIMARY KEY (id)
);

CREATE TABLE class_subject (
	idclass varchar(36) NOT NULL,
	idsubject varchar(36) NOT NULL,
	CONSTRAINT class_subject_pkey PRIMARY KEY (idclass, idsubject)
);

CREATE TABLE schedule (
    id VARCHAR(36) NOT NULL,
    idclass VARCHAR(36) NOT NULL,   
    idsubject VARCHAR(36) NOT NULL,   
    idmember VARCHAR(36) NOT NULL,   
    
    day_of_week INTEGER NOT NULL,  
    start_time TIME NOT NULL,     
    end_time TIME NOT NULL,     
    
    recurrence_rule VARCHAR(255) NULL, 
    start_date DATE NOT NULL,     
    end_date DATE NULL,         
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT schedule_pkey PRIMARY KEY (id),
    CONSTRAINT fk_schedule_class   FOREIGN KEY (idclass) REFERENCES class(id),
    CONSTRAINT fk_schedule_subject FOREIGN KEY (idsubject) REFERENCES subject(id),
    CONSTRAINT fk_schedule_teacher FOREIGN KEY (idmember) REFERENCES member(id)
);
