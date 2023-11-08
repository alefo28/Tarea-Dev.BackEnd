package com.microservice.zuul.filters;

import java.io.InputStream;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.io.IOUtils;

import org.json.JSONObject;
import org.json.XML;

public class FormatValidationFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "pre"; // Filtro que se ejecuta antes de la petición se enrutada
    }

    @Override
    public int filterOrder() {
        return 1; // Orden de ejecución del filtro
    }

    @Override
    public boolean shouldFilter() {
        return true; // Habilita el filtro
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        try {
            // Leer el cuerpo de la solicitud
            InputStream requestStream = ctx.getRequest().getInputStream();
            String requestContent = IOUtils.toString(requestStream, Charset.defaultCharset());

            // Realizar la conversión de XML a JSON
            String jsonRequest = convertXmlToJson(requestContent);

            // Reemplazar el cuerpo de la solicitud con el JSON convertido
            ctx.setResponseDataStream(IOUtils.toInputStream(jsonRequest, Charset.defaultCharset()));

            // Continuar con el enrutamiento de la solicitud
            ctx.setSendZuulResponse(true);

            // Validar que la respuesta sea un JSON válido
            InputStream responseStream = ctx.getResponseDataStream();
            String jsonResponse = IOUtils.toString(responseStream, Charset.defaultCharset());
            if (!isValidJson(jsonResponse)) {
                // Si la respuesta no es válida, establece el código de respuesta en 400 (Bad Request)
                ctx.setResponseStatusCode(400);
                ctx.setResponseBody("Respuesta no válida");
                ctx.setSendZuulResponse(false);
            }
        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir
            throw new ZuulException(e, 500, "Error interno");
        }

        return null;
    }

    // Método para convertir de XML a JSON (debes implementar esta lógica según tus necesidades)
    private String convertXmlToJson(String xmlContent) throws JsonProcessingException {
        // Ejemplo de conversión simple de XML a JSON utilizando la biblioteca Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject jsonObject = XML.toJSONObject(xmlContent);
        return objectMapper.writeValueAsString(jsonObject);
    }

    // Método para validar si una cadena es un JSON válido (debes implementar esta lógica según tus necesidades)
    private boolean isValidJson(String jsonContent) {
        try {
            new JSONObject(jsonContent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
