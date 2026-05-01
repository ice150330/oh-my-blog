package com.portfolio.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.portfolio.config.SecurityConfig;
import com.portfolio.entity.Project;
import com.portfolio.security.JwtAuthenticationFilter;
import com.portfolio.security.JwtTokenProvider;
import com.portfolio.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 项目接口测试
 */
@WebMvcTest(ProjectController.class)
@Import({SecurityConfig.class})
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldListProjects() throws Exception {
        Page<Project> emptyPage = new Page<>(1, 10);
        when(projectService.listPublished(any())).thenReturn(emptyPage);

        mockMvc.perform(get("/api/v1/projects")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records").isArray());
    }

    @Test
    void shouldListCategories() throws Exception {
        when(projectService.listCategories()).thenReturn(List.of("data-engineering", "data-analysis"));

        mockMvc.perform(get("/api/v1/projects/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0]").value("data-engineering"))
                .andExpect(jsonPath("$.data[1]").value("data-analysis"));
    }

    @Test
    void shouldReturn404ForMissingProject() throws Exception {
        when(projectService.getBySlug("non-existent"))
                .thenThrow(new com.portfolio.common.exception.ResourceNotFoundException("项目不存在: non-existent"));

        mockMvc.perform(get("/api/v1/projects/non-existent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404));
    }
}
