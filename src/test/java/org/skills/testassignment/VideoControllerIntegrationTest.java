package org.skills.testassignment;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skills.testassignment.entity.Video;
import org.skills.testassignment.repository.EngagementRepository;
import org.skills.testassignment.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private EngagementRepository engagementRepository;

    @BeforeEach
    void setup() {
        videoRepository.deleteAll();

        Video video = new Video();
        video.setTitle("Integration Test Movie");
        video.setDirector("Integration Director");
        video.setMainActor("Integration Star");
        video.setGenre("Test");
        video.setRunningTime(120);

        videoRepository.save(video);
    }

    @AfterEach
    void tearDown() {
        engagementRepository.deleteAll();
        videoRepository.deleteAll();
    }

    @Test
    void listAvailableVideos_success() throws Exception {
        mockMvc.perform(get("/videos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Integration Test Movie"))
                .andExpect(jsonPath("$[0].director").value("Integration Director"))
                .andExpect(jsonPath("$[0].mainActor").value("Integration Star"))
                .andExpect(jsonPath("$[0].genre").value("Test"))
                .andExpect(jsonPath("$[0].runningTime").value(120));
    }

    @Test
    void publishVideo_success() throws Exception {
        String requestBody = """
        {
           "title": "New Movie",
           "director": "New Director",
           "mainActor": "New Actor",
           "genre": "Action",
           "runningTime": 90
        }
        """;

        mockMvc.perform(post("/videos")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("New Movie"));
    }

    @Test
    void softlyDeleteVideo_success() throws Exception {
        Long videoId = videoRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/videos/" + videoId))
                .andExpect(status().isOk());

        Video updatedVideo = videoRepository.findById(videoId).orElseThrow();
        assertThat(updatedVideo.getSoftDeleted()).isTrue();
    }

    @Test
    void searchVideosByFilter_success() throws Exception {
        Video matchingVideo = new Video();
        matchingVideo.setTitle("Searchable Movie");
        matchingVideo.setDirector("Search Director");
        matchingVideo.setMainActor("Search Star");
        matchingVideo.setGenre("Search Genre");
        matchingVideo.setRunningTime(100);
        matchingVideo.setYearOfRelease(2022);
        videoRepository.save(matchingVideo);

        Video nonMatchingVideo = new Video();
        nonMatchingVideo.setTitle("Irrelevant Movie");
        nonMatchingVideo.setDirector("Some Director");
        nonMatchingVideo.setMainActor("Some Star");
        nonMatchingVideo.setGenre("Some Genre");
        nonMatchingVideo.setRunningTime(110);
        nonMatchingVideo.setYearOfRelease(2020);
        videoRepository.save(nonMatchingVideo);

        String requestBody = """
    {
       "director": "Search Director",
       "yearOfRelease": 2022
    }
    """;

        mockMvc.perform(post("/videos/search")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Searchable Movie"))
                .andExpect(jsonPath("$[0].director").value("Search Director"))
                .andExpect(jsonPath("$[0].mainActor").value("Search Star"))
                .andExpect(jsonPath("$[0].genre").value("Search Genre"))
                .andExpect(jsonPath("$[0].runningTime").value(100));
    }

}