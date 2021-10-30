package hello.hellospring.domain;

import javax.persistence.*;

@Entity
public class Member { //alt+insert->generate를 활용해서 getter, setter를 만들 수 있다

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
