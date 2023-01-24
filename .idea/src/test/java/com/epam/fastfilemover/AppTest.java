package com.epam.fastfilemover;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.nio.file.Path;

class AppTest {
   @Test
    void testFileFastMover()  {
       FastFileMover.moveWithFileChannel("c:/1/lindgren-dzieci-z-bullerbyn.pdf", "c:/1/lindgren-dzieci-z-bullerbyn.pdf");
       String[] args = { "-mfc","c:/1/payment-documents.json","c:/2"};
       new CommandLine(new App())
               .setCaseInsensitiveEnumValuesAllowed(true)
               .execute(args);
    }

}
