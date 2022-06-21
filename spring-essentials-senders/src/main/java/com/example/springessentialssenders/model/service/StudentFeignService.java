package com.example.springessentialssenders.model.service;

import com.example.springessentialssenders.model.client.ConsumersClient;
import com.example.springessentialssenders.model.domain.EValidation;
import com.example.springessentialssenders.model.dto.ErrorDTO;
import com.example.springessentialssenders.model.entity.Student;
import com.example.springessentialssenders.model.exceptions.*;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentFeignService {

    @Autowired private ConsumersClient client;

    public List<Student> findStudentConsumersByName(String name) {
        try {
            if (StringUtils.isBlank(name))
                throw new StudentNotFoundException(EValidation.STUDENT_NOT_FOUND_FOR_NAME);

            List<Student> students = client.findConsumersByNameIgnoreCaseContaining(name);
            return students;
        } catch (FeignException e) {
            throw e;
        } catch (FeignDecoderException e){
            ErrorDTO erro = e.getErrorsDTO().getErrors().get(0);

            if (erro.getCode().equals(EValidation.NOT_IDENTIFIED.getCode()))
                throw new EssentialsRuntimeException(erro.getMessage());

            throw new ValidationsException(erro.getCode(), erro.getMessage());
        } catch (Exception e) {
            log.error("There was a generic problem when trying to return students by name.", ExceptionUtils.getStackTrace(e));
            throw new ResourceNotFoundException(EValidation.NOT_IDENTIFIED);
        }
    }
}
