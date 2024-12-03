package br.com.fiap.soat07.clean.infra.rest.presenter;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.fiap.soat07.clean.core.domain.value.CPF;

public class CPFSerializer extends JsonSerializer<CPF> {
    @Override
    public void serialize(CPF cpf, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(cpf.toString());
    }
}
