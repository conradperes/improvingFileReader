package br.com.improving.backend;

import java.io.IOException;

public interface Webservice {
    void processed(String fileName) throws IOException;
}
