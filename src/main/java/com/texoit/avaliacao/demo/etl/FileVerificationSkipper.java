package com.texoit.avaliacao.demo.etl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

import java.io.FileNotFoundException;

public class FileVerificationSkipper implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger("badRecordLogger");

    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
        if (exception instanceof FileNotFoundException) {
            return false;
        } else if (exception instanceof FlatFileParseException && skipCount <= 15) {
            FlatFileParseException ffpe = (FlatFileParseException) exception;
            final String errorMessage = "Ocorreu um erro ao processar o "
                    + ffpe.getLineNumber()
                    + " linha do arquivo. Abaixo estava o defeito " + "arquivoInicial.\n" +
                    ffpe.getInput() + "\n";
            logger.error("{}", errorMessage);
            return true;
        } else {
            return false;
        }
    }
}
