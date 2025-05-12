package tpspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import tpspring.model.Category;
import tpspring.model.Todo;

//@TestConfiguration
//class TestConfig {
//    @Bean
//    public ObjectMapper om() {
//        var om = Mockito.mock(ObjectMapper.class);
//        Mockito.when(om.reader()).thenReturn(Mockito.mock(ObjectReader.class));
//        return om;
//    }
//}

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@Import(TestConfig.class)
public class TestTodoServiceV2 {
    // Mocking the repository
     @MockitoBean
     private TodoCrudRepository repository;

    // @Autowired
    // private TodoServiceV2 todoService;

    @Autowired
    ObjectMapper om;

    // Todo todo;
    // Todo todo2;

    @BeforeEach
    void setUp() {
        // To adapt
        // todo = new Todo(1L, "title 1", "bar", List.of(Category.LOW_PRIORITY), null, "foo");
        // todo2 = new Todo(2L, "title 2", "foo", List.of(Category.HIGH_PRIORITY), null, "you");
    }

    // @Test()
    // public void saveCalledWhenAddingATodo() {
    //     // Configuring the mock so that a call to 'save' with 'todo' will return the same todo
    //     Mockito.when(repository.save(todo)).thenReturn(todo2);

    //     Todo res = todoService.addTodo(todo);

        // Checking that the method returns the result of 'save'.
        // To adapt since not same:
        // assertSame(todo2, res);
    // }
}
