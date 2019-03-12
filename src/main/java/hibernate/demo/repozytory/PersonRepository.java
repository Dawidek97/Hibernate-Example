package hibernate.demo.repozytory;

import hibernate.demo.model.Person;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("SELECT p from Person p order by p .id")
    List<Person> findAllSortById();

    @Query("SELECT p from Person p where p.name=?1 OR p.age=?2")
    List<Person> findAllSortNameOrAge(String name, Integer age);

}
