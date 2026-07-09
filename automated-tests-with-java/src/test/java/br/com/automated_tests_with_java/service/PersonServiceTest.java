package br.com.automated_tests_with_java.service;
import br.com.automated_tests_with_java.ResourceNotFoundException;
import br.com.automated_tests_with_java.model.Person;
import br.com.automated_tests_with_java.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonServices services;

    private Person person0;

    @BeforeEach
    public void setup(){
        //Given/Arrage
        person0= new Person(
                "Guilherme",
                "Barbosa",
                "gs@gmail.com",
                "Recife",
                "Male");

    }

    @DisplayName("JUnit test for Given Person object when Save Person the Return Person object")
    @Test
    void testGivenPersonObject_WhenSavePerson_thenReturnPersonobject(){
        //Given /arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person0)).willReturn(person0);

        //when /act
        Person savedPerson= services.create(person0);

        //then/assert
        assertNotNull(savedPerson);
        assertEquals("Guilherme",savedPerson.getFirstName());
    }
    @DisplayName("JUnit test for Given Existing Email when Save Person the throws Exception")
    @Test
    void testGivenExistingEmail_WhenSavePerson_thenTrowsException(){
        //Given /arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));


        //when /act
        assertThrows(ResourceNotFoundException.class,()-> {
                    services.create(person0);
                });

        //then/assert
        verify(repository,never()).save(any(Person.class));  //em nenhuma situação o método save pode ser chamado
    }
    @DisplayName("JUnit test for Given Person List when findAll Persons the Return Persons")
    @Test
    void testGivenPersonList_WhenfindAllPersons_thenReturnPersonList(){
        //Given /arrange
        Person person1= new Person("Geovane",
                "Barbosa",
                "gb@gmail.com",
                "Recife",
                "Male");

        given(repository.findAll()).willReturn(List.of(person0,person1));

        //when /act   - verifica se vai retornar as duas pessaos
        List<Person> personsList= services.findAll();

        //then/assert
        assertNotNull(personsList);
        assertEquals(2,personsList.size());
    }

    @DisplayName("JUnit test for Given Empty Person List when findAll Persons the Return Persons")
    @Test
    void testGivenEmptyPersonList_WhenfindAllPersons_thenReturnEmptyPersonList(){
        //Given /arrange
        Person person1= new Person("Geovane",
                "Barbosa",
                "gb@gmail.com",
                "Recife",
                "Male");

        given(repository.findAll()).willReturn(Collections.emptyList());

        //when /act   - verifica se vai retornar as duas pessaos
        List<Person> personsList= services.findAll();

        //then/assert
        assertTrue(personsList.isEmpty());
        assertEquals(0,personsList.size());
    }
    @DisplayName("JUnit test for Given PersonId when  findById the Return Person object")
    @Test
    void testGivenPersonId_WhenfindById_thenReturnPersonobject(){
        //Given /arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));


        //when /act
      Person savedPerson= services.findById(1L);

        //then/assert
        assertNotNull(savedPerson);
        assertEquals("Guilherme",savedPerson.getFirstName());
    }

    @DisplayName("JUnit test for Given Person object when Update Person the Return  updated Person object")
    @Test
    void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatePersonObject(){
        //Given /arrange
        person0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));

        person0.setFirstName("George");
        person0.setEmail("gb@gmail.com");

        given(repository.save(person0)).willReturn(person0);


        //when /act
        Person updatedPerson= services.update(person0);

        //then/assert
        assertNotNull(updatedPerson);
        assertEquals("George",updatedPerson.getFirstName());
        assertEquals("gb@gmail.com",updatedPerson.getEmail());
    }
    @DisplayName("JUnit test for Given PersonID when Delete  Person then do Nothing")
    @Test
    void testGivenPersonID_WhenDeletePerson_thenDoNothing(){

        //Given /arrange
        person0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));
        willDoNothing().given(repository).delete(person0);


        //when /act
         services.delete(person0.getId());

        //then/assert
       verify(repository,times(1)).delete(person0);   //verifica se o método delete é chamado apenas 1 vez
    }







}