package com.tsm.xlstocsv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PerSheetOutputDispatcher implements OutputDispatcher {
    private String basePath;

    public PerSheetOutputDispatcher(String basePath) {
        this.basePath = basePath;
    }

    @Override
	public PrintStream openStreamForSheet(String sheetName) throws FileNotFoundException {
        return new PrintStream(new File(basePath, sheetName + ".csv"));
    }

    @Override
	public void closeStreamForSheet(PrintStream stream, String sheetName) {
        stream.close();
    }
}