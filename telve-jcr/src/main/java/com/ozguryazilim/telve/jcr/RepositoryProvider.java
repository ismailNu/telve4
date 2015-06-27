/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozguryazilim.telve.jcr;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import org.infinispan.schematic.document.ParsingException;
import org.modeshape.common.collection.Problems;
import org.modeshape.jcr.ConfigurationException;
import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.RepositoryConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Altta bulunan JCR motorunu kullanarak repository provider sağlar.
 *
 * @author Hakan Uygun
 */
public class RepositoryProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryProvider.class);

    private static ModeShapeEngine engine;

    

    public static Repository getRepository(String configFile) {
        if( engine == null ){
            engine = new ModeShapeEngine();
        }
        engine.start();
        // Load the configuration for a repository via the classloader (can also use path to a file)...
        Repository repository;
        String repositoryName;
        try {
            InputStream is = RepositoryProvider.class.getClassLoader().getResourceAsStream(configFile);
            RepositoryConfiguration config = RepositoryConfiguration.read(is, configFile);

            // We could change the name of the repository programmatically ...
            // config = config.withName("Some Other Repository");
            // Verify the configuration for the repository ...
            Problems problems = config.validate();
            if (problems.hasErrors()) {
                LOGGER.error("Problems starting the engine.");
                LOGGER.error("{}", problems);
                return null;
            }

            // Deploy the repository ...
            repository = engine.deploy(config);
            repositoryName = config.getName();
            return repository;
        } catch (ParsingException | FileNotFoundException | ConfigurationException | RepositoryException e) {
            LOGGER.error("Problems starting the engine.", e);
        }

        return null;
    }

    public static void shutdown(){
        try {
            engine.shutdown().get();
            engine = null;
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error("Connot shutdown jcr engine", ex);
        }
    }
    
}