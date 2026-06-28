package com.hbb.ai;

import com.hbb.ai.utils.VectorDistanceUtils;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class HeimaAiApplicationTests {

    @Autowired
    private OpenAiEmbeddingModel openAiEmbeddingModel;
    @Test
    void contextLoads() {
        float[] helloWorlds = openAiEmbeddingModel.embed("hello world");
        System.out.println(Arrays.toString(helloWorlds));
    }

}
