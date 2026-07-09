package br.com.automated_tests_with_java.repository;

import br.com.automated_tests_with_java.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.automated_tests_with_java.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PersonRepository repository;


    private Person person0;

    @BeforeEach  // faz com que esse seja executado antes de todos os testes
    public void setup(){
        //Given/Arrage
       person0= new Person(
               "Guilherme",
               "Barbosa",
               "gs@gmail.com",
               "Recife",
               "Male");

    }

    @DisplayName("JUnit test for Given Person object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_WhenSave_thenReturnSavedPerson(){

        //when/act   - ação do teste (salvar uma pessoa)
        Person savedPerson= repository.save(person0);

        //then/assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId()> 0); //testa se o id vai ser maior que 0
    }

    @DisplayName("JUnit test for Given Person List when findAll then Return Person List")
    @Test
    void testGivenPersonObject_WhenfindAll_thenReturndPersonList(){
        //Given/Arrage
        Person person1= new Person("Geovane","Barbosa","gb@gmail.com","Recife","Male");

        repository.save(person0);
        repository.save(person1);

        //when/act   - ação do teste (retornar as pessoas salvas)
        List<Person> personList=repository.findAll();


        //then/assert
        assertNotNull(personList);  // testa se não está nulo
        assertEquals(2,personList.size()); //testa se o tamanho da lista é 2
    }

    @DisplayName("JUnit test for Given Person object when findByID then Return Person object")
    @Test
    void testGivenPersonObject_WhenfindByID_thenReturnSavedPerson(){
        //Given/Arrage
        repository.save(person0);

        //when/act   - ação do teste (retornar uma pessoa pelo ID)
        Person savedPerson= repository.findById(person0.getId()).get();


        //then/assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(),savedPerson.getId()); //compara os gets
    }

    @DisplayName("JUnit test for Given Person object when findByEmail then Return Person object")
    @Test
    void testGivenPersonObject_WhenfindByEmail_thenReturnSavedPerson(){
        //Given/Arrage
        repository.save(person0);

        //when/act   - ação do teste (retornar uma pessoa pelo Email)
        Person savedPerson= repository.findByEmail(person0.getEmail()).get();


        //then/assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(),savedPerson.getId()); //compara os gets
    }
    @DisplayName("JUnit test for Given Person object when update person then Return update person object")
    @Test
    void testGivenPersonObject_WhenUpdate_thenReturnUpdatePersonObject(){
        //Given/Arrage
        repository.save(person0);

        //when/act   - ação do teste (retornar uma pessoa pelo ID)
        Person savedPerson= repository.findById(person0.getId()).get();
        savedPerson.setFirstName("George");
        savedPerson.setEmail("geo@gmail.com");

        Person updatedPerson= repository.save(savedPerson);


        //then/assert
        assertNotNull(updatedPerson);
        assertEquals("George",updatedPerson.getFirstName()); //compara os gets de nome
        assertEquals("geo@gmail.com",updatedPerson.getEmail()); //compara os gets de email
    }

    @DisplayName("JUnit test for Given Person object when Delete then Remove person")
    @Test
    void testGivenPersonObject_WhenDelete_thenRemovePerson(){
        //Given/Arrage
        repository.save(person0);

        //when/act   - ação do teste (deletar pessoa pelo id)
        repository.deleteById(person0.getId());

        Optional<Person> personOptional= repository.findById(person0.getId());


        //then/assert
        assertTrue(personOptional.isEmpty()); //verifica se está vazio/se realmente foi deletado
    }
    @DisplayName("JUnit test for Given firstName and lastName when findJPQL then Return Person")
    @Test
    void testGivenFirstNameAndLastName_WhenfindByJPQL_thenReturnPersonObject(){
        //Given/Arrage

        repository.save(person0);
        String firstName = "Guilherme";
        String lastName = "Barbosa";

        //when/act   - ação do teste (retornar uma pessoa pelo ID)

        Person savedPerson= repository.findByJPQL(firstName, lastName);


        //then/assert
        assertNotNull(savedPerson);
        assertEquals(firstName,savedPerson.getFirstName()); //compara o nome principal
        assertEquals(lastName,savedPerson.getLastName()); //compara o sobrenome
    }

}