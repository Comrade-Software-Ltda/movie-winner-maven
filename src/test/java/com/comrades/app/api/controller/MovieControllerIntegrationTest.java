//package com.comrades.app.api.controller;
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.comrades.app.application.services.movie.IMovieCommand;
//import com.comrades.app.application.services.movie.IMovieQuery;
//import com.comrades.app.application.services.movie.commands.MovieCommand;
//import com.comrades.app.config.MatrixWebConfig;
//import com.comrades.app.core.bases.UseCaseFacade;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.io.File;
//import java.io.FileInputStream;
//
//
//@WebAppConfiguration
//@ContextConfiguration(classes = { MatrixWebConfig.class, MovieController.class })
//@ActiveProfiles("test")
//@RunWith(SpringJUnit4ClassRunner.class)
//public class MovieControllerIntegrationTest {
//
//    @MockBean
//    private IMovieCommand _movieCommand;
//
//    @MockBean
//    private IMovieQuery _movieQuery;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Test
//    public void whenFileUploaded_thenVerifyStatus() throws Exception {
//        MockMultipartFile file = new MockMultipartFile("file", "hello.csv",
//                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(new File("src/main/resources/movielist.csv")));
//
//        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        mockMvc.perform(multipart("/api/v1/movie/uploadFile").file(file)).andExpect(status().isOk());
//    }
//
//}