package com.example.practice.service.kafka;

import com.example.practice.model.TranslateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics="topic01", groupId = "cjs" )
    public void consume(ConsumerRecord<String, TranslateResult> consumerRecord){
        TranslateResult translateResult = consumerRecord.value();
        String text = translateResult.getMessage().getResult().getTranslatedText();
        String beforeLanguage = translateResult.getMessage().getResult().getSrcLangType();
        String afterLanguage = translateResult.getMessage().getResult().getTarLangType();

        log.info("topic01 = beforeLanguage : {}, afterLanguage : {}, translate messager : {}", beforeLanguage, afterLanguage, text);
    }

    @KafkaListener(topics="topic02", groupId = "cjs" )
    public void consume2(ConsumerRecord<String, TranslateResult> consumerRecord){
        TranslateResult translateResult = consumerRecord.value();
        String text = translateResult.getMessage().getResult().getTranslatedText();
        String beforeLanguage = translateResult.getMessage().getResult().getSrcLangType();
        String afterLanguage = translateResult.getMessage().getResult().getTarLangType();

        log.info("topic02 = beforeLanguage : {}, afterLanguage : {}, translate messager : {}", beforeLanguage, afterLanguage, text);
    }

//    @KafkaListener(topics="${kafka.topic.name}", groupId = "cjs",
//            containerFactory = "kafkaListenerContainerFactoryMysql")
//    public void mysqlConsume(ConsumerRecord<String, SongEntity> consumerRecord){
//        log.info("consumerRecord.value1 : {}", consumerRecord.value());
        //SongEntity songEntity = consumerRecord.value().getClass();
        /*log.info("consumerRecord.value1 : {}", consumerRecord.value());

        log.info("consumer insertSongModel : {}", songEntity);
        log.info("mysql에 적재 start : {}, {}절-{}", songEntity.getSongName(), songEntity.getVerse(), songEntity.getVerseRow());
        songRepository.save(songEntity);
        log.info("mysql에 적재 end : {}", songEntity.getSongName());*/
//    }

}
