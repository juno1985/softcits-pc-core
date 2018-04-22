package org.softcits.pc.core.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PCCoreWebServiceTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void whenGetGreeting() throws Exception {
		String result = mockMvc.perform(get("/greeting").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.content").value("Fist SpringBoot App!")).andReturn().getResponse()
				.getContentAsString();

		System.out.println(result);
	}
	
	//http://localhost:8881/core/computer/all
	@Test
	public void whenGetAllComputers() throws UnsupportedEncodingException, Exception {
		String result = mockMvc.perform(get("/computer/all").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				//判断应该返回数组长度为3
				.andExpect(jsonPath("$.length()").value(3)).andReturn().getResponse()
				.getContentAsString();

		System.out.println(result);
	}
}
